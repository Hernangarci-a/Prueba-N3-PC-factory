package com.prueba3.pc_factory.dto;

import java.util.List;
import lombok.Data;

@Data
public class RegionDTO {
    private Integer idRegion;
    private String nombreRegion;
    private List<String> nombresComunas;
}