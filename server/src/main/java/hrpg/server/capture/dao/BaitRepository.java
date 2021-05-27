package hrpg.server.capture.dao;

import hrpg.server.common.dao.WithUserRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface BaitRepository extends WithUserRepository<Bait, Long> {
    Optional<Bait> findByGeneration(int level);
}
