package com.prueba3.pc_factory.dto;

import java.util.List;
import lombok.Data;

@Data
public class TipoVentaDTO {
    private Integer idTipoVenta;
    private String nombre;
    private List<Integer> foliosVentas;
}