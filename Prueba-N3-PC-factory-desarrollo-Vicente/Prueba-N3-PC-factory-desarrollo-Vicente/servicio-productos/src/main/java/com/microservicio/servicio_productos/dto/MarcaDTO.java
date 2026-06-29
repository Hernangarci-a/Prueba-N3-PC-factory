package com.microservicio.servicio_productos.dto;

import java.util.List;
import lombok.Data;

@Data
public class MarcaDTO {
    private Integer idMarca;
    private String nombreMarca;
    private List<String> nombresProductos;
}