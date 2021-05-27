package hrpg.server.capture.dao;

import hrpg.server.common.dao.WithUserRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface CaptureRepository extends WithUserRepository<Capture, Long> {
    long countByEndTimeGreaterThan(LocalDateTime endTime);

    List<Capture> findByCreatureIdIsNullAndEndTimeLessThanEqual(LocalDateTime endTime);
}
