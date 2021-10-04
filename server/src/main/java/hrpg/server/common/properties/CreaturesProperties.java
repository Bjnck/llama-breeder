package hrpg.server.common.properties;

import lombok.Getter;
import lombok.Setter;

import java.time.temporal.ChronoUnit;
import java.util.Map;

@Getter
@Setter
public class CreaturesProperties {
    private int max;
    private int chanceFemale;
    private ChronoUnit energyTimeUnit;
    private int energyTimeValue;
    private Map<String, Integer> price;
}

