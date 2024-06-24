package epita.tp.configuration;

import epita.tp.common.RoleConstants;
import epita.tp.repository.AppUserRepository;
import epita.tp.service.CustomUserDetailsService;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.annotation.web.configurers.HeadersConfigurer;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfig {
    private final AppUserRepository appUserRepository;

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .csrf(AbstractHttpConfigurer::disable)
                .cors(AbstractHttpConfigurer::disable)
                .headers(httpSecurityHeadersConfigurer -> httpSecurityHeadersConfigurer.frameOptions(HeadersConfigurer.FrameOptionsConfig::disable))
                .authorizeHttpRequests((authorize) -> authorize
                        .requestMatchers("/error").permitAll()
                        .requestMatchers("/h2-console/**").permitAll()
                        .requestMatchers("/swagger/**").permitAll()
                        .requestMatchers("/v3/api-docs/**").permitAll()
                        .requestMatchers("/login").permitAll()
                        .requestMatchers(HttpMethod.POST, "/api/v1/users").permitAll()
                        .requestMatchers("/api/v1/shuttles/**", "/api/v1/revisions/**").hasRole(RoleConstants.TECHNICIEN_ROLE)
                        .requestMatchers(HttpMethod.GET, "/api/v1/flights/future").hasRole(RoleConstants.VOYAGEUR_ROLE)
                        .requestMatchers(HttpMethod.POST, "/api/v1/flights/book/**").hasRole(RoleConstants.VOYAGEUR_ROLE)
                        .requestMatchers("/api/v1/flights/**").hasRole(RoleConstants.PLANIFICATEUR_ROLE)
                        .anyRequest().authenticated()
                )
                .userDetailsService(customerUserDetailsService())
                .httpBasic(Customizer.withDefaults())
                .logout(Customizer.withDefaults());

        return http.build();
    }

    @Bean
    public UserDetailsService customerUserDetailsService() {
        return new CustomUserDetailsService(appUserRepository);
    }
}