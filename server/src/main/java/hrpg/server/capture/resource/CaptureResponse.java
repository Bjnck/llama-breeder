package hrpg.server.capture.resource;

import hrpg.server.creature.resource.Color;
import hrpg.server.creature.type.Sex;
import lombok.*;
import org.springframework.hateoas.RepresentationModel;
import org.springframework.hateoas.server.core.Relation;

import java.time.ZonedDateTime;

@Data
@EqualsAndHashCode(callSuper = false)
@Builder(toBuilder = true)
@AllArgsConstructor
@NoArgsConstructor
@Relation(collectionRelation = "captures")
public class CaptureResponse extends RepresentationModel<CaptureResponse> {
    private Long id;

    private int quality;
    private Integer bait;

    private Long creatureId;
    private Sex sex;
    private Color color;
    private String gene;

    private ZonedDateTime startTime;
    private ZonedDateTime endTime;
}
