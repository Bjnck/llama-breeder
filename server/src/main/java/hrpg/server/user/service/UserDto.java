package hrpg.server.user.service;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Data
@Builder(toBuilder = true)
@AllArgsConstructor
public class UserDto {
    private Integer id;
    private String name;

    private String uid;
    private String issuer;
    private String email;

    private int coins;
    private int level;
}
