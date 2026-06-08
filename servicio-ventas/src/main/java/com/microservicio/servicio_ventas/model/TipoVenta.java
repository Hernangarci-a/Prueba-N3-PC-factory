package com.microservicio.servicio_ventas.model;

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
@Table(name = "tipos_venta")
public class TipoVenta {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer idTipoVenta;

    // Aqui se coloca el nombre del tipo de venta ejemplo presencial o online
    @NotBlank(message = "El nombre es obligatorio")
    @Size(min = 3, max = 15, message = "El nombre debe tener entre 3 y 15 caracteres")
    @Column(nullable = false, length = 15)
    private String nombre;

    // Relación inversa Un tipo de venta puede tener muchas ventas
    @OneToMany(mappedBy = "tipoVenta")
    private List<Ventas> ventas;
}
