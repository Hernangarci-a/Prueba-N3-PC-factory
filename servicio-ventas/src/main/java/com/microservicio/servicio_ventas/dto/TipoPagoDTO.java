package com.microservicio.servicio_ventas.dto;

import java.util.List;
import lombok.Data;

@Data
public class TipoPagoDTO {
    private Integer idTipoPago;
    private String nombreTipoPago;
    private List<Integer> foliosVentas;
}