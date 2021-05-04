package hrpg.server.shop.dao;

import hrpg.server.item.type.ItemCode;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
@AllArgsConstructor
public class ShopItemCriteria {
    private ItemCode code;
    private Integer quality;
}
