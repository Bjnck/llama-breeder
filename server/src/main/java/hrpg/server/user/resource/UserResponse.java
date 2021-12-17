package hrpg.server.user.resource;

import lombok.*;
import org.springframework.hateoas.RepresentationModel;

import java.util.List;

@Data
@EqualsAndHashCode(callSuper = false)
@Builder(toBuilder = true)
@AllArgsConstructor
@NoArgsConstructor
public class UserResponse extends RepresentationModel<UserResponse> {
    private String name;
    private int level;
    private long coins;
    private long points;
    private String email;

    private List<UserCollection> collection;
}
