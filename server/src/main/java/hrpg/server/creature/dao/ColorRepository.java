package hrpg.server.creature.dao;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ColorRepository extends CrudRepository<Color, Integer> {
    Optional<Color> findByCode(String code);

    Optional<Color> findByParentCode(String parentCode);

    List<Color> findAllByGeneration(int generation);

    List<Color> findAllByGenerationAndParentCodeIsNull(int generation);

    Long countByGenerationAndParentCodeIsNull(int generation);
}
