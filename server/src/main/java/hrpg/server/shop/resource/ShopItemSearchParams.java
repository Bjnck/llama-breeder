package hrpg.server.shop.resource;

import hrpg.server.item.type.ItemCode;

public interface ShopItemSearchParams {
    ItemCode getCode();
    Integer getQuality();
}
