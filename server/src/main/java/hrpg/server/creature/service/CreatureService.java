package hrpg.server.creature.service;

import hrpg.server.creature.service.exception.CreatureInUseException;
import hrpg.server.creature.service.exception.CreatureNotFoundException;
import hrpg.server.item.type.ItemCode;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Optional;

public interface CreatureService {
    Optional<CreatureDto> findById(long id);

    long count();

    Page<CreatureDto> search(CreatureSearch search, Pageable pageable);

    int delete(long id) throws CreatureNotFoundException, CreatureInUseException;

    void deletePartial(long id) throws CreatureNotFoundException;

    CreatureDto hit(long id, ItemCode itemCode, int quality) throws CreatureNotFoundException;

    void calculateEnergy(List<Long> ids) throws CreatureNotFoundException;

    List<CreatureDto> calculateBirth(List<Long> ids) throws CreatureNotFoundException;
}
