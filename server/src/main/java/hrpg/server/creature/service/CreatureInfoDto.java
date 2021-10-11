package hrpg.server.creature.service;

import hrpg.server.creature.type.Gene;
import hrpg.server.creature.type.Sex;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CreatureInfoDto {
    private Sex sex;

    private ColorDto color1;
    private ColorDto color2;

    private Gene gene1;
    private Gene gene2;
}

