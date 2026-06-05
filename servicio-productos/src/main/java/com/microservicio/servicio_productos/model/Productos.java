package com.microservicio.servicio_productos.model;

import java.util.List;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
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
@Table(name = "productos") // Indica el nombre exacto de la tabla en MySQL
public class Productos {

        @Id
        @GeneratedValue(strategy = GenerationType.IDENTITY)
        @Column(name = "id")
        private Integer idProductos;

        @NotBlank(message = "El nombre es obligatorio") // Validación de Java no puede ser nulo ni estar vacío
        @Size(min = 3, max = 100, message = "El nombre debe tener entre 3 y 25 caracteres") // Límite de caracteres
        @Column(nullable = false, length = 100) // Esto es lo mismo que NOT NULL y VARCHAR(25) en la base de datos
        private String nombreProducto;

        @NotNull(message = "El precio no puede estar vacio")
        @Positive(message = "El precio debe ser mayor a cero")
        @Column(nullable = false)
        private double precioUnitario;

        @Column(length = 70)
        private String procesador;

        @Column(length = 20)
        private String memoria_ram;

        @Column(length = 20)
        private String almacenamiento;

        @ManyToMany
        @JoinTable(name = "categoria_productos", // El nombre que definido en el modelo
                        joinColumns = @JoinColumn(name = "producto_id"), // Llave de esta tabla
                        inverseJoinColumns = @JoinColumn(name = "categoria_id")) // Llave de la otra tabla
        private List<Categoria> categorias; // Ahora es una lista porque puede tener muchas categorias

        @ManyToOne
        @JoinColumn(name = "tipo_producto_id")
        private TipoProducto tipoProducto;

        @ManyToOne
        @JoinColumn(name = "marca_id")
        private Marca marca;

}
