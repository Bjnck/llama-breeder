package hrpg.server.creature.service;

import hrpg.server.creature.service.exception.CreatureNotFoundException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

public interface CreatureService {
    Optional<CreatureDto> findById(Long id);

    Page<CreatureDto> search(CreatureSearch search, Pageable pageable);

    int delete(long id) throws CreatureNotFoundException;
}
