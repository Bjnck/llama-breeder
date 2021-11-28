package hrpg.server.pen.resource;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.Singular;

import java.util.List;

@Data
@Builder
@AllArgsConstructor
public class PenInfo {
    private int count;
    @Singular
    private List<Price> sizes;

    @Data
    @Builder
    @AllArgsConstructor
    public static class Price {
        private int count;
        private int price;
    }
}
