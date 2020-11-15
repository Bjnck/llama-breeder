package hrpg.server.item.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

public interface ItemService {
    ItemDto create(ItemDto itemDto);

    Optional<ItemDto> findById(String id);

    Page<ItemDto> search(ItemSearch search, Pageable pageable);

    long count(ItemSearch search);

    void delete(String id);
}
