package com.prueba3.pc_factory.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.prueba3.pc_factory.model.Productos;

// @Repository le dice a Spring que esta interfaz manejará el acceso a datos
@Repository
public interface ProductosRepository extends JpaRepository<Productos, Integer> {
    // Aquí podemos agregar métodos de búsqueda personalizados
}
