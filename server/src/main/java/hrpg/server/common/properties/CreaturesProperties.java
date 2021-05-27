package hrpg.server.common.properties;

import lombok.Getter;
import lombok.Setter;

import java.util.Map;

@Getter
@Setter
public class CreaturesProperties {
    private int max;
    private int chanceFemale;
    private Map<String, Integer> price;
}

