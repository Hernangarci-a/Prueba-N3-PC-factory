package com.microservicio.servicio_ventas.Assembler;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.server.RepresentationModelAssembler;
import org.springframework.stereotype.Component;

import com.microservicio.servicio_ventas.controller.TipoDespachoControllerV2;
import com.microservicio.servicio_ventas.model.TipoDespacho;

@Component
public class TipoDespachoModelAssembler
        implements RepresentationModelAssembler<TipoDespacho, EntityModel<TipoDespacho>> {

    @Override
    public EntityModel<TipoDespacho> toModel(TipoDespacho tipoDespacho){
        return EntityModel.of(tipoDespacho,
                linkTo(methodOn(TipoDespachoControllerV2.class).porId(tipoDespacho.getIdTipoDespacho())).withSelfRel(),
                linkTo(methodOn(TipoDespachoControllerV2.class).getTipoDespacho()).withRel("tipodespacho"));
    }
}