package com.microservicio.servicio_usuarios.config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.servers.Server;
import org.springdoc.core.customizers.OpenApiCustomizer;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class OpenApiConfig {

    @Value("${gateway.url:http://localhost:8080}")
    private String gatewayUrl;

    @Bean
    public OpenAPI customOpenAPI() {
        return new OpenAPI().info(
                new Info()
                        .title("Api administración de usuarios")
                        .version("1.1")
                        .description("Con esta API se puede administrar los usuarios de pc factory"));
    }

    @Bean
    public OpenApiCustomizer gatewayServerCustomizer() {
        return openApi -> {
            openApi.getServers().clear();
            openApi.addServersItem(
                    new Server()
                            .url(gatewayUrl)
                            .description("API Gateway (Eureka load-balanced)"));
        };
    }
}