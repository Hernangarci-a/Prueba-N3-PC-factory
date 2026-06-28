package com.microservicio.servicio_productos.controller.V2;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.MediaTypes;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.microservicio.servicio_productos.assemblers.TipoProductoModelAssembler;
import com.microservicio.servicio_productos.dto.TipoProductoDTO;
import com.microservicio.servicio_productos.model.TipoProducto;
import com.microservicio.servicio_productos.services.TipoProductoService;

@RestController
@RequestMapping("/api/v2/tipoproducto")
public class TipoProductoControllerV2 {

    @Autowired
    private TipoProductoService tipoProductoService;

    @Autowired
    private TipoProductoModelAssembler assembler;

    @GetMapping(produces = MediaTypes.HAL_JSON_VALUE)
    public CollectionModel<EntityModel<TipoProductoDTO>> getAllTipoProductos() {
        List<EntityModel<TipoProductoDTO>> tipoProducto = tipoProductoService.obtenerTodos().stream()
                .map(assembler::toModel)
                .collect(Collectors.toList());

        return CollectionModel.of(tipoProducto,
                linkTo(methodOn(TipoProductoControllerV2.class).getAllTipoProductos()).withSelfRel());
    }

    @GetMapping(value = "/{id}", produces = MediaTypes.HAL_JSON_VALUE)
    public ResponseEntity<EntityModel<TipoProductoDTO>> getTipoProductoByCodigo(@PathVariable Integer id) {
        TipoProductoDTO tipoProducto = tipoProductoService.buscarPorId(id);
        if (tipoProducto == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(assembler.toModel(tipoProducto));
    }

    @PostMapping(produces = MediaTypes.HAL_JSON_VALUE)
    public ResponseEntity<EntityModel<TipoProductoDTO>> guardarTipoProductoDTO(@RequestBody TipoProductoDTO dto) {
        TipoProducto entidad = new TipoProducto();
        entidad.setIdTipoProducto(dto.getId());

        TipoProducto guardado = tipoProductoService.guardarTipoProducto(entidad);

        TipoProductoDTO nuevoTipoProductoDTO = new TipoProductoDTO();
        nuevoTipoProductoDTO.setId(guardado.getIdTipoProducto());

        return ResponseEntity
                .created(linkTo(methodOn(TipoProductoControllerV2.class)
                        .getTipoProductoByCodigo(nuevoTipoProductoDTO.getId())).toUri())
                .body(assembler.toModel(nuevoTipoProductoDTO));
    }

    @DeleteMapping(value = "/{id}", produces = MediaTypes.HAL_JSON_VALUE)
    public ResponseEntity<Void> deleteTipoProducto(@PathVariable Integer id) {
        TipoProductoDTO tipoProductoDto = tipoProductoService.buscarPorId(id);
        if (tipoProductoDto == null) {
            return ResponseEntity.notFound().build();
        }
        tipoProductoService.eliminarTipoProdcucto(id);
        return ResponseEntity.noContent().build();
    }
}
