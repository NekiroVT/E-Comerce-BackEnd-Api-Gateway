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
                                "/api/auth/login",
                                "/api/carrito/agregar",
                                "/api/carrito/obtener",
                                "/api/carrito/eliminar/**",
                                "/api/producto-logistica/**",
                                "/api/sedes/**",
                                "/api/direcciones/**",
                                "/api/carrito/actualizar-cantidad",
                                "/api/carrito/listar-poco",
                                "/api/carrito/listar-completo",
                                "/api/usuarios/public/",
                                "/api/productos/usuario/",
                                "/api/combinaciones/stock/**",
                                "/api/auth/refresh",
                                "/api/usuarios/register",
                                "/api/usuarios/generate-otp",  // 👈 Asegúrate de que esta esté aquí
                                "/api/usuarios/verificar-otp",
                                "/api/usuarios/otp-tiempo-restante",
                                "/api/usuarios/reenviar-otp",
                                "/api/productos/**",
                                "/api/claves",
                                "/api/valores",
                                "/api/clavevalorrelacion",
                                "/api/categorias",
                                "/api/categorias/**",
                                "/api/usuarios",
                                "/api/usuarios/**",
                                "/api/roles",
                                "/api/roles/**",
                                "/api/permissions",
                                "/api/permissions/**",
                                "/api/userroles",
                                "/api/userroles/**",
                                "/api/rolespermisos",
                                "/api/rolespermisos/**"



                        ).permitAll()
                        .anyExchange().authenticated()
                )
                .build();
    }

}