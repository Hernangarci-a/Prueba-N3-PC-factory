package com.prueba3.pc_factory.model;

import java.util.List;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "tipos_despacho")
public class TipoDespacho {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer idTipoDespacho;

    @NotBlank(message = "El nombre del despacho es obligatorio")
    @Size(min = 3, max = 30)
    @Column(nullable = false, length = 30)
    private String nombreTipoDespacho;

    // Relación inversa: Un tipo de despacho puede estar en muchas ventas
    @OneToMany(mappedBy = "tipoDespacho")
    private List<Ventas> ventas;
}
