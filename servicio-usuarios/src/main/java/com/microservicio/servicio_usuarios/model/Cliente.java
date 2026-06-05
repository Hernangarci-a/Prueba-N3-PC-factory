package com.microservicio.servicio_usuarios.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "cliente")
public class Cliente {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer idCliente;

    @NotBlank(message = "El nombre es obligatorio")
    @Size(min = 3, max = 25, message = "El nombre debe tener entre 3 y 25 caracteres")
    @Column(nullable = false, length = 25, unique = true) // Unique es para que rut sea unico y no se repita en la basde
                                                          // datos
    private String rut;

    @NotBlank(message = "El nombre es obligatorio")
    @Size(min = 3, max = 25, message = "El nombre debe tener entre 3 y 25 caracteres")
    @Column(nullable = false, length = 25)
    private String nombreCliente;

    @NotBlank(message = "El apellido no puede estar vacio")
    @Size(min = 3, max = 25, message = "El apellido debe tener entre 3 y 25 caracteres")
    @Column(nullable = false, length = 25)
    private String apellidoCliente;

    @Email(message = "Formato de correo inválido") // esta anotacion sirve para que tenga fromato email debe tener @.com
    @NotBlank(message = "El correo no puede estar vacio")
    @Size(max = 50, message = "El correo debe tener entre 3 y 50 caracteres")
    @Column(nullable = false, length = 50)
    private String correo;

    @NotBlank(message = "La dirección no puede estar vacía")
    @Size(max = 65)
    @Column(nullable = false, length = 65)
    private String direccion;

    @NotBlank(message = "El telefono no puede estar vacio")
    @Size(min = 9, max = 9, message = "El telefono debe tener entre 8 y 9 caracteres")
    @Column(nullable = false, length = 9)
    private String telefono;

    @ManyToOne
    @JoinColumn(name = "id_comuna")
    private Comuna comuna;

}