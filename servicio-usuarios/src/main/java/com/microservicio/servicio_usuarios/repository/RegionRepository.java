package com.microservicio.servicio_usuarios.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.microservicio.servicio_usuarios.model.Region;

@Repository
public interface RegionRepository extends JpaRepository<Region, Integer> {

}
