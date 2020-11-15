package hrpg.server.user.service;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
@Builder(toBuilder = true)
@AllArgsConstructor
public class UserDto {
    private String id;
    private String name;

    private List<String> registrationKeys;

    private long coins;
    private int level;
    private boolean capture;
}
