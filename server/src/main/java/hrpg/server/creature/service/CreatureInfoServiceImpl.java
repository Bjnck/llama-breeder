package hrpg.server.creature.service;

import hrpg.server.common.properties.CreaturesProperties;
import hrpg.server.common.properties.ParametersProperties;
import hrpg.server.creature.dao.ColorRepository;
import hrpg.server.creature.resource.CreatureInfo;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import static hrpg.server.common.properties.PropertiesConstant.GENERATION_KEY_PROPERTIES_PREFIX;

@Service
public class CreatureInfoServiceImpl implements CreatureInfoService {
    private final CreaturesProperties creaturesProperties;
    private final ColorRepository colorRepository;

    public CreatureInfoServiceImpl(ParametersProperties parametersProperties) {
        this.creaturesProperties = parametersProperties.getCreatures();
    }

    @Override
    public CreatureInfo getInfo() {
        return CreatureInfo.builder()
                .prices(getPrices())
                .colors(getColors())
                .build();
    }

    private List<CreatureInfo.Price> getPrices() {
        return creaturesProperties.getPrices().entrySet().stream()
                .map(entry -> CreatureInfo.Price.builder()
                        .generation(Integer.parseInt(
                                entry.getKey().replace(GENERATION_KEY_PROPERTIES_PREFIX, "")))
                        .price(entry.getValue())
                        .build())
                .collect(Collectors.toList());
    }

    private List<CreatureInfo.Color> getColors() {

        List<CreatureInfo.Color> colors = new ArrayList<>();
        colorRepository.findAll().forEach(color -> colors.add(CreatureInfo.Color.builder()
                .code(color.getCode())
                .generation(color.getGeneration())
                .name(color.getName())
                .parent1(color.getParentCode())

                .build()));


    }

    private
}
