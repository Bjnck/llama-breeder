package hrpg.server.common.security;

import org.springframework.security.authentication.AbstractAuthenticationToken;

public class FirebaseAuthenticationToken extends AbstractAuthenticationToken {
    private Object principal;
    private Object credentials;

    public FirebaseAuthenticationToken(Object credentials) {
        super(null);
        this.credentials = credentials;
        setAuthenticated(false);
    }

    public FirebaseAuthenticationToken(Object principal, Object credentials) {
        super(null);
        this.principal = principal;
        this.credentials = credentials;
        setAuthenticated(true);
    }

    @Override
    public Object getCredentials() {
        return this.credentials;
    }

    @Override
    public Object getPrincipal() {
        return this.principal;
    }
}
