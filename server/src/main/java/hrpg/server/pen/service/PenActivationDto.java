package hrpg.server.pen.service;

import hrpg.server.creature.service.CreatureDto;
import hrpg.server.item.service.ItemDto;
import lombok.Builder;
import lombok.Data;

import java.util.Set;

@Data
@Builder
public class PenActivationDto {
    private ItemDto item;
    private Set<CreatureDto> creatures;
}
