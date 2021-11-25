package hrpg.server.common.properties;

import lombok.Getter;
import lombok.Setter;

import java.time.temporal.ChronoUnit;
import java.util.Map;

@Getter
@Setter
public class PensProperties {
    private ChronoUnit activationTimeUnit;
    private int activationTimeValue;

    private int itemActivationChance;

    private Map<String, Map<String, Integer>> prices;
}

