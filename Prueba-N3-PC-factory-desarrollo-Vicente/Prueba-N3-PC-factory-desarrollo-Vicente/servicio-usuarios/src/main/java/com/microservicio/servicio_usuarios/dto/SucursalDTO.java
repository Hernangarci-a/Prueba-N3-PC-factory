package com.microservicio.servicio_usuarios.dto;

import java.util.List;
import lombok.Data;

@Data
public class SucursalDTO {
    private Integer idSucursal;
    private String nombreSucursal;
    private String direccionSucursal;
    private String nombreComuna;
    private List<String> nombresColaboradores;
}