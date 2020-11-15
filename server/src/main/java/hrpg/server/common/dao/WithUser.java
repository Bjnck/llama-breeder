package hrpg.server.common.dao;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotNull;

@Getter
@Setter
public abstract class WithUser {
    @NotNull
    private String userId;
}
