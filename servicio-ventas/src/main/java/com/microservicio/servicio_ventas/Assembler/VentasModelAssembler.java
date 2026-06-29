package com.microservicio.servicio_ventas.Assembler;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.server.RepresentationModelAssembler;
import org.springframework.stereotype.Component;

import com.microservicio.servicio_ventas.controller.VentaControllerV2;
import com.microservicio.servicio_ventas.dto.VentasDTO;

@Component
public class VentasModelAssembler
        implements RepresentationModelAssembler<VentasDTO, EntityModel<VentasDTO>> {

    @Override
    public EntityModel<VentasDTO> toModel(VentasDTO venta) {
        return EntityModel.of(venta,
                linkTo(methodOn(VentaControllerV2.class).porId(venta.getIdVenta())).withSelfRel(),
                linkTo(methodOn(VentaControllerV2.class).todas()).withRel("ventas"));
    }
}