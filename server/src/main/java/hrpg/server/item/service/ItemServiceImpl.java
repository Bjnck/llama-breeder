package hrpg.server.item.service;

import hrpg.server.user.service.exception.InsufficientCoinsException;
import hrpg.server.common.properties.ParametersProperties;
import hrpg.server.item.dao.Item;
import hrpg.server.item.dao.ItemRepository;
import hrpg.server.item.dao.ItemSpecification;
import hrpg.server.item.service.exception.ItemNotFoundException;
import hrpg.server.item.service.exception.MaxItemsException;
import hrpg.server.item.service.exception.ShopItemNotFoundException;
import hrpg.server.item.type.ItemCode;
import hrpg.server.shop.service.ShopItemDto;
import hrpg.server.shop.service.ShopItemService;
import hrpg.server.user.dao.User;
import hrpg.server.user.dao.UserRepository;
import hrpg.server.user.service.UserService;
import hrpg.server.user.service.exception.InsufficientLevelException;
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
    private final UserService userService;
    private final ParametersProperties parametersProperties;

    public ItemServiceImpl(ItemRepository itemRepository,
                           UserRepository userRepository,
                           ItemMapper itemMapper,
                           ShopItemService shopItemService,
                           UserService userService,
                           ParametersProperties parametersProperties) {
        this.itemRepository = itemRepository;
        this.userRepository = userRepository;
        this.itemMapper = itemMapper;
        this.shopItemService = shopItemService;
        this.userService = userService;
        this.parametersProperties = parametersProperties;
    }

    @Transactional(rollbackFor = {
            ShopItemNotFoundException.class,
            InsufficientCoinsException.class,
            InsufficientLevelException.class,
            MaxItemsException.class
    })
    @Override
    public ItemDto create(@NotNull ItemCode code, int quality)
            throws ShopItemNotFoundException, InsufficientCoinsException, InsufficientLevelException, MaxItemsException {
        //validate shopItem exists
        ShopItemDto shopItemDto = shopItemService.findByCodeAndQuality(code, quality)
                .orElseThrow(ShopItemNotFoundException::new);

        User user = userRepository.get();

        //validate user has required level
        if (user.getDetails().getLevel() < quality) {
            throw new InsufficientLevelException();
        }

        //validate user has enough coins
        if (shopItemDto.getCoins() > 0) {
            if (user.getDetails().getCoins() < shopItemDto.getCoins())
                throw new InsufficientCoinsException();
            userService.removeCoins(shopItemDto.getCoins());
        }

        //validate max number of items reached
        long itemCount = itemRepository.count();
        if (itemCount >= parametersProperties.getItems().getMax())
            throw new MaxItemsException();

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
    public void delete(long id) throws ItemNotFoundException {
        Item item = itemRepository.findById(id).orElseThrow(ItemNotFoundException::new);
        itemRepository.delete(item);
    }
}
