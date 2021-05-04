package hrpg.server.item.service;

import hrpg.server.common.exception.InsufficientCoinsException;
import hrpg.server.item.service.exception.MaxItemsReachedException;
import hrpg.server.item.service.exception.ShopItemNotFoundException;
import hrpg.server.item.type.ItemCode;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import javax.validation.constraints.NotNull;
import java.util.Optional;

public interface ItemService {

    ItemDto create(@NotNull ItemCode code, int quality) throws ShopItemNotFoundException, InsufficientCoinsException, MaxItemsReachedException;

    Optional<ItemDto> findById(long id);

    Page<ItemDto> search(ItemSearch search, Pageable pageable);

    void delete(long id);
}
