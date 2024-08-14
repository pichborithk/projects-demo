package dev.pichborith.config;

import dev.pichborith.repositories.UserRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import static org.springframework.http.HttpMethod.DELETE;
import static org.springframework.http.HttpMethod.GET;
import static org.springframework.http.HttpMethod.POST;
import static org.springframework.http.HttpMethod.PUT;
import static dev.pichborith.models.Permission.ADMIN_CREATE;
import static dev.pichborith.models.Permission.ADMIN_DELETE;
import static dev.pichborith.models.Permission.ADMIN_READ;
import static dev.pichborith.models.Permission.ADMIN_UPDATE;
import static dev.pichborith.models.Permission.EMPLOYEE_CREATE;
import static dev.pichborith.models.Permission.EMPLOYEE_DELETE;
import static dev.pichborith.models.Permission.EMPLOYEE_READ;
import static dev.pichborith.models.Permission.EMPLOYEE_UPDATE;
import static dev.pichborith.models.Role.ADMIN;
import static dev.pichborith.models.Role.EMPLOYEE;
import static org.springframework.security.config.http.SessionCreationPolicy.STATELESS;

import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;

import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    @Autowired
    private JwtAuthFilter jwtAuthFilter;

    @Autowired
    private JwtAuthEntryPoint jwtAuthEntryPoint;

    @Autowired
    private AuthenticationProvider authenticationProvider;

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http.csrf(AbstractHttpConfigurer::disable)
            .authorizeHttpRequests(
                req -> req.requestMatchers("/api/register").permitAll()
                          .requestMatchers("/api/login").permitAll()
                          .requestMatchers("/api/v1/management/**").hasAnyRole(ADMIN.name(), EMPLOYEE.name())
                          .requestMatchers(GET, "/api/employee/**")
                          .hasAnyAuthority(ADMIN_READ.name(),
                                           EMPLOYEE_READ.name())
                          .requestMatchers(POST, "/api/employee/**")
                          .hasAnyAuthority(ADMIN_CREATE.name(),
                                           EMPLOYEE_CREATE.name())
                          .requestMatchers(PUT, "/api/employee/**")
                          .hasAnyAuthority(ADMIN_UPDATE.name(),
                                           EMPLOYEE_UPDATE.name())
                          .requestMatchers(DELETE, "/api/employee/**")
                          .hasAnyAuthority(ADMIN_DELETE.name(),
                                           EMPLOYEE_DELETE.name())
                          .anyRequest()
                          .authenticated())
            .sessionManagement(
                session -> session.sessionCreationPolicy(STATELESS))
            .authenticationProvider(authenticationProvider)
            .exceptionHandling(
                ex -> ex.authenticationEntryPoint(jwtAuthEntryPoint))
            .addFilterBefore(jwtAuthFilter,
                             UsernamePasswordAuthenticationFilter.class);

        return http.build();
    }
}
