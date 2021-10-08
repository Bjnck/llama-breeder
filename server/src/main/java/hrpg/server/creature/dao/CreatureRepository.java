package hrpg.server.creature.dao;

import hrpg.server.common.dao.WithUserRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

import java.time.ZonedDateTime;
import java.util.Optional;

@Repository
public interface CreatureRepository extends WithUserRepository<Creature, Long> {
    Optional<Creature> findByIdAndDetailsNotNull(Long id);

    default Optional<Creature> findByIdAlive(Long id) {
        return findByIdAndDetailsNotNull(id);
    }

    Page<Creature> findAllByDetailsNotNull(Pageable pageable);

    default Page<Creature> findAllAlive(Pageable pageable) {
        return findAllByDetailsNotNull(pageable);
    }

    long countByDetailsNotNull();

    default long countAlive() {
        return countByDetailsNotNull();
    }

    Page<Creature> findAllByDetailsNotNullAndDetails_EnergyLessThan(int energy, Pageable pageable);

    default Page<Creature> findAllByEnergyNotFull(Pageable pageable) {
        return findAllByDetailsNotNullAndDetails_EnergyLessThan(100, pageable);
    }

    Page<Creature> findAllByDetailsNotNullAndDetails_PregnancyEndTimeLessThanEqual(ZonedDateTime pregnancyEndTime, Pageable pageable);

    default Page<Creature> findAllByDetails_PregnancyEndTimeLessThanEqual(ZonedDateTime pregnancyEndTime, Pageable pageable) {
        return findAllByDetailsNotNullAndDetails_PregnancyEndTimeLessThanEqual(pregnancyEndTime, pageable);
    }

    //todo do not comply with userAspect if creature can be shared between users
    long countByDetailsNotNullAndParentId1OrParentId2(long parentId1, long parentId2);

    default long countByParentId1OrParentId2(long parentId1, long parentId2) {
        return countByDetailsNotNullAndParentId1OrParentId2(parentId1, parentId2);
    }
}
