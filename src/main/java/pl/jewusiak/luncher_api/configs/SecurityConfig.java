package pl.jewusiak.luncher_api.configs;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import pl.jewusiak.luncher_api.configs.jwt.AuthEntryPointJwt;
import pl.jewusiak.luncher_api.configs.jwt.AuthTokenFilter;
import pl.jewusiak.luncher_api.users.UsersService;

@Configuration
@EnableMethodSecurity(securedEnabled = true)
@RequiredArgsConstructor
public class SecurityConfig {

    private final UsersService usersService;
    private final AuthEntryPointJwt unauthorizedHandler;
    private final AuthTokenFilter authTokenFilter;
    private final PasswordEncoder passwordEncoder;


    @Bean
    public AuthenticationProvider authenticationProvider() {
        DaoAuthenticationProvider provider = new DaoAuthenticationProvider();
        provider.setUserDetailsService(usersService::getUserByEmail);
        provider.setPasswordEncoder(passwordEncoder);

        return provider;
    }

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration configuration) throws Exception {
        return configuration.getAuthenticationManager();
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity httpSecurity) throws Exception {
        return httpSecurity.csrf(AbstractHttpConfigurer::disable)
                           .authorizeHttpRequests(matcherRegistry ->
                                   matcherRegistry.requestMatchers("/auth/**", "/error", "/docs/**").permitAll()
                                                  .anyRequest().authenticated())
                           .exceptionHandling(ex -> ex.authenticationEntryPoint(unauthorizedHandler))
                           .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                           .authenticationProvider(authenticationProvider())

                           .addFilterBefore(authTokenFilter, UsernamePasswordAuthenticationFilter.class)
                           .build();
    }


}
