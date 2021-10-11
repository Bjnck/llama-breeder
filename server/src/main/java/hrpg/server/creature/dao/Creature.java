package hrpg.server.creature.dao;

import hrpg.server.common.dao.WithUser;
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

    @OneToOne(cascade = CascadeType.ALL, orphanRemoval = true)
    @JoinColumn(name = "info_id", updatable = false)
    private CreatureInfo info;

    @OneToOne(cascade = CascadeType.ALL, orphanRemoval = true)
    @JoinColumn(name = "parent_one_info_id", updatable = false)
    private CreatureInfo parentInfo1;
    @OneToOne(cascade = CascadeType.ALL, orphanRemoval = true)
    @JoinColumn(name = "parent_two_info_id", updatable = false)
    private CreatureInfo parentInfo2;

    @Builder.Default
    @Column(nullable = false)
    private String name = "Llama";

    @OneToOne(mappedBy = "creature", cascade = CascadeType.ALL, orphanRemoval = true)
    private CreatureDetails details;
}
