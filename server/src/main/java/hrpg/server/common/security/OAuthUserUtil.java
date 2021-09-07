package hrpg.server.common.security;

import org.springframework.security.core.context.SecurityContextHolder;

public class OAuthUserUtil {

    public static Integer getUserId() {
        return ((CustomPrincipal) SecurityContextHolder.getContext().getAuthentication().getPrincipal()).getUserId();
    }
}
