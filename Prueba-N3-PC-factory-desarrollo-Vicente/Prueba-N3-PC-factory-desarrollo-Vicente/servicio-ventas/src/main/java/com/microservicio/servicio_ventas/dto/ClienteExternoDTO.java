package com.microservicio.servicio_ventas.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class ClienteExternoDTO {
    private Integer id;
    private String nombre;
    private String email;
}