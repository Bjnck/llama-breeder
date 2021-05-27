package hrpg.server.pen.service;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Set;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PenDto {
    private Long id;

    private Integer size;

    private Set<Long> itemIds;
    private Set<Long> creatureIds;
}
