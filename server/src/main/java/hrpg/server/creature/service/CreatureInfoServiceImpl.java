package hrpg.server.creature.service;

import hrpg.server.common.properties.CreaturesProperties;
import hrpg.server.common.properties.ParametersProperties;
import hrpg.server.creature.resource.CreatureInfo;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

import static hrpg.server.common.properties.PropertiesConstant.GENERATION_KEY_PROPERTIES_PREFIX;

@Service
public class CreatureInfoServiceImpl implements CreatureInfoService {
    private final CreaturesProperties creaturesProperties;

    public CreatureInfoServiceImpl(ParametersProperties parametersProperties) {
        this.creaturesProperties = parametersProperties.getCreatures();
    }

    @Override
    public List<CreatureInfo> getInfo() {
        return creaturesProperties.getPrices().entrySet().stream()
                .map(entry -> CreatureInfo.builder()
                        .generation(Integer.parseInt(
                                entry.getKey().replace(GENERATION_KEY_PROPERTIES_PREFIX, "")))
                        .price(entry.getValue())
                        .build())
                .collect(Collectors.toList());
    }
}
