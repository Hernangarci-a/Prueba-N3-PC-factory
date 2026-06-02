package com.prueba3.pc_factory.model;

import java.util.List;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Entity
@Table(name = "comuna")
public class Comuna {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id_comuna;

    @NotBlank(message = "El nombre de la comuna no puede estar vacio")
    @Size(max = 25)
    @Column(nullable = false, length = 25)
    private String nombre_comuna;

    @ManyToOne
    @JoinColumn(name = "id_region")
    private Region region;

    @OneToMany(mappedBy = "comuna")
    private List<Sucursal> sucursales;

    @OneToMany(mappedBy = "comuna")
    private List<Cliente> clientes;

}
