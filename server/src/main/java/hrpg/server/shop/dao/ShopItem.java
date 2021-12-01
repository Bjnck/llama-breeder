package hrpg.server.shop.dao;

import hrpg.server.item.type.ItemCode;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Data
@Builder(toBuilder = true)
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "shop")
@Entity
public class ShopItem {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, updatable = false)
    private ItemCode code;
    @Column(nullable = false, updatable = false)
    private Integer quality;

    @Column(nullable = false, updatable = false)
    private Integer availability;

    @Column(nullable = false, updatable = false)
    private Integer coins;
}
