package hrpg.server.common.security;

import hrpg.server.user.service.UserService;
import org.springframework.context.annotation.Bean;
import org.springframework.core.convert.converter.Converter;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.RequestEntity;
import org.springframework.security.authentication.AuthenticationManagerResolver;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.oauth2.server.resource.introspection.OpaqueTokenIntrospector;
import org.springframework.security.oauth2.server.resource.web.BearerTokenAuthenticationFilter;

import javax.servlet.http.HttpServletRequest;
import java.net.URI;
import java.util.Collections;

@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    private final UserService userService;
    private final SecurityProperties securityProperties;

    public SecurityConfig(UserService userService,
                          SecurityProperties securityProperties) {
        this.userService = userService;
        this.securityProperties = securityProperties;
    }

    protected void configure(HttpSecurity http) throws Exception {
        http.authorizeRequests().antMatchers("/health-check", "/").permitAll();

        http.authorizeRequests(authorize -> authorize.anyRequest().authenticated())
                .oauth2ResourceServer(oauth2 ->
                        oauth2.authenticationManagerResolver(this.tokenAuthenticationManagerResolver()));

        http.cors();
        http.addFilterAfter(new DbUserFilter(userService), BearerTokenAuthenticationFilter.class);
    }

    @Bean
    AuthenticationManagerResolver<HttpServletRequest> tokenAuthenticationManagerResolver() {
//        BearerTokenResolver bearerToken = new DefaultBearerTokenResolver();
//        JwtAuthenticationProvider jwt = jwt();

        CustomOpaqueTokenAuthenticationProvider opaqueToken = new CustomOpaqueTokenAuthenticationProvider(this.introspectorGoogle());

        return request -> {
//            if (useJwt(request)) {
//                return jwt::authenticate;
//            } else {
            return opaqueToken::authenticate;
//            }
        };
    }

    @Bean
    public OpaqueTokenIntrospector introspectorGoogle() {
        return new GoogleAccessTokenIntrospector(
                securityProperties.getGoogleUrl(),
                securityProperties.getGoogleClientId(),
                securityProperties.getGoogleSecretKey());
    }

    //    @Bean
//    public OpaqueTokenIntrospector introspectorGithub() {
//        String introspectionUri = "https://api.github.com/user";
//        NimbusOpaqueTokenIntrospector introspector = new NimbusOpaqueTokenIntrospector(
//                introspectionUri, "clientId", "clientSecret");
//        introspector.setRequestEntityConverter(githubRequestEntityConverter(URI.create(introspectionUri)));
//        return introspector;
//    }

    private Converter<String, RequestEntity<?>> githubRequestEntityConverter(URI introspectionUri) {
        return token -> {
            HttpHeaders headers = requestHeaders();
            headers.set("Authorization", "token " + token);
            return new RequestEntity<>(headers, HttpMethod.POST, introspectionUri);
        };
    }

    private HttpHeaders requestHeaders() {
        HttpHeaders headers = new HttpHeaders();
        headers.setAccept(Collections.singletonList(MediaType.APPLICATION_JSON));
        return headers;
    }
}
