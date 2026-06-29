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
import com.microservicio.servicio_productos.model.Marca;
import com.microservicio.servicio_productos.model.Productos;
import com.microservicio.servicio_productos.model.TipoProducto;
import com.microservicio.servicio_productos.repository.MarcaRepository;
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

    @GetMapping(value = "/{id}", produces = MediaTypes.HAL_JSON_VALUE)
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
        entidad.setNombreProducto(productosdto.getNombreProducto());
        entidad.setPrecioUnitario(productosdto.getPrecioUnitario());
        entidad.setProcesador(productosdto.getProcesador());
        entidad.setMemoriaRam(productosdto.getMemoriaRam());
        entidad.setAlmacenamiento(productosdto.getAlmacenamiento());

        // Guardamos la entidad usando el método existente de tu servicio
        Productos productoGuardado = productosService.guardarProductos(entidad);

        // Convertimos la entidad guardada de vuelta a DTO para HATEOAS
        ProductosDTO nuevoProductoDto = new ProductosDTO();
        nuevoProductoDto.setIdProducto(productoGuardado.getIdProductos());
        nuevoProductoDto.setNombreProducto(productoGuardado.getNombreProducto());
        nuevoProductoDto.setPrecioUnitario(productoGuardado.getPrecioUnitario());
        nuevoProductoDto.setProcesador(productoGuardado.getProcesador());
        nuevoProductoDto.setMemoriaRam(productoGuardado.getMemoriaRam());
        nuevoProductoDto.setAlmacenamiento(productoGuardado.getAlmacenamiento());

        return ResponseEntity
                .created(linkTo(methodOn(ProductosControllerV2.class)
                        .getProductosByCodigo(Integer.valueOf(nuevoProductoDto.getIdProducto()))).toUri())
                .body(assembler.toModel(nuevoProductoDto));
    }

    @PutMapping(value = "/{id}", produces = MediaTypes.HAL_JSON_VALUE)
    public ResponseEntity<EntityModel<ProductosDTO>> updateProductos(
            @PathVariable Integer id,
            @RequestBody ProductosDTO productosdto) {

        if (productosService.buscarPorId(id) == null) {
            return ResponseEntity.notFound().build();
        }

        Productos entidad = new Productos();

        // Le pasamos el ID para que podamos acttualizar el que corresponda a ese id
        entidad.setIdProductos(id);

        entidad.setNombreProducto(productosdto.getNombreProducto());
        entidad.setPrecioUnitario(productosdto.getPrecioUnitario());
        entidad.setProcesador(productosdto.getProcesador());
        entidad.setMemoriaRam(productosdto.getMemoriaRam());
        entidad.setAlmacenamiento(productosdto.getAlmacenamiento());

        // Guardamos la entidad actualizada usando tu servicio
        Productos productoGuardado = productosService.guardarProductos(entidad);

        // Convertimos la entidad guardada de vuelta a DTO para que no responda con
        // nulls
        ProductosDTO nuevoProductoDto = new ProductosDTO();
        nuevoProductoDto.setIdProducto(productoGuardado.getIdProductos());
        nuevoProductoDto.setNombreProducto(productoGuardado.getNombreProducto());
        nuevoProductoDto.setPrecioUnitario(productoGuardado.getPrecioUnitario());
        nuevoProductoDto.setProcesador(productoGuardado.getProcesador());
        nuevoProductoDto.setMemoriaRam(productoGuardado.getMemoriaRam());
        nuevoProductoDto.setAlmacenamiento(productoGuardado.getAlmacenamiento());

        if (productoGuardado.getMarca() != null) {
            nuevoProductoDto.setNombreMarca(productoGuardado.getMarca().getNombreMarca());
        }
        if (productoGuardado.getTipoProducto() != null) {
            nuevoProductoDto.setNombreTipoProducto(productoGuardado.getTipoProducto().getNombreTipoProducto());
        }

        // Devolvemos la respuesta con el assembler de HATEOAS (200 OK)
        return ResponseEntity.ok(assembler.toModel(nuevoProductoDto));
    }

    @PatchMapping(value = "/{id}", produces = MediaTypes.HAL_JSON_VALUE)
    public ResponseEntity<EntityModel<ProductosDTO>> patchProdcutos(
            @PathVariable Integer id,
            @RequestBody ProductosDTO dto) {

        // Buscar el producto existente en la base de datos
        // se usa el metodo para buscar id en el service de producto
        Productos productoExistente = productosService.buscarProducto(id);
        if (productoExistente == null) {
            return ResponseEntity.notFound().build();
        }

        if (dto.getNombreProducto() != null) {
            productoExistente.setNombreProducto(dto.getNombreProducto());
        }
        if (dto.getPrecioUnitario() != null) {
            productoExistente.setPrecioUnitario(dto.getPrecioUnitario());
        }
        if (dto.getProcesador() != null) {
            productoExistente.setProcesador(dto.getProcesador());
        }
        if (dto.getMemoriaRam() != null) {
            productoExistente.setMemoriaRam(dto.getMemoriaRam());
        }
        if (dto.getAlmacenamiento() != null) {
            productoExistente.setAlmacenamiento(dto.getAlmacenamiento());
        }

        // Guardar la entidad actualizada en la base de datos
        Productos productoGuardado = productosService.guardarProductos(productoExistente);

        // Convertir la entidad guardada de vuelta a DTO para la respuesta HATEOAS
        ProductosDTO nuevoProductoDto = new ProductosDTO();
        nuevoProductoDto.setIdProducto(productoGuardado.getIdProductos());
        nuevoProductoDto.setNombreProducto(productoGuardado.getNombreProducto());
        nuevoProductoDto.setPrecioUnitario(productoGuardado.getPrecioUnitario());
        nuevoProductoDto.setProcesador(productoGuardado.getProcesador());
        nuevoProductoDto.setMemoriaRam(productoGuardado.getMemoriaRam());
        nuevoProductoDto.setAlmacenamiento(productoGuardado.getAlmacenamiento());

        // Retornar la respuesta con el assembler de HATEOAS
        return ResponseEntity.ok(assembler.toModel(nuevoProductoDto));
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
