package hrpg.server.item.resource.validation;

import hrpg.server.common.resource.validation.ValidationContextService;
import hrpg.server.item.resource.ItemRequest;
import hrpg.server.item.service.ItemService;
import hrpg.server.shop.service.ShopItemService;
import hrpg.server.user.service.UserService;
import org.springframework.stereotype.Service;

@Service
public class ItemValidationContextService implements ValidationContextService<ItemRequest, ItemValidationContext> {

    private final UserService userService;
    private final ShopItemService shopItemService;
    private final ItemService itemService;

    public ItemValidationContextService(UserService userService,
                                        ShopItemService shopItemService,
                                        ItemService itemService) {
        this.userService = userService;
        this.shopItemService = shopItemService;
        this.itemService = itemService;
    }

    @Override
    public ItemValidationContext getContext(ItemRequest toValidate) {
        return ItemValidationContext.builder()
                .user(userService.get())
                .shopItem(shopItemService.findByCodeAndQuality(toValidate.getCode(), toValidate.getQuality()).orElse(null))
                .numberOfItems(itemService.count(null))
                .build();
    }
}
