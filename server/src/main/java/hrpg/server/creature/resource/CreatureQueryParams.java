package hrpg.server.creature.resource;

import hrpg.server.creature.type.Sex;
import lombok.Data;

import java.util.Set;

@Data
public class CreatureQueryParams {
    private Integer generation;
    private Sex sex;
    private Boolean inPen;
    private Integer maxMaturity;
    private Boolean pregnant;
    private Integer minPregnancyCount;
    private Set<Long> ids;

    public void setInpen(Boolean inPen) {
        this.inPen = inPen;
    }

    public void setMaxmaturity(Integer maxMaturity) {
        this.maxMaturity = maxMaturity;
    }

    public void setMinpregnancycount(Integer minPregnancyCount) {
        this.minPregnancyCount = minPregnancyCount;
    }
}
