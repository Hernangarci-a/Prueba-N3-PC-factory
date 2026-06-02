package com.prueba3.pc_factory.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.prueba3.pc_factory.model.TipoColaborador;

@Repository
public interface TipoColaboradorRepository extends JpaRepository<TipoColaborador, Integer> {

}
