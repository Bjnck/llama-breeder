package hrpg.server.user.resource;

import hrpg.server.creature.type.Gene;
import lombok.*;

import java.util.List;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class UserCollection  {
    private String code;
    private List<Gene> genes;
}
