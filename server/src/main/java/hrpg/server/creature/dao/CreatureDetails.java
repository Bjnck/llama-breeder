package hrpg.server.creature.dao;

import lombok.*;

import javax.persistence.*;
import java.time.LocalDateTime;

@Data
@ToString(exclude = "creature")
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

    @Column(nullable = false, updatable = false)
    private boolean wild;

    @Builder.Default
    @Column(nullable = false)
    private boolean pregnant = false;
    private LocalDateTime pregnancyStartTime;
    private LocalDateTime pregnancyEndTime;

    @Builder.Default
    @Column(nullable = false)
    private int energy = 100;
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
