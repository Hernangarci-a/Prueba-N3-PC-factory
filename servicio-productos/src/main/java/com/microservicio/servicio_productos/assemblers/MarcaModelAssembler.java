package com.microservicio.servicio_productos.assemblers;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.server.RepresentationModelAssembler;
import org.springframework.stereotype.Component;
import com.microservicio.servicio_productos.controller.V2.MarcaControllerV2;
import com.microservicio.servicio_productos.dto.MarcaDTO;

@Component
public class MarcaModelAssembler implements RepresentationModelAssembler<MarcaDTO, EntityModel<MarcaDTO>> {

    @Override
    public EntityModel<MarcaDTO> toModel(MarcaDTO marcadto) {
        return EntityModel.of(marcadto,
                linkTo(methodOn(MarcaControllerV2.class)
                        .getMarcaByCodigo(marcadto.getIdMarca())).withSelfRel(),

                linkTo(methodOn(MarcaControllerV2.class)
                        .getAllMarcas()).withRel("marcas"),

                linkTo(methodOn(MarcaControllerV2.class)
                        .guardarMarcaDTO(marcadto)).withRel("guardar"),

                linkTo(methodOn(MarcaControllerV2.class)
                        .deleteMarca(marcadto.getIdMarca())).withRel("eliminar"));
    }
}
