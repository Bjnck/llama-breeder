package hrpg.server.creature.dao;

import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface GeneRepository extends org.springframework.data.repository.Repository<Gene, Integer> {
    Optional<Gene> findByCode(hrpg.server.creature.type.Gene code);
}
