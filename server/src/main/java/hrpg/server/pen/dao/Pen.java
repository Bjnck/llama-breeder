package hrpg.server.pen.dao;

import hrpg.server.common.dao.WithUser;
import hrpg.server.creature.dao.Creature;
import hrpg.server.item.dao.Item;
import lombok.*;

import javax.persistence.*;
import java.util.Set;

@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
@Builder(toBuilder = true)
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "pen")
public class Pen extends WithUser {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Builder.Default
    @Version
    private long version = 0;

    @Builder.Default
    @Column(nullable = false)
    private int size = 3;

    @Singular
    @ManyToMany
    @JoinTable(name = "pen_item",
            joinColumns = @JoinColumn(name = "pen_id", referencedColumnName = "id"),
            inverseJoinColumns = @JoinColumn(name = "item_id", referencedColumnName = "id"))
    private Set<Item> items;

    @Singular
    @ManyToMany
    @JoinTable(name = "pen_creature",
            joinColumns = @JoinColumn(name = "pen_id", referencedColumnName = "id"),
            inverseJoinColumns = @JoinColumn(name = "creature_id", referencedColumnName = "id"))
    private Set<Creature> creatures;
}
