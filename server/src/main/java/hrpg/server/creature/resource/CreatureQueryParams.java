package hrpg.server.creature.resource;

import hrpg.server.creature.type.Sex;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.ZonedDateTime;

@Data
public class CreatureQueryParams {
    private Integer generation;
    private Sex sex;
    private Boolean inPen;
    private Integer minLove;
    private Integer maxMaturity;
    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME)
    private ZonedDateTime minPregnancyEndTime;
    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME)
    private ZonedDateTime maxPregnancyEndTime;
    private Boolean pregnant;
    private Integer minPregnancyCount;

    public void setInpen(Boolean inPen) {
        this.inPen = inPen;
    }

    public void setminlove(Integer minLove) {
        this.minLove = minLove;
    }

    public void setmaxmaturity(Integer maxMaturity) {
        this.maxMaturity = maxMaturity;
    }

    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME)
    public void setminpregnancyendtime(ZonedDateTime minPregnancyEndTime) {
        this.minPregnancyEndTime = minPregnancyEndTime;
    }

    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME)
    public void setmaxpregnancyendtime(ZonedDateTime maxPregnancyEndTime) {
        this.maxPregnancyEndTime = maxPregnancyEndTime;
    }

    public void setminpregnancycount(Integer minPregnancyCount) {
        this.minPregnancyCount = minPregnancyCount;
    }
}
