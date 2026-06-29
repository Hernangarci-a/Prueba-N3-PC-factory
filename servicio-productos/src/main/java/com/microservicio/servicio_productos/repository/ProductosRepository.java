package com.microservicio.servicio_productos.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.microservicio.servicio_productos.dto.ProductosDTO;
import com.microservicio.servicio_productos.model.Productos;

// @Repository le dice a Spring que esta interfaz manejará el acceso a datos
@Repository
public interface ProductosRepository extends JpaRepository<Productos, Integer> {
    // Aquí podemos agregar métodos de búsqueda personalizado
}
