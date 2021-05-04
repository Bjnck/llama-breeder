package hrpg.server.user.service;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

import java.util.Set;

@Data
@Builder(toBuilder = true)
@AllArgsConstructor
public class UserDto {
    private Integer id;
    private String name;

    private Set<String> registrationKeys;

    private int version;

    private int coins;
    private int level;
//    private boolean capture;
}
