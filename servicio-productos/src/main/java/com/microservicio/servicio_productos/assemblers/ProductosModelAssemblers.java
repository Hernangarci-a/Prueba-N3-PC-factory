package com.microservicio.servicio_productos.assemblers;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.server.RepresentationModelAssembler;
import org.springframework.stereotype.Component;

import com.microservicio.servicio_productos.controller.V2.ProductosControllerV2;
import com.microservicio.servicio_productos.dto.ProductosDTO;

@Component
public class ProductosModelAssemblers implements RepresentationModelAssembler<ProductosDTO, EntityModel<ProductosDTO>> {

    @SuppressWarnings("null")
    @Override
    public EntityModel<ProductosDTO> toModel(ProductosDTO productosdto) {
        return EntityModel.of(productosdto,
                linkTo(methodOn(ProductosControllerV2.class)
                        .getProductosByCodigo(Integer.valueOf(productosdto.getIdProducto())))
                        .withSelfRel(),
                linkTo(methodOn(ProductosControllerV2.class).getAllProductos()).withRel("productos"),
                linkTo(methodOn(ProductosControllerV2.class).guardarProductoDTO(productosdto))
                        .withRel("guardar"),
                linkTo(methodOn(ProductosControllerV2.class).updateProductos(
                        productosdto.getIdProducto(),
                        productosdto)).withRel("actualizar"),
                linkTo(methodOn(ProductosControllerV2.class)
                        .deleteProductos(productosdto.getIdProducto()))
                        .withRel("eliminar"),
                linkTo(methodOn(ProductosControllerV2.class)
                        .patchProdcutos(productosdto.getIdProducto(), productosdto))
                        .withRel("actualizar-parcial"));
    }
}
