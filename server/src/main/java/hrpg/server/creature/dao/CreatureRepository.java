package hrpg.server.creature.dao;

import hrpg.server.common.dao.WithUserRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

import java.time.ZonedDateTime;

import static hrpg.server.creature.type.CreatureConstant.ENERGY_MAX;

@Repository
public interface CreatureRepository extends WithUserRepository<Creature, Long> {
    Page<Creature> findAllByDetails_EnergyLessThan(int energy, Pageable pageable);

    default Page<Creature> findAllByEnergyNotFull(Pageable pageable) {
        return findAllByDetails_EnergyLessThan(ENERGY_MAX, pageable);
    }

    Page<Creature> findAllByDetails_PregnancyEndTimeLessThanEqual(ZonedDateTime pregnancyEndTime, Pageable pageable);
}
