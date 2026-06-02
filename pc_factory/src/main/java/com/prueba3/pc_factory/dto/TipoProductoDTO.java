package com.prueba3.pc_factory.dto;

import java.util.List;

import lombok.Data;

@Data
public class TipoProductoDTO {

    private Integer id;
    private String nombreTipoProducto;
    private List<String> nombresProductos;
}
