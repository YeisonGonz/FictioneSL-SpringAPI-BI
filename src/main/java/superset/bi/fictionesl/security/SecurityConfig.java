package superset.bi.fictionesl.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.web.SecurityFilterChain;


@Configuration
public class SecurityConfig {

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        return http.csrf(csrf -> csrf.disable())
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers(
                                "/departments/**",
                                "/employess/**",
                                "/clients/**",
                                "/products/**",
                                "/orders/**")
                        .permitAll()
                        .anyRequest().authenticated()
                )
                .build();
    }
}

