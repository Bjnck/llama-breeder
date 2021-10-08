package hrpg.server.pen.dao;

import hrpg.server.common.dao.WithUserRepository;
import hrpg.server.creature.dao.Creature;
import hrpg.server.item.dao.Item;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface PenRepository extends WithUserRepository<Pen, Long> {
    boolean existsByItemsContainingAndIdNot(Item item, long id);

    boolean existsByCreaturesContainingAndIdNot(Creature creature, long id);

    boolean existsByCreaturesContaining(Creature creature);

    Optional<Pen> findByCreaturesContaining(Creature creature);

    Optional<Pen> findByItemsContaining(Item item);
}
