package com.microservicio.servicio_ventas.services;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.microservicio.servicio_ventas.dto.TipoVentaDTO;
import com.microservicio.servicio_ventas.model.TipoVenta;
import com.microservicio.servicio_ventas.repository.TipoVentaRepository;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;



@Service
@Transactional
public class TipoVentaService {

    @Autowired
    private TipoVentaRepository tipoVentaRepository;

    public List<TipoVentaDTO> obtenerTodos(){
        return tipoVentaRepository.findAll().stream()
                .map(this::convertirADTO)
                .toList();
    }

    public TipoVentaDTO buscarPorId(Integer id){
        TipoVenta tipo = tipoVentaRepository.findById(id).orElseThrow(() -> new EntityNotFoundException("no encontrado con ID: " + id));
        return convertirADTO(tipo);
    }

    private TipoVentaDTO convertirADTO(TipoVenta tipo) {
        TipoVentaDTO dto = new TipoVentaDTO();
        dto.setIdTipoVenta(tipo.getIdTipoVenta());
        dto.setNombre(tipo.getNombre());
        return dto;
    }
}