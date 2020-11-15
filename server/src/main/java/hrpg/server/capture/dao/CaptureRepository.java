package hrpg.server.capture.dao;

import hrpg.server.common.dao.WithUserRepository;

import java.time.Instant;
import java.util.Optional;

public interface CaptureRepository extends WithUserRepository<Capture, String> {
    Optional<Capture> findByStartTimeLessThanEqualAndEndTimeGreaterThanEqualAndUserId(
            Instant startTime, Instant endTime, String userId);

    Optional<Capture> findByCreatureIdIsNullAndUserId(String userId);
}
