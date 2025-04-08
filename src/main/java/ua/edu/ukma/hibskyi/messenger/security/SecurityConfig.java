package ua.edu.ukma.hibskyi.messenger.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import ua.edu.ukma.hibskyi.messenger.security.filter.AuthExceptionHandlerFilter;
import ua.edu.ukma.hibskyi.messenger.security.filter.AuthenticationFilter;
import ua.edu.ukma.hibskyi.messenger.security.filter.JWTAuthorizationFilter;

@Configuration
public class SecurityConfig {

    @Bean
    public PasswordEncoder bCryptPasswordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http,
                                           AuthenticationManager authenticationManager,
                                           JWTUtility jwtUtility) throws Exception {
        return http
            .csrf(AbstractHttpConfigurer::disable)
            .authorizeHttpRequests(authorize -> authorize
                .requestMatchers("/auth/**").permitAll()
//                .requestMatchers(HttpMethod.POST, "api/user").permitAll()
                .anyRequest().authenticated())
            .formLogin(login -> login
                .loginPage("/auth/login")
                .defaultSuccessUrl("/home"))
            .logout(logout -> logout
                .logoutUrl("/auth/logout")
                .deleteCookies("SECRET_TOKEN"))
            .addFilterBefore(new AuthExceptionHandlerFilter(), AuthenticationFilter.class)
            .addFilter(new AuthenticationFilter(authenticationManager, jwtUtility))
            .addFilterAfter(new JWTAuthorizationFilter(jwtUtility), AuthenticationFilter.class)
            .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
            .build();
    }
}
