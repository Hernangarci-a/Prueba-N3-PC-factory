package com.microservicio.servicio_ventas.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.microservicio.servicio_ventas.model.TipoDespacho;

@Repository
public interface TipoDespachoRepository extends JpaRepository<TipoDespacho, Integer> {

}
