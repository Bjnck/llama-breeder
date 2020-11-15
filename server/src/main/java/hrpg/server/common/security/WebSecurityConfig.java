//package hrpg.server.common.security;
//
//import org.springframework.context.annotation.Configuration;
//import org.springframework.security.config.annotation.web.builders.HttpSecurity;
//import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
//import org.springframework.security.core.session.SessionRegistryImpl;
//
//@Configuration
//public class WebSecurityConfig extends WebSecurityConfigurerAdapter {
//    @Override
//    protected void configure(HttpSecurity http) throws Exception {
//        http.authorizeRequests((requests) -> {
//            try {
//                requests
//                        .anyRequest().authenticated()
//                        .and()
//                        .sessionManagement()
//                        .maximumSessions(1)
//                        .sessionRegistry(new SessionRegistryImpl());
//            } catch (Exception e) {
//                throw new RuntimeException(e);
//            }
//        });
////                .exceptionHandling(e -> e.authenticationEntryPoint(new HttpStatusEntryPoint(HttpStatus.UNAUTHORIZED)))
////        .oauth2Login(Customizer.withDefaults());
////        http.oauth2Client();
////        .oauth2Login();
////        http.sessionManagement().maximumSessions(1);
//        http.cors();
//
//    }
//
////    @Bean
////    CorsConfigurationSource corsConfigurationSource() {
////        UrlBasedCorsConfigurationSource source = new
////                UrlBasedCorsConfigurationSource();
////        source.registerCorsConfiguration("/**", new CorsConfiguration().applyPermitDefaultValues());
////        return source;
////    }
//
//}