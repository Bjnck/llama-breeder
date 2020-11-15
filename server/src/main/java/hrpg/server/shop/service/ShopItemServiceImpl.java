package hrpg.server.shop.service;

import hrpg.server.item.type.ItemCode;
import hrpg.server.shop.dao.ShopItem;
import hrpg.server.shop.dao.ShopItemRepository;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class ShopItemServiceImpl implements ShopItemService {

    private final ShopItemRepository shopItemRepository;
    private final ShopItemMapper shopItemMapper;

    public ShopItemServiceImpl(ShopItemRepository shopItemRepository,
                               ShopItemMapper shopItemMapper) {
        this.shopItemRepository = shopItemRepository;
        this.shopItemMapper = shopItemMapper;
    }

    @Override
    public Page<ShopItemDto> search(ShopItemSearch search, Pageable pageable) {
        return shopItemRepository.findAll(
                Example.of(ShopItem.builder()
                        .code(search.getCode())
                        .quality(search.getQuality())
                        .build()),
                pageable)
                .map(shopItemMapper::toDto);
    }

    @Override
    public Optional<ShopItemDto> findByCodeAndQuality(ItemCode code, int quality) {
        return shopItemRepository.findByCodeAndQuality(code, quality)
                .map(shopItemMapper::toDto);
    }
}
