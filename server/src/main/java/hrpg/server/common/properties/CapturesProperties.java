package hrpg.server.common.properties;

import lombok.Getter;
import lombok.Setter;

import java.time.temporal.ChronoUnit;

@Getter
@Setter
public class CapturesProperties {
    private int timeValue;
    private ChronoUnit timeUnit;
    private int timeValueNet;
    private ChronoUnit timeUnitNet;
    private int timeValueFirst;
    private ChronoUnit timeUnitFirst;
    private int max;
}

