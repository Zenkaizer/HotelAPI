package cl.ucn.codecrafters.configuration;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.authentication.logout.LogoutHandler;

import static cl.ucn.codecrafters.user.domain.entities.Permission.*;
import static cl.ucn.codecrafters.user.domain.entities.Role.*;
import static org.springframework.http.HttpMethod.*;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfig{

    private final JwtAuthenticationFilter jwtAuthenticationFilter;
    private final AuthenticationProvider authProvider;
    private final LogoutHandler logoutHandler;

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {

        return http
            .csrf(csrf ->
                csrf
                .disable())
            .authorizeHttpRequests(authRequest->
                authRequest
                    .requestMatchers("/auth/**").permitAll()

                    .requestMatchers("/clients/**").hasAnyRole(ADMIN.name(), ADMINISTRATIVE.name())

                    .requestMatchers(GET, "/clients/**").hasAnyAuthority(ADMIN_READ.name(), ADMINISTRATIVE_READ.name())
                    .requestMatchers(POST, "/clients/**").hasAnyAuthority(ADMIN_CREATE.name(), ADMINISTRATIVE_CREATE.name())
                    .requestMatchers(PUT, "/clients/**").hasAnyAuthority(ADMIN_UPDATE.name(), ADMINISTRATIVE_UPDATE.name())
                    .requestMatchers(DELETE, "/clients/**").hasAnyAuthority(ADMIN_DELETE.name(), ADMINISTRATIVE_DELETE.name())

                    .requestMatchers("/administratives/**").hasRole(ADMIN.name())

                    .requestMatchers(GET, "/administratives/**").hasAuthority(ADMIN_READ.name())
                    .requestMatchers(POST, "/administratives/**").hasAuthority(ADMIN_CREATE.name())
                    .requestMatchers(PUT, "/administratives/**").hasAuthority(ADMIN_UPDATE.name())
                    .requestMatchers(DELETE, "/administratives/**").hasAuthority(ADMIN_DELETE.name())

                    .requestMatchers("/reserves/**").hasAnyRole(CLIENT.name(),ADMINISTRATIVE.name(), ADMIN.name())

                    .requestMatchers(GET, "/reserves/**").hasAnyAuthority(CLIENT_READ.name(), ADMINISTRATIVE_READ.name())
                    .requestMatchers(POST, "/reserves/**").hasAuthority(CLIENT_CREATE.name())
                    .requestMatchers(PUT, "/reserves/**").hasAnyAuthority(CLIENT_UPDATE.name(), ADMINISTRATIVE_UPDATE.name())
                    .requestMatchers(DELETE, "/reserves/**").hasAnyAuthority(CLIENT_DELETE.name(), ADMINISTRATIVE_DELETE.name())

                    .requestMatchers("/rooms/**").hasAnyRole(CLIENT.name(),ADMINISTRATIVE.name(),ADMIN.name())

                    .requestMatchers(GET,"/rooms/**").hasAnyAuthority(CLIENT_READ.name())
                    .requestMatchers(POST,"/rooms/**").hasAnyAuthority(ADMINISTRATIVE_CREATE.name(), ADMIN_CREATE.name())
                    .requestMatchers(PUT,"/rooms/¨**").hasAnyAuthority(ADMINISTRATIVE_UPDATE.name(), ADMIN_UPDATE.name())
                    .requestMatchers(DELETE,"/rooms/**").hasAnyAuthority(ADMIN_DELETE.name(),ADMINISTRATIVE_DELETE.name())

                    .anyRequest().authenticated()
            )
            .sessionManagement(sessionMannager ->
                sessionMannager
                    .sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .authenticationProvider(authProvider)
                .addFilterBefore(jwtAuthenticationFilter, UsernamePasswordAuthenticationFilter.class)
                .logout(logout -> logout.addLogoutHandler(logoutHandler).logoutSuccessHandler(
                                (request, response, authentication) -> SecurityContextHolder.clearContext())
                .logoutUrl("/auth/logout")
                )
            .build();
    }

}
