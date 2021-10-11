package hrpg.server.creature.resource;

import hrpg.server.creature.type.Sex;
import lombok.*;
import org.springframework.hateoas.RepresentationModel;
import org.springframework.hateoas.server.core.Relation;

import java.time.LocalDateTime;

@Data
@EqualsAndHashCode(callSuper = false)
@Builder(toBuilder = true)
@AllArgsConstructor
@NoArgsConstructor
@Relation(collectionRelation = "creatures")
public class CreatureResponse extends RepresentationModel<CreatureResponse> {
    private Long id;

    private String originalUser;

    private int generation;
    private Sex sex;
    private String name;

    private Parents parents;
    private Colors colors;
    private Genes genes;

    private boolean wild;

    private int breedingCount;
    private boolean pregnant;
    private LocalDateTime pregnancyStartTime;
    private LocalDateTime pregnancyEndTime;

    private Statistics statistics;
}
