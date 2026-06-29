package com.microservicio.servicio_usuarios.assemblers;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.*;

import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.server.RepresentationModelAssembler;
import org.springframework.stereotype.Component;

import com.microservicio.servicio_usuarios.controller.v2.RegionControllerV2;
import com.microservicio.servicio_usuarios.dto.RegionDTO;

@Component
public class RegionModelAssembler implements RepresentationModelAssembler<RegionDTO, EntityModel<RegionDTO>> {
        @SuppressWarnings("null")
        @Override
        public EntityModel<RegionDTO> toModel(RegionDTO region) {
                return EntityModel.of(region,
                                linkTo(methodOn(RegionControllerV2.class).getAllRegiones())
                                                .withRel("Regiones"),
                                linkTo(methodOn(RegionControllerV2.class)
                                                .getRegionByCodigo(region.getIdRegion())).withSelfRel(),
                                linkTo(methodOn(RegionControllerV2.class).addRegion(region)).withRel("Agregar"),
                                linkTo(methodOn(RegionControllerV2.class)
                                                .deleteRegion(region.getIdRegion())).withRel("Borrar"));
        }
}
