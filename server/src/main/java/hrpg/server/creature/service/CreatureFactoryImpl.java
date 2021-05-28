package hrpg.server.creature.service;

import hrpg.server.common.properties.CreaturesProperties;
import hrpg.server.common.properties.ParametersProperties;
import hrpg.server.common.security.OAuthUserUtil;
import hrpg.server.creature.dao.*;
import hrpg.server.creature.service.exception.MaxCreaturesException;
import hrpg.server.creature.type.Sex;
import hrpg.server.user.service.UserService;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Component;

import javax.validation.constraints.NotNull;
import java.time.LocalDate;
import java.util.Random;

@Component
public class CreatureFactoryImpl implements CreatureFactory {

    private final CreatureRepository creatureRepository;
    private final CreatureMapper creatureMapper;
    private final CreaturesProperties creaturesParameters;
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
        this.creaturesParameters = parametersProperties.getCreatures();
        this.geneFactory = geneFactory;
        this.colorFactory = colorFactory;
        this.userService = userService;
    }

    @Override
    public CreatureDto generateForCapture(int userLevel, int nestQuality, Integer baitGeneration, @NotNull LocalDate captureEndDate)
            throws MaxCreaturesException {
        if (creatureRepository.count() >= creaturesParameters.getMax())
            throw new MaxCreaturesException();

        Creature.CreatureBuilder creatureBuilder = Creature.builder();

        //tutorial - always get female then male of different color, no gene
        if (userLevel == 0) {
            if (creatureRepository.count() == 0) {
                //if first creature
                creatureBuilder
                        .sex(Sex.F)
                        .color1(colorFactory.getForCapture(null));
            } else {
                //second creature
                Creature creature = creatureRepository.findAll(Pageable.unpaged()).stream().findFirst().orElseThrow();
                creatureBuilder
                        .sex(Sex.M)
                        .color1(colorFactory.getForCapture(creature.getColor1().getCode()));
            }
        } else {
            creatureBuilder
                    .sex(randomSex())
                    .color1(colorFactory.getForCapture(null))
                    .gene1(geneFactory.getForCapture(nestQuality, baitGeneration).orElse(null));
        }

        //set creature parameters for capture and save
        Creature creature = creatureBuilder
                .originalUserId(OAuthUserUtil.getUserId())
                .createDate(captureEndDate)
                .build();
        creature.setDetails(CreatureDetails.builder()
                .creature(creature)
                .wild(true)
                .build());
        return creatureMapper.toDto(creatureRepository.save(creature), userService);
    }

    private Sex randomSex() {
        return new Random().nextInt(100) < creaturesParameters.getChanceFemale() ? Sex.F : Sex.M;
    }
}
