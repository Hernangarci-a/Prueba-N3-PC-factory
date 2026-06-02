package com.prueba3.pc_factory.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.prueba3.pc_factory.dto.TipoPagoDTO;
import com.prueba3.pc_factory.model.TipoPago;
import com.prueba3.pc_factory.repository.TipoPagoRepository;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;

@Service
@Transactional
public class TipoPagoService {
    @Autowired
    private TipoPagoRepository tipoPagoRepository;

    public List<TipoPagoDTO> obtenerTodos() {
        return tipoPagoRepository.findAll().stream()
                .map(this::convertirADTO)
                .toList();
    }

    // No entiendo que hacer con este service

    public TipoPagoDTO buscarPorId(Integer id) {
        TipoPago tipo = tipoPagoRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("TipoPago no encontrado con ID: " + id));
        return convertirADTO(tipo);
    }

    private TipoPagoDTO convertirADTO(TipoPago tipo) {
        TipoPagoDTO dto = new TipoPagoDTO();
        dto.setIdTipoPago(tipo.getIdTipoPago());
        dto.setNombreTipoPago(tipo.getNombreTipoPago());
        return dto;
    }
}