package hrpg.server.creature.dao;

import hrpg.server.creature.type.Sex;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
@AllArgsConstructor
public class CreatureCriteria {
    private Sex sex;
    private Integer generation;
    private String color1;
    private String color2;
    private String gene1;
    private String gene2;
    private Boolean wild;
    private Boolean pregnant;
}
