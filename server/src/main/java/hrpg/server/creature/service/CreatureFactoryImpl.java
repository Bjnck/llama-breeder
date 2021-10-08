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
import org.springframework.data.util.Pair;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import javax.validation.constraints.NotNull;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Random;

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
    public CreatureDto generateForCapture(int userLevel, int netQuality, Integer baitGeneration, @NotNull LocalDate captureEndDate)
            throws MaxCreaturesException {
        validateMaxCreaturesReached();

        Creature.CreatureBuilder creatureBuilder = Creature.builder();

        //tutorial - always get female then male of different color, no gene
        if (userLevel == 0) {
            if (creatureRepository.countAlive() == 0) {
                //if first creature
                creatureBuilder
                        .sex(Sex.F)
                        .color1(colorFactory.getForCapture(null));
            } else {
                //second creature
                Creature creature = creatureRepository.findAllAlive(PageRequest.of(0, 1))
                        .stream().findFirst().orElseThrow();
                creatureBuilder
                        .sex(Sex.M)
                        .color1(colorFactory.getForCapture(creature.getColor1().getCode()));
            }
        } else {
            creatureBuilder
                    .sex(randomSex())
                    .color1(colorFactory.getForCapture(null))
                    .gene1(geneFactory.getForCapture(netQuality, baitGeneration).orElse(null));
        }

        //set creature parameters for capture and save
        Creature creature = creatureBuilder
                .originalUserId(OAuthUserUtil.getUserId())
                .createDate(captureEndDate)
                .build();
        creature.setDetails(CreatureDetails.builder()
                .creature(creature)
                .wild(true)
                .maturity(1000)
                .build());
        return creatureMapper.toDto(creatureRepository.save(creature), userService);
    }

    @Transactional
    @Override
    public List<CreatureDto> generateForBirth(long id) throws MaxCreaturesException, CreatureNotFoundException {
        validateMaxCreaturesReached();

        //calculate max possible babies
        int maxBabies = getNumberOfBabies();
        int spaceAvailable = creaturesProperties.getMax() - ((Long) creatureRepository.countAlive()).intValue();
        if (maxBabies > spaceAvailable)
            maxBabies = spaceAvailable;

        //get parents
        Creature mother = creatureRepository.findByIdAlive(id).orElseThrow(CreatureNotFoundException::new);
        Creature father = creatureRepository.findById(mother.getDetails().getPregnancyMaleId())
                .orElseThrow(CreatureNotFoundException::new);

        //generate babies
        List<CreatureDto> babies = new ArrayList<>();
        for (int i = 0; i < maxBabies; i++) {
            Pair<Color, Optional<Color>> colors = colorFactory.getForBirth(
                    mother.getColor1(), mother.getColor2(), father.getColor1(), father.getColor2());
            Pair<Optional<Gene>, Optional<Gene>> genes = geneFactory.getForBirth(
                    mother.getGene1(), mother.getGene2(), father.getGene1(), father.getGene2());

            Creature baby = Creature.builder()
                    .originalUserId(OAuthUserUtil.getUserId())
                    .createDate(mother.getDetails().getPregnancyEndTime().toLocalDate())
                    .generation(Math.max(colors.getFirst().getGeneration(),
                            colors.getSecond().map(Color::getGeneration).orElse(0)))
                    .sex(randomSex())
                    .color1(colors.getFirst())
                    .color2(colors.getSecond().orElse(null))
                    .gene1(genes.getFirst().orElse(null))
                    .gene2(genes.getSecond().orElse(null))
                    .parentId1(mother.getParentId1())
                    .parentId2(mother.getParentId2())
                    .build();
            baby.setDetails(CreatureDetails.builder()
                    .creature(baby)
                    .build());
            babies.add(creatureMapper.toDto(creatureRepository.save(baby), userService));
        }

        //update mother pregnancy stats
        mother.getDetails().setPregnancyMaleId(null);
        mother.getDetails().setPregnant(false);
        mother.getDetails().setPregnancyStartTime(null);
        mother.getDetails().setPregnancyEndTime(null);

        return babies;
    }

    private void validateMaxCreaturesReached() throws MaxCreaturesException {
        if (creatureRepository.countAlive() >= creaturesProperties.getMax())
            throw new MaxCreaturesException();
    }

    private Sex randomSex() {
        return new Random().nextInt(100) < creaturesProperties.getChanceFemale() ? Sex.F : Sex.M;
    }

    private int getNumberOfBabies() {
        return new Random().nextInt(creaturesProperties.getMaxBabies()) + 1;
    }
}
