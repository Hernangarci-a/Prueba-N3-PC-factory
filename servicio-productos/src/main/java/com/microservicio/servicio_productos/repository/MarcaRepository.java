package com.microservicio.servicio_productos.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.microservicio.servicio_productos.model.Marca;

@Repository
public interface MarcaRepository extends JpaRepository<Marca, Integer> {

}
