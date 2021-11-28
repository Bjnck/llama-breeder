package hrpg.server.creature.dao;

import hrpg.server.common.dao.WithUser;
import lombok.*;

import javax.persistence.*;
import java.time.LocalDate;
import java.time.ZonedDateTime;

import static hrpg.server.creature.type.CreatureConstant.*;
import static javax.persistence.CascadeType.ALL;
import static javax.persistence.FetchType.EAGER;

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

    @OneToOne(cascade = ALL, orphanRemoval = true, fetch = EAGER)
    @JoinColumn(name = "info_id", updatable = false)
    private CreatureInfo info;

    @OneToOne(cascade = ALL, orphanRemoval = true, fetch = EAGER)
    @JoinColumn(name = "parent_one_info_id", updatable = false)
    private CreatureInfo parentInfo1;
    @OneToOne(cascade = ALL, orphanRemoval = true, fetch = EAGER)
    @JoinColumn(name = "parent_two_info_id", updatable = false)
    private CreatureInfo parentInfo2;

    @Builder.Default
    @Column(nullable = false)
    private String name = "Llama";

    @Builder.Default
    @Column(nullable = false, updatable = false)
    private boolean wild = false;

    private ZonedDateTime penActivationTime;

    @Builder.Default
    @Column(nullable = false)
    private boolean pregnant = false;
    @Builder.Default
    @Column(nullable = false)
    private int breedingCount = 0;
    private ZonedDateTime pregnancyStartTime;
    private ZonedDateTime pregnancyEndTime;
    @OneToOne(cascade = ALL, orphanRemoval = true, fetch = EAGER)
    @JoinColumn(name = "pregnancy_male_info_id")
    private CreatureInfo pregnancyMaleInfo;

    @Builder.Default
    @Column(nullable = false)
    private ZonedDateTime energyUpdateTime = ZonedDateTime.now();

    @Builder.Default
    @Column(nullable = false)
    private int energy = ENERGY_MAX;
    @Builder.Default
    @Column(nullable = false)
    private int love = STATS_MIN;
    @Builder.Default
    @Column(nullable = false)
    private int thirst = STATS_MIN;
    @Builder.Default
    @Column(nullable = false)
    private int hunger = STATS_MIN;
    @Builder.Default
    @Column(nullable = false)
    private int maturity = MATURITY_MIN;
}
