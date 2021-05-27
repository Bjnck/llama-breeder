package hrpg.server.creature.service;

import hrpg.server.creature.service.exception.MaxCreaturesReachedException;

import javax.validation.constraints.NotNull;
import java.time.LocalDate;

public interface CreatureFactory {
    CreatureDto generateForCapture(int userLevel, int nestQuality, Integer baitGeneration, @NotNull LocalDate captureEndDate)
            throws MaxCreaturesReachedException;
}
