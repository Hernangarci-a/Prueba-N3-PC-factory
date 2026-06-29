package com.microservicio.servicio_productos.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;

@Configuration
public class SwaggerConfig {

    @Bean
    public OpenAPI customOpenAPI() {
        return new OpenAPI().info(
                new Info()
                        .title("Api administración de productos")
                        .version("1.1")
                        .description(
                                "Con esta API se puede administrar los productos de pc factory, incluyendo la creación, actualización y eliminación de salas, así como la gestión de reservas."));
    }

}
