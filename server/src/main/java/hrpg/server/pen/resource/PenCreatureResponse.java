package hrpg.server.pen.resource;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.springframework.hateoas.RepresentationModel;

@Data
@EqualsAndHashCode(callSuper = false)
@Builder
@AllArgsConstructor
public class PenCreatureResponse extends RepresentationModel<PenCreatureResponse> {
    private Long id;
}
