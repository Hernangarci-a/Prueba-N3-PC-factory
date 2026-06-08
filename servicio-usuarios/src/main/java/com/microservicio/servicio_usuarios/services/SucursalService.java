package com.microservicio.servicio_usuarios.services;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.microservicio.servicio_usuarios.dto.SucursalDTO;
import com.microservicio.servicio_usuarios.model.Colaborador;
import com.microservicio.servicio_usuarios.model.Sucursal;
import com.microservicio.servicio_usuarios.repository.ColaboradorRepository;
import com.microservicio.servicio_usuarios.repository.SucursalRepository;

import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;

@Service
@Transactional
public class SucursalService {

    @Autowired
    private SucursalRepository sucursalRepository;

    @Autowired
    private ColaboradorRepository colaboradorRepository;

    public List<SucursalDTO> obtenerTodos() {
        return sucursalRepository.findAll().stream()
                .map(this::convertirADTO)
                .toList();
    }

    public SucursalDTO buscarPorId(Integer id) {
        Sucursal sucursal = sucursalRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("no se encontro sucursal con ID: " + id));
        return convertirADTO(sucursal);
    }

    public Sucursal buscarSucursal(Integer id) {
        return sucursalRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("no se encontro sucural con ID: " + id));
    }

    public Sucursal guardarSucursal(Sucursal sucursal) {
        if (sucursal.getNombreSucursal() == null || sucursal.getNombreSucursal().trim().isEmpty()) {
            throw new RuntimeException("el nombre no debe estar vacio");
        }
        return sucursalRepository.save(sucursal);
    }

    public Sucursal añadirColaborador(Integer idSucursal, Integer idColaborador) {
        Sucursal sucursal = sucursalRepository.findById(idSucursal)
                .orElseThrow(() -> new EntityNotFoundException("no se encontro"));
        Colaborador colaborador = colaboradorRepository.findById(idColaborador)
                .orElseThrow(() -> new EntityNotFoundException("no se encontro"));
        sucursal.getColaboradores().add(colaborador);
        return sucursalRepository.save(sucursal);
    }

    public String eliminarSucursal(Integer id) {
        try {
            Sucursal sucursal = sucursalRepository.findById(id)
                    .orElseThrow(() -> new RuntimeException(
                            "no se puede eliminar la sucursal con ID " + id + " no existe."));
            sucursalRepository.delete(sucursal);
            return "la sucursal " + sucursal.getNombreSucursal() + " se elimino";
        } catch (RuntimeException e) {
            return e.getMessage();
        }
    }

    private SucursalDTO convertirADTO(Sucursal sucursal) {
        SucursalDTO dto = new SucursalDTO();
        dto.setIdSucursal(sucursal.getIdSucursal());
        dto.setNombreSucursal(sucursal.getNombreSucursal());
        dto.setDireccionSucursal(sucursal.getDireccionSucursal());

        if (sucursal.getComuna() != null) {
            dto.setNombreComuna(sucursal.getComuna().getNombre_comuna());
        }
        if (sucursal.getColaboradores() != null) {
            dto.setNombresColaboradores(sucursal.getColaboradores().stream()
                    .map(Colaborador::getNombreColaborador)
                    .toList());
        } else {
            dto.setNombresColaboradores(new ArrayList<>());
        }
        return dto;
    }

    public Sucursal actualizarSucursal(Integer id, Sucursal sucursal) {
        Sucursal sucurs = sucursalRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("El producto no existe en el catalogo"));
        if (sucursal.getNombreSucursal() != null) {
            if (sucursal.getNombreSucursal().trim().isEmpty()) {
                throw new RuntimeException("El nuevo nombre es muy corto, debe tener al menos 25 caracteres.");
            }
            sucurs.setNombreSucursal(sucursal.getNombreSucursal());
        }

        if (sucursal.getDireccionSucursal() != null) {
            sucurs.setDireccionSucursal(sucurs.getDireccionSucursal());
        }
        // guarda el original los cambios del original que ya existe
        return sucursalRepository.save(sucurs);
    }

}