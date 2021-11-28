package hrpg.server.common.security;

import hrpg.server.user.service.UserDto;
import org.springframework.security.core.context.SecurityContextHolder;

public class OAuthUserUtil {
    public static Integer getUserId() {
        Object details = SecurityContextHolder.getContext().getAuthentication().getDetails();
        if (details instanceof UserDto)
            return ((UserDto) details).getId();

        return null;
    }
}
