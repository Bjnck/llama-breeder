package hrpg.server.common.properties;

import lombok.Getter;
import lombok.Setter;

import java.time.temporal.ChronoUnit;
import java.util.Map;

import static hrpg.server.common.properties.PropertiesConstant.GENERATION_KEY_PROPERTIES_PREFIX;

@Getter
@Setter
public class CreaturesProperties {
    private int max;

    private int maxBabies;

    private int chanceFemale;
    private int chanceCrossBreed;
    private int chanceBiColor;

    private ChronoUnit energyTimeUnit;
    private int energyTimeValue;

    private ChronoUnit pregnancyTimeUnit;
    private Map<String, Integer> pregnancyTimeValues;

    public int getPregnancyTimeValue(int generation) {
        return pregnancyTimeValues.get(GENERATION_KEY_PROPERTIES_PREFIX + generation);
    }

    private Map<String, Integer> energyIncrements;

    public int getEnergyIncrement(int generation) {
        return energyIncrements.get(GENERATION_KEY_PROPERTIES_PREFIX + generation);
    }

    private Map<String, Integer> maturityIncrements;

    public int getMaturityIncrement(int generation) {
        return maturityIncrements.get(GENERATION_KEY_PROPERTIES_PREFIX + generation);
    }

    private Map<String, Integer> statsIncrements;

    public int getStatsIncrement(int generation) {
        return statsIncrements.get(GENERATION_KEY_PROPERTIES_PREFIX + generation);
    }

    private Map<String, Integer> prices;

    public int getPrice(int generation) {
        return prices.get(GENERATION_KEY_PROPERTIES_PREFIX + generation);
    }
}

