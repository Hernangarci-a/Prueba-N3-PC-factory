package com.microservicio.servicio_productos.dto;

import java.util.List;
import lombok.Data;

@Data
public class CategoriaDTO {

    private Integer idCategoria;
    private String nombreCategoria;
    private List<String> nombresProductos;
}
