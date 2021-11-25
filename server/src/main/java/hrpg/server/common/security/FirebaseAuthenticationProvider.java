package hrpg.server.common.security;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthException;
import com.google.firebase.auth.FirebaseToken;
import hrpg.server.user.service.UserDto;
import hrpg.server.user.service.UserService;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

@Component
public class FirebaseAuthenticationProvider implements AuthenticationProvider {

    private final UserService userService;

    public FirebaseAuthenticationProvider(UserService userService) {
        this.userService = userService;
    }

    public boolean supports(Class<?> authentication) {
        return (FirebaseAuthenticationToken.class.isAssignableFrom(authentication));
    }

    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        if (!supports(authentication.getClass())) {
            return null;
        }

        FirebaseAuthenticationToken firebaseAuthentication = (FirebaseAuthenticationToken) authentication;
        String idToken = (String) firebaseAuthentication.getCredentials();

        FirebaseToken token = verifyIdToken(idToken);
        String uid = token.getUid();
        String email = token.getEmail();
        String issuer = token.getIssuer();

        FirebaseAuthenticationToken authenticationToken = new FirebaseAuthenticationToken(uid, idToken);
        UserDto user = userService.findByUid(uid).orElse(null);
        if (user == null) user = userService.create(uid, issuer, email);
        authenticationToken.setDetails(user);

        return authenticationToken;
    }

    private FirebaseToken verifyIdToken(String idToken) {
        if (StringUtils.isEmpty(idToken)) {
            throw new FirebaseAuthenticationException("empty idToken");
        }
        try {
            return FirebaseAuth.getInstance().verifyIdToken(idToken);
        } catch (FirebaseAuthException e) {
            throw new BadCredentialsException("invalid idToken", e);
        }
    }
}
