package com.prueba3.pc_factory.model;

import java.util.List;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "region")
public class Region {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id_region;

    @NotNull(message = "El nombre de la region no puede estar vacio")
    @Column(nullable = false, name = "nombre_region")
    private String nombre_region;

    // Relación inversa Una región tiene muchas comunas
    @OneToMany(mappedBy = "region")
    private List<Comuna> comunas;

}
