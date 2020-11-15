package hrpg.server.common.security;

import org.springframework.security.authentication.AbstractAuthenticationToken;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.AuthenticationServiceException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.oauth2.core.OAuth2AccessToken;
import org.springframework.security.oauth2.core.OAuth2AuthenticatedPrincipal;
import org.springframework.security.oauth2.server.resource.BearerTokenAuthenticationToken;
import org.springframework.security.oauth2.server.resource.InvalidBearerTokenException;
import org.springframework.security.oauth2.server.resource.authentication.BearerTokenAuthentication;
import org.springframework.security.oauth2.server.resource.introspection.BadOpaqueTokenException;
import org.springframework.security.oauth2.server.resource.introspection.OAuth2IntrospectionException;
import org.springframework.security.oauth2.server.resource.introspection.OpaqueTokenIntrospector;
import org.springframework.util.Assert;

import java.time.Instant;

import static org.springframework.security.oauth2.server.resource.introspection.OAuth2IntrospectionClaimNames.*;

public final class CustomOpaqueTokenAuthenticationProvider implements AuthenticationProvider {
	private OpaqueTokenIntrospector introspector;

	/**
	 * Creates a {@code OpaqueTokenAuthenticationProvider} with the provided parameters
	 *
	 * @param introspector The {@link OpaqueTokenIntrospector} to use
	 */
	public CustomOpaqueTokenAuthenticationProvider(OpaqueTokenIntrospector introspector) {
		Assert.notNull(introspector, "introspector cannot be null");
		this.introspector = introspector;
	}

	/**
	 * Introspect and validate the opaque
	 * <a href="https://tools.ietf.org/html/rfc6750#section-1.2" target="_blank">Bearer Token</a>.
	 *
	 * @param authentication the authentication request object.
	 *
	 * @return A successful authentication
	 * @throws AuthenticationException if authentication failed for some reason
	 */
	@Override
	public Authentication authenticate(Authentication authentication) throws AuthenticationException {
		if (!(authentication instanceof BearerTokenAuthenticationToken)) {
			return null;
		}
		BearerTokenAuthenticationToken bearer = (BearerTokenAuthenticationToken) authentication;

		OAuth2AuthenticatedPrincipal principal;
		try {
			principal = this.introspector.introspect(bearer.getToken());
		} catch (BadOpaqueTokenException failed) {
			throw new InvalidBearerTokenException(failed.getMessage());
		} catch (OAuth2IntrospectionException failed) {
			throw new AuthenticationServiceException(failed.getMessage());
		}

		AbstractAuthenticationToken result = convert(principal, bearer.getToken());
		result.setDetails(bearer.getDetails());
		return result;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public boolean supports(Class<?> authentication) {
		return BearerTokenAuthenticationToken.class.isAssignableFrom(authentication);
	}

	private AbstractAuthenticationToken convert(OAuth2AuthenticatedPrincipal principal, String token) {
		Instant iat = principal.getAttribute(ISSUED_AT);
		Instant exp = Instant.ofEpochSecond(Long.parseLong(principal.getAttribute(EXPIRES_AT)));
		OAuth2AccessToken accessToken = new OAuth2AccessToken(OAuth2AccessToken.TokenType.BEARER,
				token, iat, exp);
		return new BearerTokenAuthentication(principal, accessToken, principal.getAuthorities());
	}
}