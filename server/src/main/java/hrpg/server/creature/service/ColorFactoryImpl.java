package hrpg.server.creature.service;

import hrpg.server.creature.dao.Color;
import hrpg.server.creature.dao.ColorRepository;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Random;

@Component
public class ColorFactoryImpl implements ColorFactory {

    private final ColorRepository colorRepository;

    public ColorFactoryImpl(ColorRepository colorRepository) {
        this.colorRepository = colorRepository;
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
}
