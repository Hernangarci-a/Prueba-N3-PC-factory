package com.prueba3.pc_factory.dto;

import java.util.List;
import lombok.Data;

@Data
public class TipoPagoDTO {
    private Integer idTipoPago;
    private String nombreTipoPago;
    private List<Integer> foliosVentas;
}