package com.prueba3.pc_factory.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.prueba3.pc_factory.model.Colaborador;

@Repository
public interface ColaboradorRepository extends JpaRepository<Colaborador, Integer> {

}
