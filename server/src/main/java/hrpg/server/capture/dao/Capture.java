package hrpg.server.capture.dao;

import hrpg.server.common.dao.WithUser;
import hrpg.server.creature.dao.CreatureInfo;
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
@Table(name = "capture")
public class Capture extends WithUser {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Builder.Default
    @Version
    private long version = 0;

    @OneToOne(cascade = CascadeType.ALL, orphanRemoval = true)
    @JoinColumn(name = "creature_info_id")
    private CreatureInfo creatureInfo;

    @Builder.Default
    @Column(nullable = false, updatable = false)
    private Integer quality = 0;

    @Column(nullable = false, updatable = false)
    private ZonedDateTime startTime;
    @Column(nullable = false, updatable = false)
    private ZonedDateTime endTime;
}
