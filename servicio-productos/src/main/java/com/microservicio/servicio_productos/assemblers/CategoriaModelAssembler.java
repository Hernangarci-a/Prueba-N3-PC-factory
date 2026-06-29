package com.microservicio.servicio_productos.assemblers;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.server.RepresentationModelAssembler;
import org.springframework.stereotype.Component;
import com.microservicio.servicio_productos.controller.V2.CategoriaControllerV2;
import com.microservicio.servicio_productos.dto.CategoriaDTO;

@Component
public class CategoriaModelAssembler implements RepresentationModelAssembler<CategoriaDTO, EntityModel<CategoriaDTO>> {

    @Override
    public EntityModel<CategoriaDTO> toModel(CategoriaDTO categoriadto) {
        return EntityModel.of(categoriadto,
                linkTo(methodOn(CategoriaControllerV2.class)
                        .getCategoriaByCodigo(categoriadto.getIdCategoria())).withSelfRel(),

                linkTo(methodOn(CategoriaControllerV2.class)
                        .getAllCategorias()).withRel("categorias"),

                linkTo(methodOn(CategoriaControllerV2.class)
                        .guardarCategoriaDTO(categoriadto)).withRel("guardar"),

                linkTo(methodOn(CategoriaControllerV2.class)
                        .deleteCategoria(categoriadto.getIdCategoria())).withRel("eliminar"));
    }
}
