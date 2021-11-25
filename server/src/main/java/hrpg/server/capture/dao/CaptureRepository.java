package hrpg.server.capture.dao;

import hrpg.server.common.dao.WithUserRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CaptureRepository extends WithUserRepository<Capture, Long> {
    long countByCreatureInfoIsNull();
}
