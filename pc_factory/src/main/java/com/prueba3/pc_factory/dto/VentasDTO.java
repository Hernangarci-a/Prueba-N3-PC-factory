package com.prueba3.pc_factory.dto;

import java.time.LocalDateTime;
import java.util.List;
import lombok.Data;

@Data
public class VentasDTO {
    private Integer idVenta;
    private LocalDateTime fecha;
    private double total;
    private String nombreCliente;
    private String nombreSucursal;
    private String nombreTipoPago;
    private String nombreTipoVenta;
    private String nombreTipoDespacho;
    private List<String> nombresProductos;
}