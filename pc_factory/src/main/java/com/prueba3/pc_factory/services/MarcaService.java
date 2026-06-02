package com.prueba3.pc_factory.services;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.prueba3.pc_factory.dto.MarcaDTO;
import com.prueba3.pc_factory.model.Marca;
import com.prueba3.pc_factory.model.Productos;
import com.prueba3.pc_factory.repository.MarcaRepository;
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
        if (marca.getNombre_marca() == null || marca.getNombre_marca().trim().isEmpty()) {
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
            return "la marca " + marca.getNombre_marca() + " se elimino";
        } catch (RuntimeException e) {
            return e.getMessage();
        }
    }

    private MarcaDTO convertirADTO(Marca marca) {
        MarcaDTO dto = new MarcaDTO();
        dto.setIdMarca(marca.getId());
        dto.setNombreMarca(marca.getNombre_marca());

        // ahí tira error porque yo no tengo el service,repository y dto de productos
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