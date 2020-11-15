package hrpg.server.common.security;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.oauth2.core.OAuth2AuthenticatedPrincipal;
import org.springframework.util.Assert;

import java.io.Serializable;
import java.util.Collection;
import java.util.Collections;
import java.util.Map;

import static org.springframework.security.core.authority.AuthorityUtils.NO_AUTHORITIES;

public class CustomPrincipal implements OAuth2AuthenticatedPrincipal, Serializable {

    private final Map<String, Object> attributes;
    private final Collection<GrantedAuthority> authorities;
    private final String name;

    private String userId;

    /**
     * Constructs an {@code CustomPrincipal} using the provided parameters.
     *
     * @param attributes  the attributes of the OAuth 2.0 token
     * @param authorities the authorities of the OAuth 2.0 token
     */
    public CustomPrincipal(Map<String, Object> attributes,
                           Collection<GrantedAuthority> authorities) {

        this(null, attributes, authorities);
    }

    /**
     * Constructs an {@code CustomPrincipal} using the provided parameters.
     *
     * @param name        the name attached to the OAuth 2.0 token
     * @param attributes  the attributes of the OAuth 2.0 token
     * @param authorities the authorities of the OAuth 2.0 token
     */
    public CustomPrincipal(String name, Map<String, Object> attributes,
                           Collection<GrantedAuthority> authorities) {

        Assert.notEmpty(attributes, "attributes cannot be empty");
        this.attributes = Collections.unmodifiableMap(attributes);
        this.authorities = authorities == null ?
                NO_AUTHORITIES : Collections.unmodifiableCollection(authorities);
        this.name = name == null ? (String) this.attributes.get("sub") : name;
    }

    /**
     * Gets the attributes of the OAuth 2.0 token in map form.
     *
     * @return a {@link Map} of the attribute's objects keyed by the attribute's names
     */
    public Map<String, Object> getAttributes() {
        return this.attributes;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return this.authorities;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String getName() {
        return this.name;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }
}
