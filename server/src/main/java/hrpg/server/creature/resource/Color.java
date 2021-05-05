package hrpg.server.creature.resource;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Data
@Builder(toBuilder = true)
@AllArgsConstructor
public class Color {
    private String code;
    private String name;

    private int red;
    private int green;
    private int blue;
}
