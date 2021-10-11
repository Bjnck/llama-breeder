package hrpg.server.pen.dao;

import hrpg.server.common.dao.WithUserRepository;
import hrpg.server.common.security.OAuthUserUtil;
import hrpg.server.creature.dao.Creature;
import hrpg.server.item.dao.Item;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface PenRepository extends WithUserRepository<Pen, Long> {
    boolean existsByItemsContainingAndIdNotAndUserId(Item item, long id, int userId);

    default boolean existsByItemsContainingAndIdNot(Item item, long id) {
        return existsByItemsContainingAndIdNotAndUserId(item, id, OAuthUserUtil.getUserId());
    }

    boolean existsByCreaturesContainingAndIdNotAndUserId(Creature creature, long id, int userId);

    default boolean existsByCreaturesContainingAndIdNot(Creature creature, long id) {
        return existsByCreaturesContainingAndIdNotAndUserId(creature, id, OAuthUserUtil.getUserId());
    }

    boolean existsByCreaturesContainingAndUserId(Creature creature, int userId);

    default boolean existsByCreaturesContaining(Creature creature) {
        return existsByCreaturesContainingAndUserId(creature, OAuthUserUtil.getUserId());
    }

    Optional<Pen> findByCreaturesContaining(Creature creature);

    Optional<Pen> findByItemsContaining(Item item);
}
