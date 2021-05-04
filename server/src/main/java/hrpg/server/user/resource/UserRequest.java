package hrpg.server.user.resource;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.Size;

@Data
@Builder(toBuilder = true)
@AllArgsConstructor
@NoArgsConstructor
public class UserRequest {
    @Size(max = 100)
    private String name;
}
