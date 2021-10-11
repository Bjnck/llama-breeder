package hrpg.server.creature.resource;

import hrpg.server.creature.type.Sex;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder(toBuilder = true)
@AllArgsConstructor
@NoArgsConstructor
public class Parent {
    private Sex sex;
    private Colors colors;
    private Genes genes;
}
