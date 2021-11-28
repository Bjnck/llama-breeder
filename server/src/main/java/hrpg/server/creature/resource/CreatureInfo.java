package hrpg.server.creature.resource;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
@AllArgsConstructor
public class CreatureInfo {
    private int generation;
    private int price;
}
