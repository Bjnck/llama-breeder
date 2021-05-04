package hrpg.server.item.service;

import hrpg.server.item.type.ItemCode;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
@AllArgsConstructor
public class ItemSearch {
    private ItemCode code;
    private Integer quality;
}
