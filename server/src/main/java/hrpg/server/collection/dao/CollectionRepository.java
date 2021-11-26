package hrpg.server.collection.dao;

import hrpg.server.common.dao.WithUserRepository;
import hrpg.server.creature.dao.Color;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface CollectionRepository extends WithUserRepository<Collection, Long> {
    Optional<Collection> findOneByColor(Color color);
}
