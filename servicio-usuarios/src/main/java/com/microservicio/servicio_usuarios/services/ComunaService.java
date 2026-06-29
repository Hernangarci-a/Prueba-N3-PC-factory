package com.microservicio.servicio_usuarios.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.microservicio.servicio_usuarios.dto.ComunaDTO;
import com.microservicio.servicio_usuarios.model.Comuna;
import com.microservicio.servicio_usuarios.model.Region;
import com.microservicio.servicio_usuarios.repository.ComunaRepository;
import com.microservicio.servicio_usuarios.repository.RegionRepository;

import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;

@Service
@Transactional
public class ComunaService {
    @Autowired
    private ComunaRepository comunaRepository;

    @Autowired
    private RegionRepository regionRepository;

    public List<ComunaDTO> obtenerTodos() {
        return comunaRepository.findAll().stream()
                .map(this::convertirADTO)
                .toList();
    }

    public ComunaDTO buscarPorId(Integer id) {
        Comuna comuna = comunaRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("no se encontro comuna con ID: " + id));
        return convertirADTO(comuna);
    }

    public Comuna convertirComuna(ComunaDTO comuna) {
        Comuna c = new Comuna();
        c.setId_comuna(comuna.getIdComuna());
        c.setNombre_comuna(comuna.getNombreComuna());
        for (Region r : regionRepository.findAll()) {
            if (r.getNombre_region().equals(comuna.getNombreRegion())) {
                c.setRegion(r);
                break;
            }
        }
        return c;
    }

    public ComunaDTO guardarComuna(ComunaDTO c) {
        Comuna comuna = convertirComuna(c);
        if (comuna.getNombre_comuna() == null || comuna.getNombre_comuna().trim().isEmpty()) {
            throw new RuntimeException("el nombre no debe estra vacio");
        }
        Comuna guardada = comunaRepository.save(comuna);
        return convertirADTO(guardada);
    }

    public String eliminarComuna(Integer id) {
        try {
            Comuna comuna = comunaRepository.findById(id)
                    .orElseThrow(
                            () -> new RuntimeException("no se puede eliminar comuna con ID " + id + " no existe."));
            comunaRepository.delete(comuna);
            return "la comuna " + comuna.getNombre_comuna() + " se elimino";
        } catch (RuntimeException e) {
            return e.getMessage();
        }
    }

    private ComunaDTO convertirADTO(Comuna comuna) {
        ComunaDTO dto = new ComunaDTO();
        dto.setIdComuna(comuna.getId_comuna());
        dto.setNombreComuna(comuna.getNombre_comuna());
        dto.setNombreRegion(comuna.getRegion().getNombre_region());
        return dto;
    }
}