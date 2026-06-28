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

import com.microservicio.servicio_productos.assemblers.CategoriaModelAssembler;
import com.microservicio.servicio_productos.dto.CategoriaDTO;
import com.microservicio.servicio_productos.model.Categoria;
import com.microservicio.servicio_productos.services.CategoriaService;

@RestController
@RequestMapping("/api/v2/categoria")
public class CategoriaControllerV2 {

    @Autowired
    private CategoriaService categoriaService;

    @Autowired
    private CategoriaModelAssembler assembler;

    @GetMapping(produces = MediaTypes.HAL_JSON_VALUE)
    public CollectionModel<EntityModel<CategoriaDTO>> getAllCategorias() {
        List<EntityModel<CategoriaDTO>> categoriadto = categoriaService.obtenerTodos().stream()
                .map(assembler::toModel)
                .collect(Collectors.toList());

        return CollectionModel.of(categoriadto,
                linkTo(methodOn(CategoriaControllerV2.class).getAllCategorias()).withSelfRel());
    }

    @GetMapping(value = "/{id}", produces = MediaTypes.HAL_JSON_VALUE)
    public ResponseEntity<EntityModel<CategoriaDTO>> getCategoriaByCodigo(@PathVariable Integer id) {
        CategoriaDTO categoriadto = categoriaService.buscarPorId(id);
        if (categoriadto == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(assembler.toModel(categoriadto));
    }

    @PostMapping(produces = MediaTypes.HAL_JSON_VALUE)
    public ResponseEntity<EntityModel<CategoriaDTO>> guardarCategoriaDTO(@RequestBody CategoriaDTO dto) {

        // Convertimos DTO a Entidad pura para la V1
        Categoria entidad = new Categoria();
        entidad.setIdCategoria(dto.getIdCategoria());

        // Guardamos usando el servicio existente
        Categoria guardada = categoriaService.guardarCategoria(entidad);

        // Convertimos la entidad guardada de vuelta a DTO
        CategoriaDTO nuevoDto = new CategoriaDTO();
        nuevoDto.setIdCategoria(guardada.getIdCategoria());

        return ResponseEntity
                .created(linkTo(methodOn(CategoriaControllerV2.class)
                        .getCategoriaByCodigo(nuevoDto.getIdCategoria())).toUri())
                .body(assembler.toModel(nuevoDto));
    }

    @DeleteMapping(value = "/{id}", produces = MediaTypes.HAL_JSON_VALUE)
    public ResponseEntity<Void> deleteCategoria(@PathVariable Integer id) {
        CategoriaDTO Categoriadto = categoriaService.buscarPorId(id);
        if (Categoriadto == null) {
            return ResponseEntity.notFound().build();
        }
        categoriaService.eliminarCategoria(id);
        return ResponseEntity.noContent().build();
    }
}
