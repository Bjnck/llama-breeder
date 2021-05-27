package hrpg.server.creature.service;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ColorDto {
    private String code;
    private String name;
    private int generation;
    private String parentCode;
}
