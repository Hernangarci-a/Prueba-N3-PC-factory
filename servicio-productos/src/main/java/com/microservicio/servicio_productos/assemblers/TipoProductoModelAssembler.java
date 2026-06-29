package com.microservicio.servicio_productos.assemblers;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.server.RepresentationModelAssembler;
import org.springframework.stereotype.Component;
import com.microservicio.servicio_productos.controller.V2.TipoProductoControllerV2;
import com.microservicio.servicio_productos.dto.TipoProductoDTO;

@Component
public class TipoProductoModelAssembler
        implements RepresentationModelAssembler<TipoProductoDTO, EntityModel<TipoProductoDTO>> {

    @Override
    public EntityModel<TipoProductoDTO> toModel(TipoProductoDTO tipoProductodto) {
        return EntityModel.of(tipoProductodto,
                linkTo(methodOn(TipoProductoControllerV2.class)
                        .getTipoProductoByCodigo(tipoProductodto.getId())).withSelfRel(),

                linkTo(methodOn(TipoProductoControllerV2.class)
                        .getAllTipoProductos()).withRel("tipoProductos"),

                linkTo(methodOn(TipoProductoControllerV2.class)
                        .guardarTipoProductoDTO(tipoProductodto)).withRel("guardar"),

                linkTo(methodOn(TipoProductoControllerV2.class)
                        .deleteTipoProducto(tipoProductodto.getId())).withRel("eliminar"));
    }
}
