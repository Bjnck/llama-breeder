package hrpg.server.common.util;

import javax.validation.constraints.NotNull;
import java.time.Duration;
import java.time.ZonedDateTime;
import java.time.temporal.ChronoUnit;

public class DurationUtil {
    public static long getDurationDividedBy(@NotNull ZonedDateTime fromTime, @NotNull ZonedDateTime toTime, int dividedByValue,
                                   @NotNull ChronoUnit dividedByUnit) {
        Duration duration = Duration.between(fromTime, toTime);
        return duration.dividedBy(Duration.of(dividedByValue, dividedByUnit));
    }
}
