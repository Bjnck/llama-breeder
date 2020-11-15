package hrpg.server.shop.service;

import hrpg.server.item.type.ItemCode;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
@AllArgsConstructor
public class ShopItemSearch {
    private ItemCode code;
    private Integer quality;
}
