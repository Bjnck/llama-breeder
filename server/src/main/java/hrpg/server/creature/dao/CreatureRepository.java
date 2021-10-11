package hrpg.server.creature.dao;

import hrpg.server.common.dao.WithUserRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

import java.time.ZonedDateTime;

import static hrpg.server.creature.type.CreatureConstant.ENERGY_MAX;

@Repository
public interface CreatureRepository extends WithUserRepository<Creature, Long> {
    Page<Creature> findAllByEnergyLessThan(int energy, Pageable pageable);

    default Page<Creature> findAllByEnergyNotFull(Pageable pageable) {
        return findAllByEnergyLessThan(ENERGY_MAX, pageable);
    }

    Page<Creature> findAllByPregnancyEndTimeLessThanEqual(ZonedDateTime pregnancyEndTime, Pageable pageable);
}
