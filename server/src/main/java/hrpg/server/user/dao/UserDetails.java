package hrpg.server.user.dao;

import lombok.*;

import javax.persistence.*;
import java.time.Instant;

@Data
@ToString(exclude = "user")
@Builder(toBuilder = true)
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "user_details")
public class UserDetails {
    @Id
    private Integer userId;

    @OneToOne
    @JoinColumn(name = "user_id")
    @MapsId
    private User user;

    @Builder.Default
    @Version
    private long version = 0;

    @Builder.Default
    @Column(nullable = false)
    private int level = 0;
    @Builder.Default
    @Column(nullable = false)
    private long coins = 0;

    @Column(name = "last_purchase_timestamp")
    private Instant lastPurchase;
    @Column(name = "last_capture_timestamp")
    private Instant lastCapture;
}
