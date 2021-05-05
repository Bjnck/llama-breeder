package hrpg.server.creature.dao;

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
@Table(name = "color")
public class Color {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(nullable = false)
    private String code;
    @Column(nullable = false)
    private String name;

    @Column(nullable = false)
    private Integer red;
    @Column(nullable = false)
    private Integer blue;
    @Column(nullable = false)
    private Integer green;
}
