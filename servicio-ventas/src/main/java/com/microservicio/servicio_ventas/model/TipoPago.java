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
@Table(name = "tipo_pago")
public class TipoPago {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer idTipoPago;

    // Aqui se coloca el nombre del tipo de pago ejemplo debito o credito
    @NotBlank(message = "El nombre es obligatorio")
    @Size(min = 3, max = 15, message = "El nombre debe tener entre 3 y 15 caracteres")
    @Column(nullable = false, length = 15)
    private String nombreTipoPago;

    // Relación inversa un solo tipo de pago tiene muchas ventas
    @OneToMany(mappedBy = "tipoPago")
    private List<Ventas> ventas;
}
