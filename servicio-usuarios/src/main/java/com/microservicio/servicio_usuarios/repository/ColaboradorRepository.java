package com.microservicio.servicio_usuarios.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.microservicio.servicio_usuarios.model.Colaborador;

@Repository
public interface ColaboradorRepository extends JpaRepository<Colaborador, Integer> {

}
