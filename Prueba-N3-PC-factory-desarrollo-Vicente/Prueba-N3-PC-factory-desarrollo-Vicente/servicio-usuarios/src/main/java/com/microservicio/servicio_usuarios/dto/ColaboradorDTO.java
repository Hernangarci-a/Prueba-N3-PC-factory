package com.microservicio.servicio_usuarios.dto;

import java.util.List;
import lombok.Data;

@Data
public class ColaboradorDTO {
    private Integer idColaborador;
    private String nombreColaborador;
    private String rutColaborador;
    private String correo;
    private String telefono;
    private boolean activo;
    private List<String> nombresSucursales;
    private List<String> tiposColaborador;
}