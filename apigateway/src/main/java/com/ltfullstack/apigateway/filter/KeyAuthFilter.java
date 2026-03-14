package com.ltfullstack.apigateway.filter;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.gateway.filter.GatewayFilter;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.cloud.gateway.filter.factory.AbstractGatewayFilterFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.http.server.reactive.ServerHttpResponse;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

import java.util.Objects;

@Component
public class KeyAuthFilter extends AbstractGatewayFilterFactory<KeyAuthFilter.Config> {

    @Value("${apiKey}")
    private String apiKey;

    // Example of a proper constructor in the filter factory
    public KeyAuthFilter() {
        super(Config.class);
    }

    @Override
    public GatewayFilter apply(KeyAuthFilter.Config config) {
        return (exchange, chain) -> {

            if (!exchange.getRequest().getHeaders().containsKey("apiKey")) {
                return handleException(exchange, "Missing apiKey header in request", HttpStatus.UNAUTHORIZED);
                //throw new RuntimeException("Missing apiKey header in request");
            }

            String key = exchange.getRequest().getHeaders().getFirst("apiKey");

            if (!Objects.equals(key, apiKey)) {
                return handleException(exchange, "Invalid apiKey", HttpStatus.FORBIDDEN);
                // throw new RuntimeException("Invalid apiKey");
            }

            ServerHttpRequest request = exchange.getRequest();

            return chain.filter(exchange.mutate().request(request).build());
        };
    }

    private Mono<Void> handleException(ServerWebExchange exchange, String message, HttpStatus status) {
        ServerHttpResponse response = exchange.getResponse();
        response.setStatusCode(status);
        response.getHeaders().setContentType(MediaType.APPLICATION_JSON);

        String errorMessage = String.format(
                "{" +
                        "\"timestamp\": \"%s\",\n" +
                        "  \"status\": %d,\n" +
                        "  \"error\": \"%s\",\n" +
                        "  \"message\": \"%s\",\n" +
                        "  \"path\": \"%s\"" +
                        "}",
                java.time.ZonedDateTime.now(),
                status.value(),
                status.getReasonPhrase(),
                message,
                exchange.getRequest().getURI().getPath()
        );

        return response.writeWith(Mono.just(response.bufferFactory().wrap(errorMessage.getBytes())));
    }

    public static class Config {

    }
}
