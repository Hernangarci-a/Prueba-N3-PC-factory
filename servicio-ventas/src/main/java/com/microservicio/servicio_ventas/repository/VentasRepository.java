package com.microservicio.servicio_ventas.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.microservicio.servicio_ventas.model.Ventas;

@Repository
public interface VentasRepository extends JpaRepository<Ventas, Integer> {

}
