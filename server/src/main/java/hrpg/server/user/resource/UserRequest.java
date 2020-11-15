package hrpg.server.user.resource;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Data
@Builder(toBuilder = true)
@AllArgsConstructor
public class UserRequest {
    @NotNull
    @Size(max = 100)
    private String name;
}
