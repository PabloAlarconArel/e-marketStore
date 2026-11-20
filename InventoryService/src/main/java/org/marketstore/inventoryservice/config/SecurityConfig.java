package org.marketstore.inventoryservice.config;


import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.convert.converter.Converter;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AbstractAuthenticationToken;
import org.springframework.security.config.annotation.method.configuration.EnableReactiveMethodSecurity;
import org.springframework.security.config.annotation.web.reactive.EnableWebFluxSecurity;
import org.springframework.security.config.web.server.ServerHttpSecurity;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.oauth2.jwt.JwtValidators;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationToken;
import org.springframework.security.oauth2.jose.jws.MacAlgorithm;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.security.oauth2.jwt.NimbusReactiveJwtDecoder;
import org.springframework.security.oauth2.jwt.ReactiveJwtDecoder;
import org.springframework.security.web.server.SecurityWebFilterChain;
import reactor.core.publisher.Mono;


import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;
import java.nio.charset.StandardCharsets;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Configuration
@EnableWebFluxSecurity
@EnableReactiveMethodSecurity
public class SecurityConfig {

    @Value("${security.jwt.secret}")
    private String jwtSecret;

    @Value("${security.jwt.issuer}")
    private String jwtIssuer;

    @Bean
    public SecurityWebFilterChain securityFilterChain(ServerHttpSecurity http) {
        http
                .csrf(ServerHttpSecurity.CsrfSpec::disable)

                .authorizeExchange(exchanges -> exchanges
                        .pathMatchers("/actuator/health")
                        .permitAll()

                        .pathMatchers("/api/inventory/reservations/**")
                        .hasRole("SERVICE")

                        .pathMatchers(HttpMethod.GET
                                , "/api/inventory/availability/**")
                        .hasAnyRole("ADMIN","CUSTOMER","SERVICE")

                        .pathMatchers(
                                HttpMethod.GET,
                                "/api/inventory",
                                "/api/inventory/**"
                        ).hasAnyRole("ADMIN","SERVICE")

                        .pathMatchers(HttpMethod.POST
                                ,"/api/inventory/**"
                        ).hasRole("ADMIN")

                        .pathMatchers(HttpMethod.PUT
                                ,"/api/inventory/**"
                        ).hasRole("ADMIN")

                        .pathMatchers(HttpMethod.PATCH
                                ,"/api/inventory/**"
                        ).hasRole("ADMIN")

                        .pathMatchers(HttpMethod.DELETE
                                ,"/api/inventory/**"
                        ).hasRole("ADMIN")

                        .anyExchange().denyAll()
                )

                .oauth2ResourceServer(oauth2->oauth2
                        .jwt(jwt ->jwt
                                .jwtAuthenticationConverter(jwtAuthenticationConverter()))
                );
        return http.build();

    }

    @Bean
    public ReactiveJwtDecoder jwtDecoder() {

        SecretKey secretKey = new SecretKeySpec(
                jwtSecret.getBytes(StandardCharsets.UTF_8),
                "HmacSHA256"
        );

        NimbusReactiveJwtDecoder decoder = NimbusReactiveJwtDecoder
                .withSecretKey(secretKey)
                .macAlgorithm(MacAlgorithm.HS256)
                .build();

        decoder.setJwtValidator(JwtValidators.createDefaultWithIssuer(jwtIssuer));

        return decoder;
    }

    @Bean
    public Converter<Jwt, Mono<AbstractAuthenticationToken>>
    jwtAuthenticationConverter() {

        return jwt -> {

            List<GrantedAuthority> authorities =
                    Optional.ofNullable(
                                    jwt.getClaimAsStringList("roles")
                            )
                            .orElse(Collections.emptyList())
                            .stream()
                            .map(role -> role.startsWith("ROLE_")
                                    ? role
                                    : "ROLE_" + role)
                            .map(SimpleGrantedAuthority::new)
                            .collect(Collectors.toList());

            return Mono.just(
                    new JwtAuthenticationToken(
                            jwt,
                            authorities,
                            jwt.getSubject()
                    )
            );
        };
    }


}
