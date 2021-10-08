package hrpg.server.common.properties;

import lombok.Getter;
import lombok.Setter;

import java.time.temporal.ChronoUnit;
import java.util.Map;

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
    private int pregnancyTimeValue;

    private Map<String, Integer> price;
}

