package clutch.clutchserver.global.common.config;

import clutch.clutchserver.global.jwt.JwtAuthenticationFilter;
import clutch.clutchserver.global.jwt.JwtTokenProvider;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@RequiredArgsConstructor
@EnableGlobalMethodSecurity(
        securedEnabled = true,
        jsr250Enabled = true,
        prePostEnabled = true
)
public class SecurityConfig {

    private final JwtTokenProvider jwtTokenProvider;
    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .formLogin()
                .disable()

                .cors()
                .disable()

                .csrf()
                .disable()

                .httpBasic()
                .disable()

                .sessionManagement()
                .sessionCreationPolicy(SessionCreationPolicy.NEVER);
        http.authorizeRequests()
                .requestMatchers("/",
                        "/error",
                        "/favicon.ico",
                        "/h2-console/**").permitAll()
                .requestMatchers("/api/auth/**").permitAll()
                .requestMatchers("/api/swagger-ui/**").permitAll()
                .requestMatchers("/api/**").authenticated();

        http.logout()
                .logoutUrl("/logout")
                .clearAuthentication(true);

        http.addFilterBefore(new JwtAuthenticationFilter(jwtTokenProvider), UsernamePasswordAuthenticationFilter.class);

        return http.build();
    }
}
