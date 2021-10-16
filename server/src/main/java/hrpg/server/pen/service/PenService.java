package hrpg.server.pen.service;

import hrpg.server.creature.service.exception.CreatureInUseException;
import hrpg.server.creature.service.exception.CreatureNotFoundException;
import hrpg.server.creature.service.exception.MaxCreaturesException;
import hrpg.server.item.service.exception.ItemInUseException;
import hrpg.server.item.service.exception.ItemNotFoundException;
import hrpg.server.item.service.exception.MaxItemsException;
import hrpg.server.pen.service.exception.InvalidPenSizeException;
import hrpg.server.pen.service.exception.PenNotFoundException;
import hrpg.server.user.service.exception.InsufficientCoinsException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import javax.validation.constraints.NotNull;
import java.time.ZonedDateTime;
import java.util.Optional;

public interface PenService {
    Optional<PenDto> findById(long id);

    Page<PenDto> search(@NotNull Pageable pageable);

    PenDto update(long id, @NotNull PenDto penDto)
            throws PenNotFoundException, InvalidPenSizeException, InsufficientCoinsException, ItemInUseException,
            MaxItemsException, ItemNotFoundException, CreatureNotFoundException, MaxCreaturesException, CreatureInUseException;

    PenActivationDto activateItem(long id, long itemId) throws PenNotFoundException, ItemNotFoundException;

    PenActivationDto activateItem(long id, long itemId, ZonedDateTime activationTime)
            throws PenNotFoundException, ItemNotFoundException;
}
