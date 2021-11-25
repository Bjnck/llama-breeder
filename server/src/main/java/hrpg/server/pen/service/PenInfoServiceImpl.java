package hrpg.server.pen.service;

import hrpg.server.common.properties.ParametersProperties;
import hrpg.server.common.properties.PensProperties;
import hrpg.server.pen.resource.PenInfo;
import hrpg.server.pen.service.exception.PriceNotFoundException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class PenInfoServiceImpl implements PenInfoService {
    private static final String PEN = "pen-";
    private static final String SIZE = "size-";

    private final PensProperties pensProperties;

    public PenInfoServiceImpl(ParametersProperties parametersProperties) {
        this.pensProperties = parametersProperties.getPens();
    }

    @Override
    public List<PenInfo> getInfo() {
        List<PenInfo> info = new ArrayList<>();
        pensProperties.getPrices().forEach((key, value) -> {
            PenInfo.PenInfoBuilder builder = PenInfo.builder().count(Integer.parseInt(key.replace(PEN, "")));
            value.forEach((key1, value1) -> {
                if (key1.startsWith(SIZE)) {
                    builder.size(PenInfo.Price.builder()
                            .count(Integer.parseInt(key1.replace(SIZE, "")))
                            .price(value1)
                            .build());
                }
            });
            info.add(builder.build());
        });
        return info;
    }

    @Override
    public int getPurchasePrice(int penCount) throws PriceNotFoundException {
        return getExtendPrice(penCount, 3);
    }

    @Override
    public int getExtendPrice(int penCount, int size) throws PriceNotFoundException {
        return pensProperties.getPrices().entrySet().stream()
                .filter(entry -> entry.getKey().equals(PEN + penCount))
                .map(entry -> entry.getValue().get(SIZE + size))
                .findFirst()
                .orElseThrow(PriceNotFoundException::new);
    }
}
