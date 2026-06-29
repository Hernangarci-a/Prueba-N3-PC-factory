package com.microservicio.servicio_productos.model;

import java.util.List;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data // Genera automáticamente Getters, Setters y toString
@AllArgsConstructor // Crea un constructor con todos los atributos
@NoArgsConstructor // Crea un constructor vacío

// JPA y base de datos
@Entity // Marca esta clase como una tabla de la base de datos
@Table(name = "categorias") // Indica el nombre exacto de la tabla en MySQL
public class Categoria {

    @Id // Define la llave primaria
    @GeneratedValue(strategy = GenerationType.IDENTITY) // Indica que es AUTO_INCREMENT en MySQL
    @Column(name = "id_categoria")
    private Integer idCategoria;

    @NotBlank(message = "El nombre de la categoria es obligatorio") // Validación de Java no puede ser nulo ni es vacío
    @Size(min = 3, max = 25, message = "El nombre debe tener entre 3 y 25 caracteres") // Límite de caracteres
    @Column(name = "nombre_categoria", nullable = false, unique = true, length = 25)
    private String nombreCategoria;

    @ManyToMany(mappedBy = "categorias")
    private List<Productos> productos;
}
