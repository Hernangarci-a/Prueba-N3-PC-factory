package com.prueba3.pc_factory.model;

import java.time.LocalDateTime;
import java.util.List;

import jakarta.persistence.*;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "ventas")
public class Ventas {

    @Id // Define la llave primaria
    @GeneratedValue(strategy = GenerationType.IDENTITY) // Indica que es AUTO_INCREMENT en MySQL
    private Integer idVenta;

    @NotNull(message = "El folio no puede quedar vacio")
    @Positive(message = "El folio debe ser mayor a cero")
    @Max(value = 9999999)
    @Column(nullable = false)
    private Integer folio;

    @Column(nullable = false)
    private LocalDateTime fecha;

    @NotNull(message = "El total es obligatorio")
    @Min(value = 100)
    @Max(value = 10000000)
    @Positive(message = "El precio debe ser mayor a cero")
    @Column(nullable = false)
    private double total;

    // Relaciones con otras entidades

    // Relación Una categoría tiene muchos productos
    @ManyToMany(mappedBy = "ventas")
    private List<Productos> productos;

    // Relacion muchas ventas tienen un cliente
    @ManyToOne
    @JoinColumn(name = "cliente_id")
    private Cliente cliente;

    @ManyToOne
    @JoinColumn(name = "sucursal_id")
    private Sucursal sucursal;

    @ManyToOne
    @JoinColumn(name = "tipo_pago_id")
    private TipoPago tipoPago;

    @ManyToOne
    @JoinColumn(name = "tipo_venta_id")
    private TipoVenta tipoVenta;

    @ManyToOne
    @JoinColumn(name = "tipo_despacho_id")
    private TipoDespacho tipoDespacho;

}