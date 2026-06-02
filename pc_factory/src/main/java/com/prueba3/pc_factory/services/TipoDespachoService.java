package com.prueba3.pc_factory.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.prueba3.pc_factory.dto.TipoDespachoDTO;
import com.prueba3.pc_factory.model.TipoDespacho;
import com.prueba3.pc_factory.repository.TipoDespachoRepository;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;

@Service
@Transactional
public class TipoDespachoService {
    @Autowired
    private TipoDespachoRepository tipoDespachoRepository;

    public List<TipoDespachoDTO> obtenerTodos() {
        return tipoDespachoRepository.findAll().stream()
                .map(this::convertirADTO)
                .toList();
    }

    // No entiendo que hacer con este service

    public TipoDespacho guardarTipoDespacho(TipoDespacho tipoDespacho) {
        if (tipoDespacho.getNombreTipoDespacho() == null || tipoDespacho.getNombreTipoDespacho().trim().isEmpty()) {
            throw new RuntimeException("Error el nombre no puede estar vacio");
        }
        return tipoDespachoRepository.save(tipoDespacho);
    }

    public TipoDespachoDTO buscarPorId(Integer id) {
        TipoDespacho tipo = tipoDespachoRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("no se ha encontrado con ID: " + id));
        return convertirADTO(tipo);
    }

    private TipoDespachoDTO convertirADTO(TipoDespacho tipo) {
        TipoDespachoDTO dto = new TipoDespachoDTO();
        dto.setIdTipoDespacho(tipo.getIdTipoDespacho());
        dto.setNombreTipoDespacho(tipo.getNombreTipoDespacho());
        return dto;
    }

    public TipoDespacho actualizarTipoDespacho(Integer id, TipoDespacho tipoDespacho) {
        TipoDespacho TipoDespa = tipoDespachoRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("El despacho no existe "));
        if (tipoDespacho.getNombreTipoDespacho() != null) {
            if (tipoDespacho.getNombreTipoDespacho().trim().isEmpty()) {
                throw new RuntimeException("El nuevo nombre no puede estar vacio");
            }
            TipoDespa.setNombreTipoDespacho(tipoDespacho.getNombreTipoDespacho());
        }
        return tipoDespachoRepository.save(TipoDespa);
    }

    public String eliminartipoDespacho(Integer id) {
        // se usa try porque borrar algo es una proceso delicado puede fallar
        try {
            TipoDespacho tipoDespacho = tipoDespachoRepository.findById(id).orElseThrow(
                    () -> new RuntimeException("¡Imposible eliminar! El producto con ID " + id + " no existe."));
            tipoDespachoRepository.delete(tipoDespacho);
            return "El tipo colaborador a sido eliminado";

        } catch (RuntimeException e) {
            return e.getMessage();
        }
    }
}