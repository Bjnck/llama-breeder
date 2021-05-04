package hrpg.server.item.service;

import hrpg.server.common.exception.InsufficientCoinsException;
import hrpg.server.common.properties.ParametersProperties;
import hrpg.server.item.dao.Item;
import hrpg.server.item.dao.ItemRepository;
import hrpg.server.item.dao.ItemSpecification;
import hrpg.server.item.service.exception.MaxItemsReachedException;
import hrpg.server.item.service.exception.ShopItemNotFoundException;
import hrpg.server.item.type.ItemCode;
import hrpg.server.shop.service.ShopItemDto;
import hrpg.server.shop.service.ShopItemService;
import hrpg.server.user.dao.User;
import hrpg.server.user.dao.UserRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.validation.constraints.NotNull;
import java.time.Instant;
import java.util.Optional;

@Service
public class ItemServiceImpl implements ItemService {

    private final ItemRepository itemRepository;
    private final UserRepository userRepository;
    private final ItemMapper itemMapper;
    private final ShopItemService shopItemService;
    private final ParametersProperties parametersProperties;

    public ItemServiceImpl(ItemRepository itemRepository,
                           UserRepository userRepository,
                           ItemMapper itemMapper,
                           ShopItemService shopItemService,
                           ParametersProperties parametersProperties) {
        this.itemRepository = itemRepository;
        this.userRepository = userRepository;
        this.itemMapper = itemMapper;
        this.shopItemService = shopItemService;
        this.parametersProperties = parametersProperties;
    }

    @Transactional
    @Override
    public ItemDto create(@NotNull ItemCode code, int quality)
            throws ShopItemNotFoundException, InsufficientCoinsException, MaxItemsReachedException {
        //validate shopItem exists
        ShopItemDto shopItemDto = shopItemService.findByCodeAndQuality(code, quality)
                .orElseThrow(ShopItemNotFoundException::new);

        User user = userRepository.get();

        //validate user has enough coins
        if(shopItemDto.getCoins() > 0){
            if(user.getDetails().getCoins() < shopItemDto.getCoins())
                throw new InsufficientCoinsException();
            user.getDetails().setCoins(user.getDetails().getCoins()- shopItemDto.getCoins());
        }

        //validate max number of items reached
        long itemCount = itemRepository.count();
        if(itemCount >= parametersProperties.getItems().getMax())
            throw new MaxItemsReachedException();

        //create new item
        user.getDetails().setLastPurchase(Instant.now());
        return itemMapper.toDto(itemRepository.save(Item.builder().code(code).quality(quality).build()));
    }

    @Override
    public Optional<ItemDto> findById(long id) {
        return itemRepository.findById(id).map(itemMapper::toDto);
    }

    @Override
    public Page<ItemDto> search(ItemSearch search, Pageable pageable) {
        return itemRepository.findAll(new ItemSpecification(itemMapper.toCriteria(search)), pageable)
                .map(itemMapper::toDto);
    }

    @Override
    public void delete(long id) {
        itemRepository.findById(id).ifPresent(itemRepository::delete);
    }
}
