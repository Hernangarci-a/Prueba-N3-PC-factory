package com.microservicio.servicio_usuarios.dto;

import java.util.List;
import lombok.Data;

@Data
public class RegionDTO {
    private Integer idRegion;
    private String nombreRegion;
    private List<String> nombresComunas;
}