package com.microservicio.servicio_productos.services;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.microservicio.servicio_productos.dto.MarcaDTO;
import com.microservicio.servicio_productos.model.Marca;
import com.microservicio.servicio_productos.model.Productos;
import com.microservicio.servicio_productos.repository.MarcaRepository;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;

@Service
@Transactional
public class MarcaService {

    @Autowired
    private MarcaRepository marcaRepository;

    public List<MarcaDTO> obtenerTodos() {
        return marcaRepository.findAll().stream()
                .map(this::convertirADTO)
                .toList();
    }

    public MarcaDTO buscarPorId(Integer id) {
        Marca marca = marcaRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("no se encontro la marca con el ID: " + id));
        return convertirADTO(marca);
    }

    public Marca guardarMarca(Marca marca) {
        if (marca.getNombreMarca() == null || marca.getNombreMarca().trim().isEmpty()) {
            throw new RuntimeException("el nombre no debe estar vacio");
        }
        return marcaRepository.save(marca);
    }

    public String eliminarMarca(Integer id) {
        try {
            Marca marca = marcaRepository.findById(id)
                    .orElseThrow(() -> new RuntimeException(
                            "no se puede eliminar la marca con el ID " + id + " no existe."));
            marcaRepository.delete(marca);
            return "la marca " + marca.getNombreMarca() + " se elimino";
        } catch (RuntimeException e) {
            return e.getMessage();
        }
    }

    private MarcaDTO convertirADTO(Marca marca) {
        MarcaDTO dto = new MarcaDTO();
        dto.setIdMarca(marca.getIdMarca());
        dto.setNombreMarca(marca.getNombreMarca());

        if (marca.getProductos() != null) {
            dto.setNombresProductos(marca.getProductos().stream()
                    .map(Productos::getNombreProducto)
                    .toList());
        } else {
            dto.setNombresProductos(new ArrayList<>());
        }
        return dto;
    }
}