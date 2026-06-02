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
@Table(name = "sucursal")
public class Sucursal {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer idSucursal;

    @NotBlank(message = "El nombre de la sucursal no puede estar vacio")
    @Size(min = 5, max = 30, message = "El nombre de la sucursal debe estar entre 5 a 30 caracteres")
    @Column(nullable = false, length = 30)
    private String nombreSucursal;

    @NotBlank(message = "La dirección no puede estar vacía")
    @Size(max = 60)
    @Column(nullable = false, length = 60)
    private String direccionSucursal;

    @OneToMany(mappedBy = "sucursal")
    private List<Ventas> ventas;

    @ManyToOne
    @JoinColumn(name = "id_comuna")
    private Comuna comuna;

    @ManyToMany
    @JoinTable(name = "sucursal_colaborador", // Nombre de la tabla intermedia
            joinColumns = @JoinColumn(name = "sucursal_id"), inverseJoinColumns = @JoinColumn(name = "colaborador_id"))
    private List<Colaborador> colaboradores;

}
