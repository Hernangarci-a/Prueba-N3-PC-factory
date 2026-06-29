package com.microservicio.servicio_usuarios.assemblers;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.*;

import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.server.RepresentationModelAssembler;
import org.springframework.stereotype.Component;

import com.microservicio.servicio_usuarios.controller.v2.SucursalControllerV2;

import com.microservicio.servicio_usuarios.dto.SucursalDTO;

@Component
public class SucursalModelAssembler implements RepresentationModelAssembler<SucursalDTO, EntityModel<SucursalDTO>> {
        @SuppressWarnings("null")
        @Override
        public EntityModel<SucursalDTO> toModel(SucursalDTO sucursal) {
                return EntityModel.of(sucursal,
                                linkTo(methodOn(SucursalControllerV2.class).getAllSucursales())
                                                .withRel("Regiones"),
                                linkTo(methodOn(SucursalControllerV2.class)
                                                .getSucursalByCodigo(sucursal.getIdSucursal())).withSelfRel(),
                                linkTo(methodOn(SucursalControllerV2.class).addSucursal(sucursal)).withRel("Agregar"),
                                linkTo(methodOn(SucursalControllerV2.class)
                                                .deleteSucursal(sucursal.getIdSucursal())).withRel("Borrar"));
        }
}
