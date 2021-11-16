package hrpg.server.common.security;

import hrpg.server.user.service.UserDto;
import hrpg.server.user.service.UserService;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.GenericFilterBean;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import java.io.IOException;

public class DbUserFilter extends GenericFilterBean {

    private final UserService userService;

    public DbUserFilter(UserService userService) {
        this.userService = userService;
    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        if (SecurityContextHolder.getContext().getAuthentication() != null) {
            CustomPrincipal principal = (CustomPrincipal) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

            String registrationKey = getRegistrationKey(principal);
            UserDto userDto = userService.findByRegistrationKey(registrationKey)
                    .orElseGet(() -> userService.create(registrationKey));
            principal.setUserId(userDto.getId());
        }

        chain.doFilter(request, response);
    }

    private String getRegistrationKey(CustomPrincipal principal) {
        return principal.getAttribute("registration") + "." + principal.getAttribute(principal.getAttribute("registration_id"));
    }
}
