package hrpg.server.item.resource;

import hrpg.server.item.type.ItemCode;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.springframework.hateoas.RepresentationModel;
import org.springframework.hateoas.server.core.Relation;

@Data
@EqualsAndHashCode(callSuper = false)
@Builder(toBuilder = true)
@AllArgsConstructor
@Relation(collectionRelation = "items")
public class ItemResponse extends RepresentationModel<ItemResponse> {
    private String id;

    private ItemCode code;
    private int quality;

    private int life;
}
