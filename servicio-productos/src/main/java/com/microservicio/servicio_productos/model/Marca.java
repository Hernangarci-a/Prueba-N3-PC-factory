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
    @Column(name = "id_marcas") // Le dices a JPA el nombre exacto del SQL
    private Integer idMarca;

    @NotBlank(message = "El nombre de la marca es obligatorio")
    @Size(min = 3, max = 50, message = "El nombre de la marca debe tener entre 3 y 50 caracteres")
    @Column(name = "nombre_marca", nullable = false, length = 50)
    private String nombreMarca;

    @OneToMany(mappedBy = "marca")
    private List<Productos> productos;

}
