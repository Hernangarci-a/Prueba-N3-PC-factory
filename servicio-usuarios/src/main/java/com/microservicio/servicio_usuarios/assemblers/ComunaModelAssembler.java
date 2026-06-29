package com.microservicio.servicio_usuarios.assemblers;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.*;

import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.server.RepresentationModelAssembler;
import org.springframework.stereotype.Component;

import com.microservicio.servicio_usuarios.controller.v2.ComunaControllerV2;
import com.microservicio.servicio_usuarios.dto.ComunaDTO;

@Component
public class ComunaModelAssembler implements RepresentationModelAssembler<ComunaDTO, EntityModel<ComunaDTO>> {

        @SuppressWarnings("null")
        @Override
        public EntityModel<ComunaDTO> toModel(ComunaDTO comuna) {
                return EntityModel.of(comuna,
                                linkTo(methodOn(ComunaControllerV2.class).getAllComunas())
                                                .withRel("Comunas"),
                                linkTo(methodOn(ComunaControllerV2.class)
                                                .getComunaByCodigo(comuna.getIdComuna())).withSelfRel(),
                                linkTo(methodOn(ComunaControllerV2.class).addComuna(comuna)).withRel("Agregar"),
                                linkTo(methodOn(ComunaControllerV2.class)
                                                .deleteComuna(comuna.getIdComuna())).withRel("Borrar"));
        }
}
