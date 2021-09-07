package hrpg.server.creature.service;

import hrpg.server.creature.service.exception.MaxCreaturesException;

import javax.validation.constraints.NotNull;
import java.time.LocalDate;

public interface CreatureFactory {
    CreatureDto generateForCapture(int userLevel, int netQuality, Integer baitGeneration, @NotNull LocalDate captureEndDate)
            throws MaxCreaturesException;
}
