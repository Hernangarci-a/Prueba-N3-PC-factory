package com.prueba3.pc_factory.model;

import java.util.List;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

//lombok
@Data // Genera automáticamente Getters, Setters y toString
@AllArgsConstructor // Crea un constructor con todos los atributos
@NoArgsConstructor // Crea un constructor vacío

// JPA y base de datos
@Entity // Marca esta clase como una tabla de la base de datos
@Table(name = "marcas") // Indica el nombre exacto de la tabla en MySQL
public class Marca {

    @Id // Define la llave primaria
    @GeneratedValue(strategy = GenerationType.IDENTITY) // Indica que es AUTO_INCREMENT en MySQL
    private Integer id;

    @NotBlank(message = "El nombre de la marca es obligatorio")
    @Size(min = 3, max = 20, message = "El nombre de la marca debe tener entre 3 y 20 caracteres")
    @Column(nullable = false, length = 25)
    private String nombre_marca;

    @OneToMany(mappedBy = "marca")
    private List<Productos> productos;

}
