package com.microservicio.servicio_productos.model;

import java.util.List;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "tipos_productos") // clase padre
public class TipoProducto {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_tipos_productos")
    private Integer idTipoProducto;

    @NotBlank(message = "El nombre del tipo es obligatorio")
    @Column(name = "nombre_tipo_producto", nullable = false, length = 50)
    private String nombreTipoProducto;

    @OneToMany(mappedBy = "tipoProducto")
    private List<Productos> productos;
}
