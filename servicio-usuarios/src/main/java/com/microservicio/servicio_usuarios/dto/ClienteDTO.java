package com.microservicio.servicio_usuarios.dto;

import lombok.Data;

@Data
public class ClienteDTO {
    private Integer idCliente;
    private String primerNombre;
    private String apellidoCliente;
}