package hrpg.server.creature.resource;

import lombok.*;

@Data
@EqualsAndHashCode(callSuper = false)
@Builder(toBuilder = true)
@AllArgsConstructor
@NoArgsConstructor
public class CreatureDeleteResponse  {
    private int coins;
}
