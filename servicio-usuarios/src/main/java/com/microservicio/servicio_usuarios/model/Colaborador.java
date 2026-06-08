package com.microservicio.servicio_usuarios.model;

import java.util.List;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToMany;
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
@Table(name = "colaboradores")
public class Colaborador {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer idColaborador;

    @NotBlank(message = "El nombre del colborador es obligatorio")
    @Size(max = 25)
    @Column(nullable = false, length = 25)
    private String nombreColaborador;

    @NotBlank(message = "El rut del colaborador es obligatorio")
    @Size(min = 9, max = 9)
    @Column(unique = true, length = 9)
    private String rutColaborador;

    @Email(message = "Formato de correo inválido")
    @Column(unique = true, length = 50)
    private String correo;

    @Column(length = 9)
    private String telefono;

    @Column(nullable = false)
    private boolean activo = true; // Por defecto entra activo

    // Relación inversa ManyToMany
    @ManyToMany(mappedBy = "colaboradores")
    private List<Sucursal> sucursales;

    @ManyToMany(mappedBy = "colaboradores")
    private List<TipoColaborador> tipocolaborador;

}
