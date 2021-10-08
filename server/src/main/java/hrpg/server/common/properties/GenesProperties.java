package hrpg.server.common.properties;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class GenesProperties {
    private int chanceQuality0;
    private int chanceQuality1;
    private int chanceQuality2;
    private int chanceQuality3;

    private int chanceLove;
    private int chanceHunger;
    private int chanceThirst;
    private int chanceFertile;

    private int specialChanceQuality1;
    private int specialChanceQuality2;
    private int specialChanceQuality3;
}

