package hrpg.server.creature.service;

import hrpg.server.creature.type.Sex;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

import java.time.ZonedDateTime;

@Data
@Builder
@AllArgsConstructor
public class CreatureSearch {
    private Integer generation;
    private Sex sex;
    private Boolean inPen;
    private Integer minLove;
    private Integer maxMaturity;
    private ZonedDateTime minPregnancyEndTime;
    private ZonedDateTime maxPregnancyEndTime;
    private Boolean pregnant;
    private Integer minPregnancyCount;
}
