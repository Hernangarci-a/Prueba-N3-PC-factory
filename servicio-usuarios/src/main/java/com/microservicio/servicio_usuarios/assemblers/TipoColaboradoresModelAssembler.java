package com.microservicio.servicio_usuarios.assemblers;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.*;

import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.server.RepresentationModelAssembler;
import org.springframework.stereotype.Component;

import com.microservicio.servicio_usuarios.controller.v2.TipoColaboradorControllerV2;
import com.microservicio.servicio_usuarios.dto.TipoColaboradorDTO;

@Component
public class TipoColaboradoresModelAssembler
                implements RepresentationModelAssembler<TipoColaboradorDTO, EntityModel<TipoColaboradorDTO>> {
        @SuppressWarnings("null")
        @Override
        public EntityModel<TipoColaboradorDTO> toModel(TipoColaboradorDTO tipo) {
                return EntityModel.of(tipo,
                                linkTo(methodOn(TipoColaboradorControllerV2.class).updateTipoColaborador(
                                                tipo.getIdTipoColaborador(),
                                                tipo)).withRel("Actualizar"),
                                linkTo(methodOn(TipoColaboradorControllerV2.class).getAllTipoColaboradores())
                                                .withRel("Tipo de colaboradores"),
                                linkTo(methodOn(TipoColaboradorControllerV2.class)
                                                .getTipoColaboradorByCodigo(tipo.getIdTipoColaborador())).withSelfRel(),
                                linkTo(methodOn(TipoColaboradorControllerV2.class).addTipoColaborador(tipo))
                                                .withRel("Agregar"),
                                linkTo(methodOn(TipoColaboradorControllerV2.class)
                                                .deleteTipoColaborador(tipo.getIdTipoColaborador())).withRel("Borrar"));
        }
}
