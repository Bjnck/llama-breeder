package hrpg.server.item.dao;

import hrpg.server.common.dao.WithUser;
import hrpg.server.item.type.ItemCode;
import lombok.*;

import javax.persistence.*;
import java.time.ZonedDateTime;

@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
@Builder(toBuilder = true)
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "item")
public class Item extends WithUser {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Builder.Default
    @Version
    private long version = 0;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, updatable = false)
    private ItemCode code;
    @Column(nullable = false, updatable = false)
    private Integer quality;

    @Builder.Default
    @Column(nullable = false)
    private Integer life = 100;

    private ZonedDateTime penActivationTime;
}
