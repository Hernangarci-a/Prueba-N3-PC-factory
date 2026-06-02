package com.prueba3.pc_factory.model;

import java.util.List;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "tipoColaborador")
public class TipoColaborador {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer idTipoColaborador;

    @NotBlank(message = "El nombre del tipo es obligatorio")
    @Column(nullable = false, length = 30)
    private String nombre; // Eje Vendedor, Bodeguero, Administrativo

    @ManyToMany
    @JoinTable(name = "tipo_colaborador_relacion", joinColumns = @JoinColumn(name = "tipo_colaborador_id"), inverseJoinColumns = @JoinColumn(name = "colaborador_id"))
    private List<Colaborador> colaboradores;
}
