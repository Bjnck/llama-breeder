package hrpg.server.item.resource;

import hrpg.server.item.type.ItemCode;

public interface ItemQueryParams {
    ItemCode getCode();
    Integer getQuality();
}
