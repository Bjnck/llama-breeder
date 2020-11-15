package hrpg.server.shop.dao;

import hrpg.server.item.type.ItemCode;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.CompoundIndex;
import org.springframework.data.mongodb.core.mapping.Document;

import javax.validation.constraints.NotNull;

@Data
@Builder(toBuilder = true)
@AllArgsConstructor
@NoArgsConstructor
@Document(collection = "shopItems")
@CompoundIndex(def = "{'code' : 1, 'quality' : 1}", unique = true)
public class ShopItem {
    @Id
    private String id;

    @NotNull
    private ItemCode code;
    @NotNull
    private Integer quality;

    @NotNull
    private Integer coins;
}
