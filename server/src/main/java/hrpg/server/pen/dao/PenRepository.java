package hrpg.server.pen.dao;

import hrpg.server.common.dao.WithUserRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PenRepository extends WithUserRepository<Pen, Long> {
}
