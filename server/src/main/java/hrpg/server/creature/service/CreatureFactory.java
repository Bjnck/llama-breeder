package hrpg.server.creature.service;

import javax.validation.constraints.NotNull;
import java.time.LocalDate;

public interface CreatureFactory {
    CreatureDto generateForCapture(int quality, @NotNull LocalDate endDate);
}
