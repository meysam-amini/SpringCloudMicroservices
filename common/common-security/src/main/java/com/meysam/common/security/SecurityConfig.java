package com.meysam.common.security;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.convert.converter.Converter;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.oauth2.server.resource.OAuth2ResourceServerConfigurer;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.session.SessionRegistryImpl;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationConverter;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.session.RegisterSessionAuthenticationStrategy;
import org.springframework.security.web.authentication.session.SessionAuthenticationStrategy;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import static org.springframework.boot.autoconfigure.security.servlet.PathRequest.toH2Console;

@Configuration
@EnableWebSecurity
@EnableMethodSecurity
public class SecurityConfig {

    @Value("${spring.security.oauth2.client.registration.keycloak.client-id}")
    private String CLIENT_ID;

    private final KeycloakLogoutHandler keycloakLogoutHandler;

    SecurityConfig(KeycloakLogoutHandler keycloakLogoutHandler) {
        this.keycloakLogoutHandler = keycloakLogoutHandler;
    }

    @Bean
    protected SessionAuthenticationStrategy sessionAuthenticationStrategy() {
        return new RegisterSessionAuthenticationStrategy(new SessionRegistryImpl());
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http.authorizeHttpRequests(authorize -> authorize
                .requestMatchers("/public/**").permitAll()
//                .requestMatchers(toH2Console()).permitAll()
                .anyRequest().authenticated()
        );

        http.csrf().disable();

        // for h2-console
//        http.csrf().ignoringRequestMatchers(toH2Console());
//        http.headers().frameOptions().disable();


//        http.oauth2Login()
//                .and()
//                .logout()
//                .addLogoutHandler(keycloakLogoutHandler)
//                .logoutSuccessUrl("/");
        http.oauth2ResourceServer(OAuth2ResourceServerConfigurer::jwt);
        return http.build();
    }

    @Bean
    public JwtAuthenticationConverter jwtAuthenticationConverterForKeycloak() {
        Converter<Jwt, Collection<GrantedAuthority>> jwtGrantedAuthoritiesConverter = jwt -> {
            Collection<GrantedAuthority> authorities = new ArrayList<>();
            Map<String, Object> resourceAccess = jwt.getClaim("resource_access");
            String sc = jwt.getClaim("scope");
            Collection<String> scopes = List.of(sc.split(" "));
            Map<String, Object> resource = null;
            Collection<String> resourceRoles = null;
            if (resourceAccess != null &&
                    (resource = (Map<String, Object>) resourceAccess.get(CLIENT_ID)) !=
                            null && (resourceRoles = (Collection<String>) resource.get("roles")) != null)
                authorities.addAll(resourceRoles.stream()
                        .map(x -> new SimpleGrantedAuthority("ROLE_" + x))
                        .collect(Collectors.toSet()));
            authorities.addAll(scopes.stream().map(y->new SimpleGrantedAuthority("SCOPE_"+y)).collect(Collectors.toList()));
            return authorities;
        };

        JwtAuthenticationConverter jwtAuthenticationConverter = new JwtAuthenticationConverter();
        jwtAuthenticationConverter.setPrincipalClaimName("preferred_username");
        jwtAuthenticationConverter.setJwtGrantedAuthoritiesConverter(jwtGrantedAuthoritiesConverter);

        return jwtAuthenticationConverter;
    }
}