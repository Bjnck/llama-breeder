package hrpg.server.common;

import hrpg.server.creature.dao.Color;
import hrpg.server.creature.dao.ColorRepository;
import org.springframework.context.annotation.Configuration;

import java.util.Arrays;
import java.util.List;
import java.util.Random;

@Configuration
public class ColorConfig {

    private ColorRepository colorRepository;

    public ColorConfig(ColorRepository colorRepository) {
        this.colorRepository = colorRepository;

        if (colorRepository.countByGenerationAndParentCodeIsNull(8) > 0) {
            getCombos(7).forEach(this::fillParent);
        }
    }

    private void fillParent(int bwgCombo) {
        for (int i = 1; i < 7; i ++) {
            fillParent(bwgCombo, i);
        }
    }

    private void fillParent(int bwgCombo, int generation) {
        getCombos(generation).forEach(genCombo -> {
            List<Color> colorsToFill = colorRepository.findAllByGenerationAndParentCodeIsNull(8);
            Color colorToFill = colorsToFill.get(new Random().nextInt(colorsToFill.size()));
            colorToFill.setParentCode(toCode(bwgCombo + genCombo));
            colorRepository.save(colorToFill);
        });
    }

    private List<Integer> getCombos(int generation) {
        List<Color> colors = colorRepository.findAllByGeneration(generation);
        assert (colors.size() == 3);
        return Arrays.asList(
                toInt(colors.get(0).getCode()) + toInt(colors.get(1).getCode()),
                toInt(colors.get(0).getCode()) + toInt(colors.get(2).getCode()),
                toInt(colors.get(1).getCode()) + toInt(colors.get(2).getCode()));
    }

    private int toInt(String code) {
        return Integer.parseInt(code.substring(1), 16);
    }

    private String toCode(int combo) {
        return "#" + Integer.toHexString(combo);
    }
}
