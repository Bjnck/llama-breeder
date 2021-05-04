package hrpg.server.user.resource;

import lombok.*;
import org.springframework.hateoas.RepresentationModel;

@Data
@EqualsAndHashCode(callSuper = false)
@Builder(toBuilder = true)
@AllArgsConstructor
@NoArgsConstructor
public class UserResponse extends RepresentationModel<UserResponse> {
    private String name;
    private int level;
    private long coins;
}
