package hrpg.server.capture.dao;

import hrpg.server.common.dao.WithUser;
import lombok.*;

import javax.persistence.*;

@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
@Builder(toBuilder = true)
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "bait")
public class Bait extends WithUser {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Builder.Default
    @Version
    private long version = 0;

    @Column(nullable = false, updatable = false)
    private Integer generation;

    @Builder.Default
    @Column(nullable = false)
    private int count = 0;
}
