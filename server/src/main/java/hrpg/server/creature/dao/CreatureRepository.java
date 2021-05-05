package hrpg.server.creature.dao;

import hrpg.server.common.dao.WithUserRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CreatureRepository extends WithUserRepository<Creature, Long> {
}
