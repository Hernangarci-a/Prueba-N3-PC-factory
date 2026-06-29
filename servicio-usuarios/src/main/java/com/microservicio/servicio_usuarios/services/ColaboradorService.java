package com.microservicio.servicio_usuarios.services;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.microservicio.servicio_usuarios.dto.ColaboradorDTO;
import com.microservicio.servicio_usuarios.model.Colaborador;
import com.microservicio.servicio_usuarios.model.Sucursal;
import com.microservicio.servicio_usuarios.model.TipoColaborador;
import com.microservicio.servicio_usuarios.repository.ColaboradorRepository;
import com.microservicio.servicio_usuarios.repository.SucursalRepository;
import com.microservicio.servicio_usuarios.repository.TipoColaboradorRepository;

import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;

@Service
@Transactional
public class ColaboradorService {

    @Autowired
    private ColaboradorRepository colaboradorRepository;

    @Autowired
    private SucursalRepository sucursalRepository;

    @Autowired
    TipoColaboradorRepository tipoColaboradorRepository;

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

    public ColaboradorDTO guardarColaborador(Colaborador colaborador) {
        if (colaborador.getNombreColaborador() == null || colaborador.getNombreColaborador().trim().isEmpty()) {
            throw new RuntimeException("el nombre no debe estar vacio");
        }
        Colaborador guardado = colaboradorRepository.save(colaborador);
        return convertirADTO(guardado);
    }

    public String eliminarColaborador(Integer id) {
        try {
            colaboradorRepository.deleteById(id);
            return "el colaborador se elimino";
        } catch (RuntimeException e) {
            return e.getMessage();
        }
    }

    public Colaborador convertirColaborador(ColaboradorDTO c) {
        Colaborador colaborador = new Colaborador();

        colaborador.setIdColaborador(c.getIdColaborador());
        colaborador.setNombreColaborador(c.getNombreColaborador());
        colaborador.setRutColaborador(c.getRutColaborador());
        colaborador.setCorreo(c.getCorreo());
        colaborador.setTelefono(c.getTelefono());
        colaborador.setActivo(c.isActivo());
        List<Sucursal> sucursales = new ArrayList<>();
        for (String nombre : c.getNombresSucursales()) {
            for (Sucursal s : sucursalRepository.findAll()) {
                if (s.getNombreSucursal().equals(nombre)) {
                    sucursales.add(s);
                    break;
                }
            }
        }

        List<TipoColaborador> tipos = new ArrayList<>();

        for (String nombre : c.getTiposColaborador()) {
            for (TipoColaborador t : tipoColaboradorRepository.findAll()) {
                if (t.getNombre().equals(nombre)) {
                    tipos.add(t);
                    break;
                }
            }
        }

        colaborador.setSucursales(sucursales);
        colaborador.setTipocolaborador(tipos);
        return colaborador;
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

    public ColaboradorDTO actualizarColaborador(Integer id, Colaborador colaborador) {
        // Buscamos el cliente existente
        Colaborador colabor = colaboradorRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("EL colaborador no encontrado con id: " + id));
        if (colaborador.getNombreColaborador() != null) {
            if (colaborador.getNombreColaborador().trim().length() <= 10) {
                throw new RuntimeException("El nuevo nombre es muy corto, debe tener al menos 10 caracteres.");
            }

            colabor.setNombreColaborador(colaborador.getNombreColaborador());
        }
        if (colaborador.getRutColaborador() != null) {
            colabor.setRutColaborador(colaborador.getRutColaborador());
        }
        if (colaborador.getCorreo() != null) {
            colabor.setCorreo(colaborador.getCorreo());
        }
        if (colaborador.getTelefono() != null) {
            colabor.setTelefono(colaborador.getTelefono());
        }
        colaboradorRepository.save(colabor);
        ColaboradorDTO c = convertirADTO(colabor);
        return c;
    }

}