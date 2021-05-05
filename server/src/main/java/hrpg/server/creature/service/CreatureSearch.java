package hrpg.server.creature.service;

import hrpg.server.creature.type.Sex;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
@AllArgsConstructor
public class CreatureSearch {
    private Sex sex;
    private Integer generation;
    private String color1;
    private String color2;
    private String gene1;
    private String gene2;
    private Boolean wild;
    private Boolean pregnant;
}
