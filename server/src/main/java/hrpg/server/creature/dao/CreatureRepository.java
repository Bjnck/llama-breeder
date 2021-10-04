package hrpg.server.creature.dao;

import hrpg.server.common.dao.WithUserRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface CreatureRepository extends WithUserRepository<Creature, Long> {
    Page<Creature> findAllByDetails_EnergyLessThan(int energy, Pageable pageable);

    Optional<Creature> findByIdAndDetails_EnergyLessThan(long id, int energy);

    default Page<Creature> findAllByEnergyNotFull(Pageable pageable) {
        return findAllByDetails_EnergyLessThan(100, pageable);
    }

    default Optional<Creature> findByIdAndEnergyNotFull(long id) {
        return findByIdAndDetails_EnergyLessThan(id, 100);
    }
}
