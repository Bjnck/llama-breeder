package hrpg.server.capture.dao;

import hrpg.server.common.dao.WithUser;
import lombok.*;

import javax.persistence.*;
import java.time.LocalDateTime;

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

    private Long creatureId;

    @Builder.Default
    @Column(nullable = false, updatable = false)
    private Integer quality = 0;
    @Column(updatable = false)
    private Integer baitGeneration;

    @Column(nullable = false, updatable = false)
    private LocalDateTime startTime;
    @Column(nullable = false, updatable = false)
    private LocalDateTime endTime;
}
