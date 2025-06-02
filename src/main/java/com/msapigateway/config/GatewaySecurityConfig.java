package com.msapigateway.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.web.server.ServerHttpSecurity;
import org.springframework.security.web.server.SecurityWebFilterChain;

@Configuration
public class GatewaySecurityConfig {

    @Bean
    public SecurityWebFilterChain securityWebFilterChain(ServerHttpSecurity http) {
        return http
                .csrf(csrf -> csrf.disable())
                .authorizeExchange(exchange -> exchange
                        .pathMatchers(
                                "/api/usuarios/login",
                                "/api/usuarios/register",
                                "/api/usuarios/generate-otp",  // 👈 Asegúrate de que esta esté aquí
                                "/api/usuarios/verificar-otp",
                                "/api/usuarios/otp-tiempo-restante",
                                "/api/usuarios/reenviar-otp",
                                "/api/productos",
                                "/api/categorias",
                                "/api/usuarios",
                                "/api/roles/**",
                                "/api/permissions/**",
                                "/api/userroles",
                                "/api/userroles/**",
                                "/api/rolespermisos"



                        ).permitAll()
                        .anyExchange().authenticated()
                )
                .build();
    }

}
