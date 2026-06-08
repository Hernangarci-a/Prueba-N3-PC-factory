package com.microservicio.servicio_ventas.dto;

import java.util.List;
import lombok.Data;

@Data
public class TipoVentaDTO {
    private Integer idTipoVenta;
    private String nombre;
    private List<Integer> foliosVentas;
}