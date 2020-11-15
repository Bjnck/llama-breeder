package hrpg.server.shop.service;

import hrpg.server.item.type.ItemCode;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

public interface ShopItemService {
    Page<ShopItemDto> search(ShopItemSearch search, Pageable pageable);

    Optional<ShopItemDto> findByCodeAndQuality(ItemCode code, int quality);
}
