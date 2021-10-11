package hrpg.server.creature.dao;

import lombok.*;

import javax.persistence.*;
import java.time.ZonedDateTime;

import static hrpg.server.creature.type.CreatureConstant.ENERGY_MAX;

@Data
@ToString(exclude = "creature")
@EqualsAndHashCode(exclude = "creature")
@Builder(toBuilder = true)
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "creature_details")
public class CreatureDetails {
    @Id
    private Long creatureId;

    @OneToOne
    @JoinColumn(name = "creature_id")
    @MapsId
    private Creature creature;

    @Builder.Default
    @Version
    private long version = 0;

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
    private Long pregnancyMaleId;

    @Builder.Default
    @Column(nullable = false)
    private ZonedDateTime energyUpdateTime = ZonedDateTime.now();

    @Builder.Default
    @Column(nullable = false)
    private int energy = ENERGY_MAX;
    @Builder.Default
    @Column(nullable = false)
    private int love = 0;
    @Builder.Default
    @Column(nullable = false)
    private int thirst = 0;
    @Builder.Default
    @Column(nullable = false)
    private int hunger = 0;
    @Builder.Default
    @Column(nullable = false)
    private int maturity = 0;
}
