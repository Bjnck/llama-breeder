package hrpg.server.creature.dao;

import hrpg.server.common.dao.WithUser;
import hrpg.server.creature.type.Sex;
import lombok.*;

import javax.persistence.*;
import java.time.LocalDate;

@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
@Builder(toBuilder = true)
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "creature")
public class Creature extends WithUser {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Builder.Default
    @Version
    private long version = 0;

    @Column(nullable = false, updatable = false)
    private Integer originalUserId;

    @Column(nullable = false, updatable = false)
    private LocalDate createDate;

    @Builder.Default
    @Column(nullable = false, updatable = false)
    private int generation = 1;

    @Column(name = "parent_one_id", updatable = false)
    private Long parentId1;
    @Column(name = "parent_two_id", updatable = false)
    private Long parentId2;

    private String name;
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

    @OneToOne(mappedBy = "creature", cascade = CascadeType.ALL, orphanRemoval = true)
    private CreatureDetails details;
}
