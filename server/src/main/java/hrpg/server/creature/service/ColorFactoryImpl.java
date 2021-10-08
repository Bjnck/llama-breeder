package hrpg.server.creature.service;

import hrpg.server.common.properties.CreaturesProperties;
import hrpg.server.common.properties.ParametersProperties;
import hrpg.server.creature.dao.Color;
import hrpg.server.creature.dao.ColorRepository;
import org.springframework.data.util.Pair;
import org.springframework.stereotype.Component;

import javax.validation.constraints.NotNull;
import java.util.*;
import java.util.stream.Collectors;

@Component
public class ColorFactoryImpl implements ColorFactory {

    private final ColorRepository colorRepository;
    private final CreaturesProperties creaturesProperties;

    public ColorFactoryImpl(ColorRepository colorRepository,
                            ParametersProperties parametersProperties) {
        this.colorRepository = colorRepository;
        this.creaturesProperties = parametersProperties.getCreatures();
    }

    @Override
    public Color getForCapture(String previousColorCode) {
        List<Color> colors = colorRepository.findAllByGeneration(1);

        int random = new Random().nextInt(colors.size());
        Color color = colors.get(random);
        if (previousColorCode != null && previousColorCode.equals(color.getCode()))
            color = colors.get((random + 1) % colors.size());

        return color;
    }

    @Override
    public Pair<Color, Optional<Color>> getForBirth(@NotNull Color parent1Color1, Color parent1Color2,
                                                    @NotNull Color parent2Color1, Color parent2Color2) {
        //check if cross-breed
        //is bi-color, is of same generation, is different combo
        //generation < 7 //todo remove this and manage gen 8
        if (parent1Color2 != null && parent2Color2 != null &&
                parent1Color1.getGeneration() == parent1Color2.getGeneration() &&
                parent1Color1.getGeneration() == parent2Color1.getGeneration() &&
                parent1Color1.getGeneration() == parent2Color2.getGeneration() &&
                (!parent1Color1.getId().equals(parent2Color1.getId()) ||
                        !parent1Color2.getId().equals(parent2Color2.getId())) &&
                parent1Color1.getGeneration() < 7) {
            if (new Random().nextInt(100) < creaturesProperties.getChanceCrossBreed()) {
                int parentCode = toInt(parent1Color1.getCode()) + toInt(parent1Color2.getCode()) +
                        toInt(parent2Color1.getCode()) + toInt(parent2Color2.getCode());
                Color color = colorRepository.findByParentCode(toCode(parentCode)).orElseThrow(RuntimeException::new);
                return Pair.of(color, Optional.empty());
            }
        }

        List<Color> colors = Arrays.asList(parent1Color1, parent2Color1);
        if (parent1Color2 != null) colors.add(parent1Color2);
        if (parent2Color2 != null) colors.add(parent2Color2);
        Collections.shuffle(colors);

        List<Color> selectedColors = new ArrayList<>();
        selectedColors.add(colors.get(0));
        //check if bi-color and add second color
        if (new Random().nextInt(100) < creaturesProperties.getChanceBiColor())
            selectedColors.add(colors.get(1));

        //distinct colors and return
        selectedColors = selectedColors.stream()
                .distinct()
                .sorted(Comparator.comparingInt(color -> toInt(color.getCode())))
                .collect(Collectors.toList());
        return Pair.of(selectedColors.get(0),
                selectedColors.size() > 1 ? Optional.of(selectedColors.get(1)) : Optional.empty());
    }

    private int toInt(String code) {
        return Integer.parseInt(code.substring(1), 16);
    }

    private String toCode(int combo) {
        return "#" + Integer.toHexString(combo);
    }
}
