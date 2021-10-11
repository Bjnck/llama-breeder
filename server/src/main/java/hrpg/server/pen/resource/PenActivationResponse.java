package hrpg.server.pen.resource;

import hrpg.server.creature.resource.CreatureResponse;
import hrpg.server.item.resource.ItemResponse;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.Set;

@Data
@EqualsAndHashCode(callSuper = false)
@Builder
@AllArgsConstructor
public class PenActivationResponse {
    private ItemResponse item;
    private Set<CreatureResponse> creatures;
}
