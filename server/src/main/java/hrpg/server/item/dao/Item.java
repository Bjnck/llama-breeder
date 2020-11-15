package hrpg.server.item.dao;

import hrpg.server.common.dao.WithUser;
import hrpg.server.item.type.ItemCode;
import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.CompoundIndex;
import org.springframework.data.mongodb.core.mapping.Document;

import javax.validation.constraints.NotNull;

@Data
@EqualsAndHashCode(callSuper = true)
@Builder(toBuilder = true)
@AllArgsConstructor
@NoArgsConstructor
@Document(collection = "items")
@CompoundIndex(def = "{'code' : 1, 'quality' : 1}", unique = true)
public class Item extends WithUser {
    @Id
    private String id;

    @NotNull
    private ItemCode code;
    @NotNull
    private Integer quality;

    @NotNull
    private Integer life;
}
