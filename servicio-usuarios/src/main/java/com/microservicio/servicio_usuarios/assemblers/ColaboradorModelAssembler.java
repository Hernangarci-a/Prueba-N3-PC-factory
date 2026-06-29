package com.microservicio.servicio_usuarios.assemblers;

import com.microservicio.servicio_usuarios.controller.v2.ColaboradorControllerV2;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.server.RepresentationModelAssembler;
import org.springframework.stereotype.Component;

import com.microservicio.servicio_usuarios.dto.ColaboradorDTO;

@Component
public class ColaboradorModelAssembler
                implements RepresentationModelAssembler<ColaboradorDTO, EntityModel<ColaboradorDTO>> {

        @SuppressWarnings("null")
        @Override
        public EntityModel<ColaboradorDTO> toModel(ColaboradorDTO colaborador) {
                return EntityModel.of(colaborador,
                                linkTo(methodOn(ColaboradorControllerV2.class).getAllColaboradores())
                                                .withRel("Colaboladores"),
                                linkTo(methodOn(ColaboradorControllerV2.class)
                                                .getColaboradorByCodigo(colaborador.getIdColaborador())).withSelfRel(),
                                linkTo(methodOn(ColaboradorControllerV2.class)
                                                .updateColaborador(colaborador.getIdColaborador(), colaborador))
                                                .withRel("Actualizar"),
                                linkTo(methodOn(ColaboradorControllerV2.class)
                                                .deleteColaborador(colaborador.getIdColaborador())).withRel("Borrar"));
        }
}