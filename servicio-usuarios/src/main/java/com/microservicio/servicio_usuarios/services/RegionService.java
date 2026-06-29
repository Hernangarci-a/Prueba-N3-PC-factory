package com.microservicio.servicio_usuarios.services;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.microservicio.servicio_usuarios.dto.RegionDTO;
import com.microservicio.servicio_usuarios.model.Comuna;
import com.microservicio.servicio_usuarios.model.Region;
import com.microservicio.servicio_usuarios.repository.ComunaRepository;
import com.microservicio.servicio_usuarios.repository.RegionRepository;

import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;

@Service
@Transactional
public class RegionService {

    @Autowired
    private RegionRepository regionRepository;

    @Autowired
    private ComunaRepository comunaRepository;

    public List<RegionDTO> obtenerTodos() {
        return regionRepository.findAll().stream()
                .map(this::convertirADTO)
                .toList();
    }

    public RegionDTO buscarPorId(Integer id) {
        Region region = regionRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("no se encontro region con ID: " + id));
        return convertirADTO(region);
    }

    public RegionDTO guardarRegion(RegionDTO region) {
        if (region.getNombreRegion() == null || region.getNombreRegion().trim().isEmpty()) {
            throw new RuntimeException("el nombre no debe estar vacio");
        }
        Region entidad = convertirRegion(region);
        Region guardada = regionRepository.save(entidad);
        return convertirADTO(guardada);
    }

    public String eliminarRegion(Integer id) {
        try {
            Region region = regionRepository.findById(id)
                    .orElseThrow(
                            () -> new RuntimeException("no se puede eliminar region con ID " + id + " no existe."));
            regionRepository.delete(region);
            return "la region " + region.getNombre_region() + " se elimino";
        } catch (RuntimeException e) {
            return e.getMessage();
        }
    }

    public Region convertirRegion(RegionDTO dto) {
        Region region = new Region();
        region.setId_region(dto.getIdRegion());
        region.setNombre_region(dto.getNombreRegion());

        List<Comuna> comunas = new ArrayList<>();

        for (String nombre : dto.getNombresComunas()) {
            for (Comuna c : comunaRepository.findAll()) {
                if (c.getNombre_comuna().equals(nombre)) {
                    comunas.add(c);
                    break;
                }
            }
        }
        region.setComunas(comunas);
        return region;
    }

    private RegionDTO convertirADTO(Region region) {
        RegionDTO dto = new RegionDTO();
        dto.setIdRegion(region.getId_region());
        dto.setNombreRegion(region.getNombre_region());
        dto.setNombresComunas(new ArrayList<>());
        return dto;
    }
}