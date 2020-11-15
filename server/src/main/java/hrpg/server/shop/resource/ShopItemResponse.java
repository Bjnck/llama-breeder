package hrpg.server.shop.resource;

import hrpg.server.item.type.ItemCode;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import org.springframework.hateoas.RepresentationModel;
import org.springframework.hateoas.server.core.Relation;

@Data
@Builder(toBuilder = true)
@AllArgsConstructor
@Relation(collectionRelation = "items")
public class ShopItemResponse extends RepresentationModel<ShopItemResponse> {
    private ItemCode code;
    private int quality;

    private int coins;
}
