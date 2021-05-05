package hrpg.server.capture.dao;

import hrpg.server.common.dao.WithUserRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.Optional;

@Repository
public interface CaptureRepository extends WithUserRepository<Capture, Long> {
    long countByStartTimeLessThanEqualAndEndTimeGreaterThanEqual(LocalDateTime startTime, LocalDateTime endTime);

    Optional<Capture> findByCreatureIdIsNullAndEndTimeLessThanEqual(LocalDateTime endTime);
}
