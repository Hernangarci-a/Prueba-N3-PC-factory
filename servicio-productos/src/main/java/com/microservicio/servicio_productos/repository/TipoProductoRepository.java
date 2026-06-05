package com.microservicio.servicio_productos.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.microservicio.servicio_productos.model.TipoProducto;

@Repository
public interface TipoProductoRepository extends JpaRepository<TipoProducto, Integer> {

}
