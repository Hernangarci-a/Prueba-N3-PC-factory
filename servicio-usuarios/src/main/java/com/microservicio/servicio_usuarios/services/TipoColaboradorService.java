package com.microservicio.servicio_usuarios.services;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.microservicio.servicio_usuarios.dto.TipoColaboradorDTO;
import com.microservicio.servicio_usuarios.model.Colaborador;
import com.microservicio.servicio_usuarios.model.TipoColaborador;
import com.microservicio.servicio_usuarios.repository.ColaboradorRepository;
import com.microservicio.servicio_usuarios.repository.TipoColaboradorRepository;

import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;

@Service
@Transactional
public class TipoColaboradorService {
    @Autowired
    private TipoColaboradorRepository tipoColaboradorRepository;

    @Autowired
    private ColaboradorRepository colaboradorRepository;

    public List<TipoColaboradorDTO> obtenerTodos() {
        return tipoColaboradorRepository.findAll().stream()
                .map(this::convertirADTO)
                .toList();
    }

    public TipoColaboradorDTO guardarTipoColaborador(TipoColaboradorDTO tipocolaborador) {
        if (tipocolaborador.getNombre() == null || tipocolaborador.getNombre().trim().isEmpty()) {
            throw new RuntimeException("Error el nombre no puede estar vacio");
        }
        TipoColaborador t = convertirTipoColaborador(tipocolaborador);
        TipoColaborador guardado = tipoColaboradorRepository.save(t);
        return convertirADTO(guardado);
    }

    public TipoColaboradorDTO buscarPorId(Integer id) {
        TipoColaborador tipo = tipoColaboradorRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("no se encontro con ID: " + id));
        return convertirADTO(tipo);
    }

    private TipoColaboradorDTO convertirADTO(TipoColaborador tipo) {
        TipoColaboradorDTO dto = new TipoColaboradorDTO();
        dto.setIdTipoColaborador(tipo.getIdTipoColaborador());
        dto.setNombre(tipo.getNombre());

        if (tipo.getColaboradores() != null) {
            dto.setNombresColaboradores(tipo.getColaboradores().stream()
                    .map(Colaborador::getNombreColaborador)
                    .toList());
        } else {
            dto.setNombresColaboradores(new ArrayList<>());
        }
        return dto;
    }

    public TipoColaboradorDTO actualizarTipoColaborador(Integer id, TipoColaboradorDTO tipoColaborador) {
        TipoColaborador tipoColabo = tipoColaboradorRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("El producto no existe en el catalogo"));
        if (tipoColaborador.getNombre() != null) {
            if (tipoColaborador.getNombre().trim().isEmpty()) {
                throw new RuntimeException("El nuevo nombre no puede estar vacio");
            }
            tipoColabo.setNombre(tipoColaborador.getNombre());
        }
        TipoColaborador guardado = tipoColaboradorRepository.save(tipoColabo);
        return convertirADTO(guardado);
    }

    public String eliminarTipoColaborador(Integer id) {
        // se usa try porque borrar algo es una proceso delicado puede fallar
        try {
            TipoColaborador tipoColaborador = tipoColaboradorRepository.findById(id).orElseThrow(
                    () -> new RuntimeException("¡Imposible eliminar! El producto con ID " + id + " no existe."));
            tipoColaboradorRepository.delete(tipoColaborador);
            return "El tipo colaborador a sido eliminado";

        } catch (RuntimeException e) {
            return e.getMessage();
        }
    }

    public TipoColaborador convertirTipoColaborador(TipoColaboradorDTO dto) {
        TipoColaborador tipo = new TipoColaborador();

        tipo.setIdTipoColaborador(dto.getIdTipoColaborador());
        tipo.setNombre(dto.getNombre());

        List<Colaborador> colaboradores = new ArrayList<>();
        for (String nombre : dto.getNombresColaboradores()) {
            for (Colaborador c : colaboradorRepository.findAll()) {
                if (c.getNombreColaborador().equals(nombre)) {
                    colaboradores.add(c);
                    break;
                }
            }
        }
        tipo.setColaboradores(colaboradores);

        return tipo;
    }

}