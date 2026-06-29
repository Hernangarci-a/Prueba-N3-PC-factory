package com.microservicio.servicio_usuarios.assemblers;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.server.RepresentationModelAssembler;
import org.springframework.stereotype.Component;

import com.microservicio.servicio_usuarios.controller.v2.ClienteControllerV2;
import com.microservicio.servicio_usuarios.dto.ClienteDTO;

@Component
public class ClienteModelAssembler implements RepresentationModelAssembler<ClienteDTO, EntityModel<ClienteDTO>> {

    @SuppressWarnings("null")
    @Override
    public EntityModel<ClienteDTO> toModel(ClienteDTO cliente) {
        return EntityModel.of(cliente,
                linkTo(methodOn(ClienteControllerV2.class).getAllClientes()).withRel("clientes"),
                linkTo(methodOn(ClienteControllerV2.class).getClienteByCodigo(cliente.getIdCliente())).withSelfRel(),
                linkTo(methodOn(ClienteControllerV2.class).updateCliente(cliente.getIdCliente(), cliente))
                        .withRel("Actualizar"),
                linkTo(methodOn(ClienteControllerV2.class).addCliente(cliente)).withRel("Añadir"),
                linkTo(methodOn(ClienteControllerV2.class).deleteCliente(cliente.getIdCliente())).withRel("Borrar"));
    }
}