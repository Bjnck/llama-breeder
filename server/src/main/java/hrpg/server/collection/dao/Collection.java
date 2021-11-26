package hrpg.server.collection.dao;

import hrpg.server.common.dao.WithUser;
import hrpg.server.creature.dao.Color;
import lombok.*;

import javax.persistence.*;

@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
@Builder(toBuilder = true)
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "collection")
public class Collection extends WithUser {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Builder.Default
    @Version
    private long version = 0;

    @OneToOne(optional = false)
    @JoinColumn(name = "color_id", nullable = false, updatable = false)
    private Color color;

    @Builder.Default
    @Column(nullable = false)
    private boolean christmas = false;
}
