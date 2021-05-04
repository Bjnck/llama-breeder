package hrpg.server.shop.service;

import hrpg.server.item.type.ItemCode;
import hrpg.server.shop.dao.ShopItemRepository;
import hrpg.server.shop.dao.ShopItemSpecification;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import javax.validation.constraints.NotNull;
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
    public Page<ShopItemDto> search(ShopItemSearch search, @NotNull Pageable pageable) {
        return shopItemRepository.findAll(new ShopItemSpecification(shopItemMapper.toCriteria(search)), pageable)
                .map(shopItemMapper::toDto);
    }

    @Override
    public Optional<ShopItemDto> findByCodeAndQuality(@NotNull ItemCode code, int quality) {
        return shopItemRepository.findByCodeAndQuality(code, quality)
                .map(shopItemMapper::toDto);
    }
}
