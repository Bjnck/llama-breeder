package hrpg.server.capture.resource;

import lombok.*;
import org.springframework.hateoas.RepresentationModel;
import org.springframework.hateoas.server.core.Relation;

import java.time.LocalDateTime;

@Data
@EqualsAndHashCode(callSuper = false)
@Builder(toBuilder = true)
@AllArgsConstructor
@NoArgsConstructor
@Relation(collectionRelation = "captures")
public class CaptureResponse extends RepresentationModel<CaptureResponse> {
    private Long id;

    private int quality;

    private Long creatureId;

    private LocalDateTime startTime;
    private LocalDateTime endTime;
}
