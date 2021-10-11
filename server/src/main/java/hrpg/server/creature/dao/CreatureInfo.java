package hrpg.server.creature.dao;

import hrpg.server.creature.type.Sex;
import lombok.*;

import javax.persistence.*;

@Data
@EqualsAndHashCode
@Builder(toBuilder = true)
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "creature_info")
public class CreatureInfo {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, updatable = false)
    private Sex sex;

    @OneToOne(optional = false)
    @JoinColumn(name = "color_one_id", nullable = false, updatable = false)
    private Color color1;
    @OneToOne
    @JoinColumn(name = "color_two_id", updatable = false)
    private Color color2;

    @OneToOne
    @JoinColumn(name = "gene_one_id", updatable = false)
    private Gene gene1;
    @OneToOne
    @JoinColumn(name = "gene_two_id", updatable = false)
    private Gene gene2;
}
