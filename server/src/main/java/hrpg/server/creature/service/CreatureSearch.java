package hrpg.server.creature.service;

import hrpg.server.creature.type.Sex;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

import java.util.Set;

@Data
@Builder
@AllArgsConstructor
public class CreatureSearch {
    private Integer generation;
    private Sex sex;
    private Boolean inPen;
    private Integer maxMaturity;
    private Boolean pregnant;
    private Integer minPregnancyCount;
    private Set<Long> ids;
}
