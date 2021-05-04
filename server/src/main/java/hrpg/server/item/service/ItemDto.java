package hrpg.server.item.service;

import hrpg.server.item.type.ItemCode;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Data
@Builder(toBuilder = true)
@AllArgsConstructor
public class ItemDto {
    private Long id;

    private ItemCode code;
    private int quality;

    private int life;
}
