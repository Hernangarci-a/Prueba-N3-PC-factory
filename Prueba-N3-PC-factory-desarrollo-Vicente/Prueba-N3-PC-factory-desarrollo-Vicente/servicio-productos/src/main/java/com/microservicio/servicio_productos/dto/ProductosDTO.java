package com.microservicio.servicio_productos.dto;

import java.util.List;

import lombok.Data;

@Data
public class ProductosDTO {
    private Integer idProducto;
    private String nombre_producto;
    private Double precioUnitario;
    private String procesador;
    private String memoria_ram;
    private String almacenamiento;
    private String nombreMarca;
    private String nombreTipoProducto;
    private List<Integer> foliosVentas;
    private List<String> nombresCategorias;

}
