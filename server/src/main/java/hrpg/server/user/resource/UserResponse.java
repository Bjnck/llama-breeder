package hrpg.server.user.resource;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import org.springframework.hateoas.RepresentationModel;

@Data
@Builder(toBuilder = true)
@AllArgsConstructor
public class UserResponse extends RepresentationModel<UserResponse> {
    private String name;
    private int level;
    private long coins;
}
