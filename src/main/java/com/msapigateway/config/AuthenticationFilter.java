package com.msapigateway.config;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.core.Ordered;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

import java.nio.charset.StandardCharsets;
import java.security.Key;
import java.util.List;

@Component
public class AuthenticationFilter implements GlobalFilter, Ordered {

    private static final String SECRET_KEY = "MiClaveSecretaSuperLargaYSeguraQueTieneAlMenos512BitsDeLongitud";

    private static final List<String> PUBLIC_PATHS = List.of(
            "/api/usuarios/login",
            "/api/usuarios/register",
            "/api/usuarios/generate-otp",  // 👈 Asegúrate de que esta esté aquí
            "/api/usuarios/verificar-otp",
            "/api/usuarios/otp-tiempo-restante",
            "/api/usuarios/reenviar-otp",
            "/api/productos",
            "/api/categorias"


    );

    @Override
    public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {
        String path = exchange.getRequest().getPath().value();

        // 🔓 Si es ruta pública, no valida token
        if (PUBLIC_PATHS.stream().anyMatch(path::startsWith)) {
            return chain.filter(exchange);
        }

        String authHeader = exchange.getRequest().getHeaders().getFirst(HttpHeaders.AUTHORIZATION);

        if (authHeader == null || !authHeader.startsWith("Bearer ")) {
            System.out.println("❌ No Authorization header o no comienza con Bearer");
            exchange.getResponse().setStatusCode(HttpStatus.UNAUTHORIZED);
            return exchange.getResponse().setComplete();
        }

        try {
            String token = authHeader.substring(7); // Quita "Bearer "

            Key key = Keys.hmacShaKeyFor(SECRET_KEY.getBytes(StandardCharsets.UTF_8));
            Claims claims = Jwts.parserBuilder()
                    .setSigningKey(key)
                    .build()
                    .parseClaimsJws(token)
                    .getBody();

            String userId = claims.getSubject();
            String permisos = claims.get("permissions", String.class);

            System.out.println("✅ TOKEN VALIDADO:");
            System.out.println("📌 Usuario ID: " + userId);
            System.out.println("🔑 Permisos: " + permisos);

            ServerHttpRequest mutatedRequest = exchange.getRequest().mutate()
                    .header("X-User-Id", userId)
                    .header("X-User-Permissions", permisos != null ? permisos : "")
                    .build();

            return chain.filter(exchange.mutate().request(mutatedRequest).build());

        } catch (Exception e) {
            System.out.println("❌ TOKEN INVÁLIDO: " + e.getMessage());
            exchange.getResponse().setStatusCode(HttpStatus.UNAUTHORIZED);
            return exchange.getResponse().setComplete();
        }
    }

    @Override
    public int getOrder() {
        return -1;
    }
}
