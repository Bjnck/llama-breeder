package hrpg.server.creature.resource;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
@Builder
@AllArgsConstructor
public class CreatureInfo {
    private List<Price> prices;
    private List<Color> colors;

    @Data
    @Builder
    @AllArgsConstructor
    public static class Price {
        private int generation;
        private int price;
    }

    @Data
    @Builder
    @AllArgsConstructor
    public static class Color {
        private String code;
        private String name;
        private int generation;
        private ColorParent parent1;
        private ColorParent parent2;
    }

    @Data
    @Builder
    @AllArgsConstructor
    public static class ColorParent {
        private String code1;
        private String code2;
    }
}
