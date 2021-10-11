package hrpg.server.creature.service;

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

    private CreatureInfoDto info;

    private CreatureInfoDto parentInfo1;
    private CreatureInfoDto parentInfo2;

    private String name;

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

