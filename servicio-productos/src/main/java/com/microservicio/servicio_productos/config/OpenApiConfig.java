package com.microservicio.servicio_productos.config;

import org.springdoc.core.customizers.OpenApiCustomizer;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.servers.Server;

@Configuration // le avisa a spring boot que esta clase es un archivo de configuracion
public class OpenApiConfig {

    // @Value: Esta línea busca una propiedad llamada gateway.url en mi archivo
    // application.yml
    // busca por defecto la ruta http://localhost:8080
    @Value("${gateway.url:http://localhost:8080}")
    private String gatewayUrl;

    @Bean
    public OpenAPI customOpenAPI() {
        return new OpenAPI().info(
                new Info()
                        .title("Api administración de productos")
                        .version("1.1")
                        .description(
                                "Con esta API se puede administrar los productos de pc factory, incluyendo la creación, actualización y eliminación de salas, así como la gestión de reservas."));
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
