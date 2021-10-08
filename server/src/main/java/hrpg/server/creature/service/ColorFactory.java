package hrpg.server.creature.service;

import hrpg.server.creature.dao.Color;
import org.springframework.data.util.Pair;

import javax.validation.constraints.NotNull;
import java.util.Optional;

public interface ColorFactory {

    Color getForCapture(String previousColorCode);

    Pair<Color, Optional<Color>> getForBirth(@NotNull Color parent1Color1, Color parent1Color2,
                                             @NotNull Color parent2Color1, Color parent2Color2);
}
