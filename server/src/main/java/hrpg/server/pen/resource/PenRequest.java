package hrpg.server.pen.resource;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import java.util.Set;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class PenRequest {
    @NotNull
    @Min(3)
    @Max(20)
    private Integer size;

    private Set<PenCreatureRequest> creatures;
    private Set<PenItemRequest> items;
}
