package hrpg.server.creature.service;

import hrpg.server.common.security.OAuthUserUtil;
import hrpg.server.creature.dao.Creature;
import hrpg.server.creature.dao.CreatureDetails;
import hrpg.server.creature.dao.CreatureRepository;
import org.springframework.stereotype.Component;

import javax.validation.constraints.NotNull;
import java.time.LocalDate;

@Component
public class CreatureFactoryImpl implements CreatureFactory {

    private final CreatureRepository creatureRepository;
    private final CreatureMapper creatureMapper;

    public CreatureFactoryImpl(CreatureRepository creatureRepository,
                               CreatureMapper creatureMapper) {
        this.creatureRepository = creatureRepository;
        this.creatureMapper = creatureMapper;
    }

    @Override
    public CreatureDto generateForCapture(int quality, @NotNull LocalDate endDate) {
        //todo define sex, color 1, gene1
        //todo check max creature, if max reached do not create new creature
        Creature creature = Creature.builder()
                .originalUserId(OAuthUserUtil.getUserId())
                .createDate(endDate)
                .build();
        creature.setDetails(CreatureDetails.builder()
                .creature(creature)
                .wild(true)
                .build());
        return creatureMapper.toDto(creatureRepository.save(creature));
    }
}
