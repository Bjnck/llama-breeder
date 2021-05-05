package hrpg.server.creature.resource;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Data
@Builder(toBuilder = true)
@AllArgsConstructor
public class Statistics {
    private int energy;
    private int love;
    private int thirst;
    private int hunger;
    private int maturity;
}
