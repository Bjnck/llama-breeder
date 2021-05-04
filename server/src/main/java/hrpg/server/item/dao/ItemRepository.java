package hrpg.server.item.dao;

import hrpg.server.common.dao.WithUserRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ItemRepository extends WithUserRepository<Item, Long> {
}
