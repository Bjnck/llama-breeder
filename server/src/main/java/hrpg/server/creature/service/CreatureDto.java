package hrpg.server.creature.service;

import hrpg.server.creature.type.Gene;
import hrpg.server.creature.type.Sex;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CreatureDto {
    private Long id;

    private String originalUser;

    private int generation;

    private Long parentId1;
    private Long parentId2;

    private String name;
    private Sex sex;

    private ColorDto color1;
    private ColorDto color2;

    private Gene gene1;
    private Gene gene2;

    private boolean wild;

    private int breedingCount;
    private boolean pregnant;
    private LocalDateTime pregnancyStartTime;
    private LocalDateTime pregnancyEndTime;

    private int energy;
    private int love;
    private int thirst;
    private int hunger;
    private int maturity;
}

