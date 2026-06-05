package com.microservicio.servicio_ventas.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.microservicio.servicio_ventas.dto.VentasDTO;
import com.microservicio.servicio_ventas.model.Ventas;
import com.microservicio.servicio_ventas.repository.VentasRepository;

import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;

@Service
@Transactional
public class VentasService {
    @Autowired
    private VentasRepository ventasRepository;

    public List<VentasDTO> obtenerTodos() {
        return ventasRepository.findAll().stream()
                .map(this::convertirADTO)
                .toList();
    }

    public VentasDTO buscarPorId(Integer id) {
        Ventas venta = ventasRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("no se encontro la venta con ID: " + id));
        return convertirADTO(venta);
    }

    public Ventas guardarVenta(Ventas venta) {
        if (venta.getIdVenta() == null) {
            throw new RuntimeException("Error el el producto no debe estar vacio");
        }
        return ventasRepository.save(venta);
    }

    public String eliminarVenta(Integer id) {
        try {
            Ventas venta = ventasRepository.findById(id)
                    .orElseThrow(() -> new RuntimeException("no se puede la venta con ID " + id + " no existe"));
            ventasRepository.delete(venta);
            return "La venta " + venta.getIdVenta() + " se elimino";
        } catch (RuntimeException e) {
            return e.getMessage();
        }
    }

    private VentasDTO convertirADTO(Ventas venta) {
        VentasDTO dto = new VentasDTO();
        dto.setIdVenta(venta.getIdVenta());
        dto.setFecha(venta.getFecha());
        dto.setTotal(venta.getTotal());

        if (venta.getTipoPago() != null) {
            dto.setNombreTipoPago(venta.getTipoPago().getNombreTipoPago());
        }

        if (venta.getTipoVenta() != null) {
            dto.setNombreTipoVenta(venta.getTipoVenta().getNombre());
        }

        if (venta.getTipoDespacho() != null) {
            dto.setNombreTipoDespacho(venta.getTipoDespacho().getNombreTipoDespacho());
        }

        return dto;
    }

    public Ventas actualizarVenta(Integer id, Ventas ventas) {
        Ventas vent = ventasRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("El producto no existe en el catalogo"));
        if (ventas.getFolio() != 0) {
            vent.setFolio(ventas.getFolio());
        }
        if (ventas.getFecha() != null) {
            vent.setFecha(ventas.getFecha());
        }
        if (ventas.getTotal() != 0.0) {
            vent.setTotal(ventas.getTotal());
        }
        return ventasRepository.save(vent);
    }

}