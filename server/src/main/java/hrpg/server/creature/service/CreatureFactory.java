package hrpg.server.creature.service;

import hrpg.server.creature.service.exception.CreatureNotFoundException;
import hrpg.server.creature.service.exception.MaxCreaturesException;

import java.util.List;

public interface CreatureFactory {
    CreatureDto generateForCapture(int userLevel, int netQuality) throws MaxCreaturesException;

    List<CreatureDto> generateForBirth(long id) throws MaxCreaturesException, CreatureNotFoundException;
}
