package com.prueba3.pc_factory.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.prueba3.pc_factory.dto.ComunaDTO;
import com.prueba3.pc_factory.model.Comuna;
import com.prueba3.pc_factory.repository.ComunaRepository;

import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;

@Service
@Transactional
public class ComunaService {
    @Autowired
    private ComunaRepository comunaRepository;

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

    public Comuna guardarComuna(Comuna comuna) {
        if (comuna.getNombre_comuna() == null || comuna.getNombre_comuna().trim().isEmpty()) {
            throw new RuntimeException("el nombre no debe estra vacio");
        }
        return comunaRepository.save(comuna);
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
        return dto;
    }
}