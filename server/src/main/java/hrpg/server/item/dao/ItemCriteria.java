package hrpg.server.item.dao;

import hrpg.server.item.type.ItemCode;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
@AllArgsConstructor
public class ItemCriteria {
    private ItemCode code;
    private Integer quality;
    private Boolean inPen;
    private Integer maxLife;
}
