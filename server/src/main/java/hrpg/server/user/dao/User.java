package hrpg.server.user.dao;

import lombok.*;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

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

    private String name;

    @Singular
    @ElementCollection(fetch = FetchType.EAGER)
    @CollectionTable(name = "user_registration", joinColumns = @JoinColumn(name = "user_id"))
    @Column(name = "registration_Key")
    private Set<String> registrationKeys = new HashSet<>();

    @OneToOne(mappedBy = "user", cascade = CascadeType.ALL, orphanRemoval = true)
    private UserDetails details;

//    @Builder.Default
//    @Column(name = "is_in_capture", nullable = false)
//    private boolean capture = false;
}
