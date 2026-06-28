package com.microservicio.servicio_productos.controller.V2;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.MediaTypes;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.microservicio.servicio_productos.assemblers.ProductosModelAssemblers;
import com.microservicio.servicio_productos.dto.ProductosDTO;
import com.microservicio.servicio_productos.model.Productos;
import com.microservicio.servicio_productos.services.ProductosService;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@RestController
@RequestMapping("/api/v2/productos")
public class ProductosControllerV2 {

    @Autowired
    private ProductosService productosService;

    @Autowired
    private ProductosModelAssemblers assembler;

    @GetMapping(produces = MediaTypes.HAL_JSON_VALUE)
    public ResponseEntity<CollectionModel<EntityModel<ProductosDTO>>> getAllProductos() {
        List<EntityModel<ProductosDTO>> productosdto = productosService.obtenerTodos().stream()
                .map(assembler::toModel)
                .collect(Collectors.toList());

        if (productosdto == null) {
            return ResponseEntity.notFound().build();
        }

        return ResponseEntity.ok(CollectionModel.of(
                productosdto,
                linkTo(methodOn(ProductosControllerV2.class).getAllProductos()).withSelfRel()));
    }

    @GetMapping(value = "/{codigo}", produces = MediaTypes.HAL_JSON_VALUE)
    public ResponseEntity<EntityModel<ProductosDTO>> getProductosByCodigo(@PathVariable Integer id) {
        ProductosDTO productosdto = productosService.buscarPorId(id);
        if (productosdto == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(assembler.toModel(productosdto));
    }

    @PostMapping(produces = MediaTypes.HAL_JSON_VALUE)
    public ResponseEntity<EntityModel<ProductosDTO>> guardarProductoDTO(@RequestBody ProductosDTO productosdto) {

        // Creamos la Entidad que la V1 del servicio necesita
        Productos entidad = new Productos();
        entidad.setIdProductos(productosdto.getIdProducto());
        // Nota: Si el servicio de la V1 te pide guardar más campos (nombre, precio,
        // etc.), los seteas aquí:
        // entidad.setNombreProducto(productosdto.getNombreProducto());

        // Guardamos la entidad usando el método existente de tu servicio
        Productos productoGuardado = productosService.guardarProductos(entidad);

        // Convertimos la entidad guardada de vuelta a DTO para HATEOAS
        ProductosDTO nuevoProductoDto = new ProductosDTO();
        nuevoProductoDto.setIdProducto(productoGuardado.getIdProductos());
        // nuevoProductoDto.setNombreProducto(productoGuardado.getNombreProducto());

        return ResponseEntity
                .created(linkTo(methodOn(ProductosControllerV2.class)
                        .getProductosByCodigo(Integer.valueOf(nuevoProductoDto.getIdProducto()))).toUri())
                .body(assembler.toModel(nuevoProductoDto));
    }

    @PutMapping(value = "/{codigo}", produces = MediaTypes.HAL_JSON_VALUE)
    public ResponseEntity<EntityModel<ProductosDTO>> updateProductos(
            @PathVariable Integer codigo, // Cambiado a Integer para que coincida con los id
            @RequestBody ProductosDTO productosdto) {

        // Creamos la Entidad que la V1 del servicio necesita
        Productos entidad = new Productos();
        entidad.setIdProductos(codigo);

        Productos productoActualizado = productosService.actualizarProductos(codigo, entidad);

        if (productoActualizado == null) {
            return ResponseEntity.notFound().build();
        }

        // Convertimos la entidad devuelta de nuevo a DTO para dársela al assembler
        ProductosDTO updatedProductosDTO = new ProductosDTO();
        updatedProductosDTO.setIdProducto(productoActualizado.getIdProductos());
        // updatedProductosDTO.setNombreProducto(productoActualizado.getNombreProducto());

        return ResponseEntity.ok(assembler.toModel(updatedProductosDTO));
    }

    @PatchMapping(value = "/{codigo}", produces = MediaTypes.HAL_JSON_VALUE)
    public ResponseEntity<EntityModel<ProductosDTO>> patchProdcutos(
            @PathVariable Integer codigo, // Cambiado a Integer para que coincida con tus IDs
            @RequestBody ProductosDTO productosdto) {

        // Creamos la Entidad que la V1 del servicio necesita
        Productos entidad = new Productos();
        entidad.setIdProductos(codigo);

        // Llamamos al servicio de la V1 pasando la entidad devuelve un objeto
        // Productos
        Productos productoActualizado = productosService.actualizarProductos(codigo, entidad);

        if (productoActualizado == null) {
            return ResponseEntity.notFound().build();
        }

        // Convertimos la entidad devuelta de nuevo a DTO para dársela al assembler
        ProductosDTO updatedProductosDTO = new ProductosDTO();
        updatedProductosDTO.setIdProducto(productoActualizado.getIdProductos());
        // updatedProductosDTO.setNombreProducto(productoActualizado.getNombreProducto());

        return ResponseEntity.ok(assembler.toModel(updatedProductosDTO));
    }

    @DeleteMapping(value = "/{id}", produces = MediaTypes.HAL_JSON_VALUE)
    public ResponseEntity<Void> deleteProductos(@PathVariable Integer id) {
        ProductosDTO existing = productosService.buscarPorId(id);
        if (existing == null) {
            return ResponseEntity.notFound().build();
        }
        productosService.eliminarProductos(id);
        return ResponseEntity.noContent().build();
    }
}
