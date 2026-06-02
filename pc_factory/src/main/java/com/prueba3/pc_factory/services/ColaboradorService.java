package com.prueba3.pc_factory.services;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.prueba3.pc_factory.dto.ColaboradorDTO;
import com.prueba3.pc_factory.model.Colaborador;
import com.prueba3.pc_factory.model.Sucursal;
import com.prueba3.pc_factory.model.TipoColaborador;
import com.prueba3.pc_factory.repository.ColaboradorRepository;

import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;

@Service
@Transactional
public class ColaboradorService {

    @Autowired
    private ColaboradorRepository colaboradorRepository;

    public List<ColaboradorDTO> obtenerTodos() {
        return colaboradorRepository.findAll().stream()
                .map(this::convertirADTO)
                .toList();
    }

    public ColaboradorDTO buscarPorId(Integer id) {
        Colaborador colaborador = colaboradorRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("no se encontro colaborador con ID: " + id));
        return convertirADTO(colaborador);
    }

    public Colaborador buscarColaborador(Integer id) {
        return colaboradorRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("no se encontro colaborador con ID: " + id));
    }

    public Colaborador guardarColaborador(Colaborador colaborador) {
        if (colaborador.getNombreColaborador() == null || colaborador.getNombreColaborador().trim().isEmpty()) {
            throw new RuntimeException("el nombre no debe estra vacio");
        }
        return colaboradorRepository.save(colaborador);
    }

    public String eliminarColaborador(Integer id) {
        try {
            Colaborador colaborador = colaboradorRepository.findById(id)
                    .orElseThrow(() -> new RuntimeException(
                            "no se puede eliminar al colaborador con ID " + id + " no existe."));
            colaboradorRepository.delete(colaborador);
            return "el colaborador " + colaborador.getNombreColaborador() + " se elimino";
        } catch (RuntimeException e) {
            return e.getMessage();
        }
    }

    private ColaboradorDTO convertirADTO(Colaborador colaborador) {
        ColaboradorDTO dto = new ColaboradorDTO();
        dto.setIdColaborador(colaborador.getIdColaborador());
        dto.setNombreColaborador(colaborador.getNombreColaborador());
        dto.setRutColaborador(colaborador.getRutColaborador());
        dto.setCorreo(colaborador.getCorreo());
        dto.setTelefono(colaborador.getTelefono());
        dto.setActivo(colaborador.isActivo());

        if (colaborador.getSucursales() != null) {
            dto.setNombresSucursales(colaborador.getSucursales().stream()
                    .map(Sucursal::getNombreSucursal)
                    .toList());
        } else {
            dto.setNombresSucursales(new ArrayList<>());
        }
        if (colaborador.getTipocolaborador() != null) {
            dto.setTiposColaborador(colaborador.getTipocolaborador().stream()
                    .map(TipoColaborador::getNombre)
                    .toList());
        } else {
            dto.setTiposColaborador(new ArrayList<>());
        }
        return dto;
    }

    public Colaborador actualizarColaborador(Integer id, Colaborador colaborador) {
        // 1. Buscamos el cliente existente
        Colaborador colabor = colaboradorRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("EL colaborador no encontrado con id: " + id));
        if (colaborador.getNombreColaborador() != null) {
            if (colaborador.getNombreColaborador().trim().length() <= 25) {
                throw new RuntimeException("El nuevo nombre es muy corto, debe tener al menos 25 caracteres.");
            }

            colabor.setNombreColaborador(colaborador.getNombreColaborador());
        }
        if (colaborador.getNombreColaborador() != null) {
            colabor.setRutColaborador(colaborador.getRutColaborador());
        }
        if (colaborador.getCorreo() != null) {
            colabor.setCorreo(colaborador.getCorreo());
        }
        if (colaborador.getTelefono() != null) {
            colabor.setTelefono(colaborador.getTelefono());
        }
        if (colaborador.getTelefono() != null) {
            colabor.setTelefono(colaborador.getTelefono());
        }

        return colaboradorRepository.save(colaborador);
    }

}