package hrpg.server.common.security;

import org.springframework.security.core.context.SecurityContextHolder;

public class OAuthUserUtil {

    public static String getUserId() {
        return ((CustomPrincipal) SecurityContextHolder.getContext().getAuthentication().getPrincipal()).getUserId();
//        return "5fa1539ecf651d499fa1856c";
//        OAuth2AuthenticationToken token = (OAuth2AuthenticationToken) SecurityContextHolder.getContext().getAuthentication();
//        return token.getPrincipal().getAttribute(OAuth2SecurityConfig.USER_ID_ATTRIBUTE);
    }
}
