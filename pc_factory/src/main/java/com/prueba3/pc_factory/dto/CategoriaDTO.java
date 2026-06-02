package com.prueba3.pc_factory.dto;

import java.util.List;
import lombok.Data;

@Data
public class CategoriaDTO {

    private Integer idCategoria;
    private String nombrCategoria;
    private List<String> nombresProductos;
}
