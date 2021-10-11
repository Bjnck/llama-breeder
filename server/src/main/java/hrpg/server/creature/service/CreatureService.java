package hrpg.server.creature.service;

import hrpg.server.creature.service.exception.CreatureInUseException;
import hrpg.server.creature.service.exception.CreatureNotFoundException;
import hrpg.server.item.type.ItemCode;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import javax.validation.constraints.NotNull;
import java.util.List;
import java.util.Optional;

public interface CreatureService {
    CreatureDto update(long id, @NotNull CreatureDto creatureDto) throws CreatureNotFoundException;

    Optional<CreatureDto> findById(long id);

    long count();

    Page<CreatureDto> search(CreatureSearch search, Pageable pageable);

    int delete(long id) throws CreatureNotFoundException, CreatureInUseException;

    CreatureDto hit(long id, ItemCode itemCode, int quality) throws CreatureNotFoundException;

    void calculateEnergy(List<Long> ids) throws CreatureNotFoundException;

    List<CreatureDto> calculateBirth(List<Long> ids) throws CreatureNotFoundException;
}
