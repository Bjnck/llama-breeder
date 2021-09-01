package hrpg.server.creature.service;

import hrpg.server.creature.service.exception.CreatureNotFoundException;
import hrpg.server.item.type.ItemCode;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

public interface CreatureService {
    Optional<CreatureDto> findById(long id);

    long count();

    Page<CreatureDto> search(CreatureSearch search, Pageable pageable);

    int delete(long id) throws CreatureNotFoundException;

    CreatureDto hit(long id, ItemCode itemCode, int quality) throws CreatureNotFoundException;

    boolean breed(long id1, long id2) throws CreatureNotFoundException;
}
