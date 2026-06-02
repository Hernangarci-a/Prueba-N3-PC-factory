package com.prueba3.pc_factory.dto;

import java.util.List;
import lombok.Data;

@Data
public class TipoDespachoDTO {
    private Integer idTipoDespacho;
    private String nombreTipoDespacho;
    private List<Integer> foliosVentas;
}