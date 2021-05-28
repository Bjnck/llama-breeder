package hrpg.server.pen.resource;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.springframework.hateoas.RepresentationModel;
import org.springframework.hateoas.server.core.Relation;

import java.util.Set;

@Data
@EqualsAndHashCode(callSuper = false)
@Builder
@AllArgsConstructor
@Relation(collectionRelation = "pens")
public class PenResponse extends RepresentationModel<PenResponse> {
    private Long id;

    private Integer size;

    private Set<PenCreatureResponse> creatures;
    private Set<PenItemResponse> items;
}
