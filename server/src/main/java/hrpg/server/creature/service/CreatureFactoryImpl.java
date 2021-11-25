package hrpg.server.creature.service;

import hrpg.server.common.properties.CreaturesProperties;
import hrpg.server.common.properties.ParametersProperties;
import hrpg.server.common.security.OAuthUserUtil;
import hrpg.server.creature.dao.*;
import hrpg.server.creature.service.exception.CreatureNotFoundException;
import hrpg.server.creature.service.exception.MaxCreaturesException;
import hrpg.server.creature.type.Sex;
import hrpg.server.user.service.UserService;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.util.Pair;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import javax.validation.constraints.NotNull;
import java.time.LocalDate;
import java.util.*;

import static hrpg.server.creature.type.CreatureConstant.MATURITY_MAX;

@Component
public class CreatureFactoryImpl implements CreatureFactory {

    private final CreatureRepository creatureRepository;
    private final CreatureMapper creatureMapper;
    private final CreaturesProperties creaturesProperties;
    private final GeneFactory geneFactory;
    private final ColorFactory colorFactory;
    private final UserService userService;

    public CreatureFactoryImpl(CreatureRepository creatureRepository,
                               CreatureMapper creatureMapper,
                               ParametersProperties parametersProperties,
                               GeneFactory geneFactory,
                               ColorFactory colorFactory,
                               UserService userService) {
        this.creatureRepository = creatureRepository;
        this.creatureMapper = creatureMapper;
        this.creaturesProperties = parametersProperties.getCreatures();
        this.geneFactory = geneFactory;
        this.colorFactory = colorFactory;
        this.userService = userService;
    }

    @Override
    public CreatureDto generateForCapture(int userLevel, int netQuality, @NotNull LocalDate captureEndDate)
            throws MaxCreaturesException {
        validateMaxCreaturesReached();

        Creature.CreatureBuilder creatureBuilder = Creature.builder();

        //tutorial - always get female then male of different color, no gene
        if (userLevel == 0) {
            long creatureCount = creatureRepository.count();
            if (creatureCount == 0) {
                //if first creature
                creatureBuilder.info(CreatureInfo.builder()
                        .sex(Sex.F)
                        .color1(colorFactory.getForCapture(null))
                        .build());
            } else if (creatureCount == 1) {
                //second creature
                Creature creature = creatureRepository.findAll(Pageable.unpaged()).stream().findFirst().orElseThrow();
                creatureBuilder.info(CreatureInfo.builder()
                        .sex(Sex.M)
                        .color1(colorFactory.getForCapture(Collections.singletonList(creature.getInfo().getColor1().getCode())))
                        .build());
            } else {
                List<String> colors = creatureRepository.findAll(PageRequest.of(0, 2))
                        .map(c -> c.getInfo().getColor1().getCode())
                        .getContent();
                creatureBuilder.info(CreatureInfo.builder()
                        .sex(randomSex())
                        .color1(colorFactory.getForCapture(colors))
                        .build());
            }
        } else {
            creatureBuilder.info(CreatureInfo.builder()
                    .sex(randomSex())
                    .color1(colorFactory.getForCapture(null))
                    .gene1(geneFactory.getForCapture(netQuality).orElse(null))
                    .build());
        }

        //set creature parameters for capture and save
        Creature creature = creatureBuilder
                .originalUserId(OAuthUserUtil.getUserId())
                .createDate(captureEndDate)
                .wild(true)
                .maturity(MATURITY_MAX)
                .build();
        return creatureMapper.toDto(creatureRepository.save(creature), userService);
    }

    @Transactional(rollbackFor = {
            MaxCreaturesException.class,
            CreatureNotFoundException.class
    })
    @Override
    public List<CreatureDto> generateForBirth(long id) throws MaxCreaturesException, CreatureNotFoundException {
        validateMaxCreaturesReached();

        //calculate max possible babies
        int maxBabies = getNumberOfBabies();
        int spaceAvailable = creaturesProperties.getMax() - ((Long) creatureRepository.count()).intValue();
        if (maxBabies > spaceAvailable)
            maxBabies = spaceAvailable;

        //get parents
        Creature mother = creatureRepository.findById(id).orElseThrow(CreatureNotFoundException::new);
        CreatureInfo father = mother.getPregnancyMaleInfo();

        //generate babies
        List<CreatureDto> babies = new ArrayList<>();
        for (int i = 0; i < maxBabies; i++) {
            Pair<Color, Optional<Color>> colors = colorFactory.getForBirth(
                    mother.getInfo().getColor1(), mother.getInfo().getColor2(), father.getColor1(), father.getColor2());
            Pair<Optional<Gene>, Optional<Gene>> genes = geneFactory.getForBirth(
                    mother.getInfo().getGene1(), mother.getInfo().getGene2(), father.getGene1(), father.getGene2());

            Creature baby = Creature.builder()
                    .originalUserId(OAuthUserUtil.getUserId())
                    .createDate(mother.getPregnancyEndTime().toLocalDate())
                    .generation(Math.max(colors.getFirst().getGeneration(),
                            colors.getSecond().map(Color::getGeneration).orElse(0)))
                    .info(CreatureInfo.builder()
                            .sex(randomSex())
                            .color1(colors.getFirst())
                            .color2(colors.getSecond().orElse(null))
                            .gene1(genes.getFirst().orElse(null))
                            .gene2(genes.getSecond().orElse(null))
                            .build())
                    .parentInfo1(mother.getInfo().toBuilder().id(null).build())
                    .parentInfo2(father.toBuilder().id(null).build())
                    .build();
            babies.add(creatureMapper.toDto(creatureRepository.save(baby), userService));
        }

        //update mother pregnancy stats
        mother.setPregnancyMaleInfo(null);
        mother.setPregnant(false);
        mother.setPregnancyStartTime(null);
        mother.setPregnancyEndTime(null);

        return babies;
    }

    private void validateMaxCreaturesReached() throws MaxCreaturesException {
        if (creatureRepository.count() >= creaturesProperties.getMax())
            throw new MaxCreaturesException();
    }

    private Sex randomSex() {
        return new Random().nextInt(100) < creaturesProperties.getChanceFemale() ? Sex.F : Sex.M;
    }

    private int getNumberOfBabies() {
        return new Random().nextInt(creaturesProperties.getMaxBabies()) + 1;
    }
}
