package hrpg.server.common.properties;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class GenesProperties {
    private int chanceQuality0;
    private int chanceQuality2;
    private int chanceQuality5;
    private int chanceQuality8;

    private int chanceLove;
    private int chanceHunger;
    private int chanceThirst;
    private int chanceFertile;

    private int specialChanceQuality2;
    private int specialChanceQuality5;
    private int specialChanceQuality8;
}

