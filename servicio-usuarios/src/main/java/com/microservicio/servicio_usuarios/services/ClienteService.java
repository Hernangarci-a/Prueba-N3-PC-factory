package com.microservicio.servicio_usuarios.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.microservicio.servicio_usuarios.dto.ClienteDTO;
import com.microservicio.servicio_usuarios.model.Cliente;
import com.microservicio.servicio_usuarios.model.Comuna;
import com.microservicio.servicio_usuarios.repository.ClienteRepository;
import com.microservicio.servicio_usuarios.repository.ComunaRepository;

import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;

@Service
@Transactional
public class ClienteService {
    @Autowired
    private ClienteRepository clienteRepository;

    @Autowired
    private ComunaRepository comunaRepository;

    public List<ClienteDTO> obtenerTodos() {
        return clienteRepository.findAll().stream()
                .map(this::convertirADTO)
                .toList();
    }

    public ClienteDTO buscarPorId(Integer id) {
        Cliente cliente = clienteRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("no se econtro cliente con ID: " + id));
        return convertirADTO(cliente);
    }

    public ClienteDTO guardarCliente(ClienteDTO clientedto) {
        Cliente cliente = convertirCliente(clientedto);
        if (cliente.getNombreCliente() == null || cliente.getApellidoCliente().trim().isEmpty()) {
            throw new RuntimeException("el nombre no debe estar vacio");
        }
        Cliente guardado = clienteRepository.save(cliente);
        return convertirADTO(guardado);
    }

    public String eliminarCliente(Integer id) {
        try {
            clienteRepository.deleteById(id);
            return "El cliente se elimino";
        } catch (RuntimeException e) {
            return e.getMessage();
        }
    }

    public ClienteDTO convertirADTO(Cliente cliente) {
        ClienteDTO dto = new ClienteDTO();
        dto.setIdCliente(cliente.getIdCliente());
        dto.setPrimerNombre(cliente.getNombreCliente());
        dto.setApellidoCliente(cliente.getApellidoCliente());
        dto.setCorreo(cliente.getCorreo());
        dto.setDireccion(cliente.getDireccion());
        dto.setTelefono(cliente.getTelefono());
        dto.setComuna(cliente.getComuna().getNombre_comuna());
        return dto;
    }

    public Cliente convertirCliente(ClienteDTO dto) {
        Cliente cliente = new Cliente();

        cliente.setIdCliente(dto.getIdCliente());
        cliente.setNombreCliente(dto.getPrimerNombre());
        cliente.setApellidoCliente(dto.getApellidoCliente());
        cliente.setCorreo(dto.getCorreo());
        cliente.setDireccion(dto.getDireccion());
        cliente.setTelefono(dto.getTelefono());
        for (Comuna comuna : comunaRepository.findAll()) {
            if (comuna.getNombre_comuna().equals(dto.getComuna())) {
                cliente.setComuna(comuna);
                break;
            }
        }
        return cliente;

    }

    public ClienteDTO actualizarCliente(Integer id, Cliente c) {
        // 1. Buscamos el cliente existente
        Cliente cliente = clienteRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Cliente no encontrado con id: " + id));

        // 2. Actualizamos los campos (Ajusta los nombres según tus atributos de la
        // clase Cliente)
        if (c.getNombreCliente() != null) {
            cliente.setNombreCliente(c.getNombreCliente());
        }

        if (c.getApellidoCliente() != null) {
            cliente.setApellidoCliente(c.getApellidoCliente());
        }

        if (c.getCorreo() != null) {
            cliente.setCorreo(c.getCorreo());
        }

        if (c.getDireccion() != null) {
            cliente.setDireccion(c.getDireccion());
        }
        if (c.getTelefono() != null) {
            cliente.setTelefono(c.getTelefono());
        }

        Cliente act = clienteRepository.save(cliente);

        return convertirADTO(act);
    }

}