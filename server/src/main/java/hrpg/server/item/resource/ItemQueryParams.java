package hrpg.server.item.resource;

import hrpg.server.item.type.ItemCode;
import lombok.Data;

@Data
public class ItemQueryParams {
    private ItemCode code;
    private Integer quality;
    private Boolean inPen;
    private Integer maxLife;

    public void setInpen(Boolean inPen) {
        this.inPen = inPen;
    }

}
