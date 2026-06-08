package com.microservicio.servicio_ventas.dto;

import java.time.LocalDateTime;
import lombok.Data;

@Data
public class VentasDTO {
    private Integer idVenta;
    private LocalDateTime fecha;
    private double total;
    private String nombreTipoPago;
    private String nombreTipoVenta;
    private String nombreTipoDespacho;
}