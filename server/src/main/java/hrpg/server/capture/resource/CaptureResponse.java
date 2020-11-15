package hrpg.server.capture.resource;

import lombok.*;
import org.springframework.hateoas.RepresentationModel;
import org.springframework.hateoas.server.core.Relation;

import java.time.Instant;

@Data
@EqualsAndHashCode(callSuper = false)
@Builder(toBuilder = true)
@AllArgsConstructor
@NoArgsConstructor
@Relation(collectionRelation = "captures")
public class CaptureResponse extends RepresentationModel<CaptureResponse> {
    private String id;

    private int quality;

    private String creatureId;

    private Instant startTime;
    private Instant endTime;
}
