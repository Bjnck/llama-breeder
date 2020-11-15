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
import org.springframework.security.oauth2.server.resource.introspection.NimbusOpaqueTokenIntrospector;
import org.springframework.security.oauth2.server.resource.introspection.OpaqueTokenIntrospector;
import org.springframework.security.oauth2.server.resource.web.BearerTokenAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import javax.servlet.http.HttpServletRequest;
import java.net.URI;
import java.util.Collections;

@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    private final UserService userService;

    public SecurityConfig(UserService userService) {
        this.userService = userService;
    }

    protected void configure(HttpSecurity http) throws Exception {
        http.authorizeRequests(authorize -> authorize.anyRequest().authenticated()
        ).oauth2ResourceServer(oauth2 ->
                oauth2.authenticationManagerResolver(this.tokenAuthenticationManagerResolver())
        );
        http.cors();
        http.addFilterAfter(
                new DbUserFilter(userService), BearerTokenAuthenticationFilter.class);
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

    //todo client info in properties
    @Bean
    public OpaqueTokenIntrospector introspectorGoogle() {
        return new GoogleAccessTokenIntrospector(
                "https://www.googleapis.com/oauth2/v3/tokeninfo",
                "932356479055-koogrkg18qg06ccbp02ngbp5fkd4k0cc.apps.googleusercontent.com",
                "LyIRcUAqz77ZoslEhynVDTsW");
    }

    //    @Bean
    public OpaqueTokenIntrospector introspectorGithub() {
        String introspectionUri = "https://api.github.com/user";
        NimbusOpaqueTokenIntrospector introspector = new NimbusOpaqueTokenIntrospector(
                introspectionUri, "d0e40e1858a6ccf54357", "da78df6928ac20210b56e0e3d6eddbe110f5b9d8");
        introspector.setRequestEntityConverter(githubRequestEntityConverter(URI.create(introspectionUri)));
        return introspector;
    }

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

    @Bean
    CorsConfigurationSource corsConfigurationSource() {
        UrlBasedCorsConfigurationSource source = new
                UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", new CorsConfiguration().applyPermitDefaultValues());
        return source;
    }
}