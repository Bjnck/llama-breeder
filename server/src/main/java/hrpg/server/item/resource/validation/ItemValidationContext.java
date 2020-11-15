package hrpg.server.item.resource.validation;

import hrpg.server.shop.service.ShopItemDto;
import hrpg.server.user.service.UserDto;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class ItemValidationContext {
    private UserDto user;
    private ShopItemDto shopItem;
    private long numberOfItems;
}
