package com.prueba3.pc_factory.dto;

import java.util.List;
import lombok.Data;

@Data
public class MarcaDTO {
    private Integer idMarca;
    private String nombreMarca;
    private List<String> nombresProductos;
}