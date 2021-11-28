package hrpg.server.common.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;

@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    private final FirebaseAuthenticationProvider firebaseAuthenticationProvider;

    public SecurityConfig(FirebaseAuthenticationProvider firebaseAuthenticationProvider) {
        this.firebaseAuthenticationProvider = firebaseAuthenticationProvider;
    }

    @Autowired
    @Override
    protected void configure(AuthenticationManagerBuilder auth) {
        auth.authenticationProvider(firebaseAuthenticationProvider);
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
                .addFilterBefore(new FirebaseTokenFilter(), BasicAuthenticationFilter.class)
                .authorizeRequests()
                .antMatchers("/health-check", "/").permitAll()
                .anyRequest().authenticated()
                .and()
                .csrf().disable()
                .cors();
    }

}
