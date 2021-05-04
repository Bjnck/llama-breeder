package hrpg.server.shop.resource;

import hrpg.server.item.type.ItemCode;

public interface ShopItemQueryParams {
    ItemCode getCode();
    Integer getQuality();
}
