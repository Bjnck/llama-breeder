package hrpg.server.capture.dao;

import hrpg.server.common.dao.WithUserRepository;
import org.springframework.stereotype.Repository;

import java.time.ZonedDateTime;
import java.util.List;

@Repository
public interface CaptureRepository extends WithUserRepository<Capture, Long> {
    long countByCreatureIdIsNull();

    List<Capture> findByCreatureIdIsNullAndEndTimeLessThanEqual(ZonedDateTime endTime);

    boolean existsByCreatureId(long creatureId);
}
