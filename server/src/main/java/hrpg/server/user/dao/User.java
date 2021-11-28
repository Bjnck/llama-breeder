package hrpg.server.user.dao;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Data
@Builder(toBuilder = true)
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "user")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Builder.Default
    @Column(nullable = false)
    private String name = "Breeder";

    @Column(nullable = false)
    private String uid;
    @Column(nullable = false)
    private String issuer;
    private String email;

    @Builder.Default
    @Column(nullable = false)
    private boolean deleted = false;

    @OneToOne(mappedBy = "user", cascade = CascadeType.ALL, orphanRemoval = true)
    private UserDetails details;
}
