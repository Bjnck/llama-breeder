package hrpg.server.shop.service;

import hrpg.server.item.type.ItemCode;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Data
@Builder(toBuilder = true)
@AllArgsConstructor
public class ShopItemDto {
    private Integer id;

    private ItemCode code;
    private int quality;

    private int availability;

    private int coins;
}
