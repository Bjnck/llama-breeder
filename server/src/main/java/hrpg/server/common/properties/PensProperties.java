package hrpg.server.common.properties;

import lombok.Getter;
import lombok.Setter;

import java.util.Map;

@Getter
@Setter
public class PensProperties {
    private int itemActivationChance;

    private Map<String, Integer> price;
}
