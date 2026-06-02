package com.prueba3.pc_factory.dto;

import java.util.List;
import lombok.Data;

@Data
public class TipoColaboradorDTO {
    private Integer idTipoColaborador;
    private String nombre;
    private List<String> nombresColaboradores;
}