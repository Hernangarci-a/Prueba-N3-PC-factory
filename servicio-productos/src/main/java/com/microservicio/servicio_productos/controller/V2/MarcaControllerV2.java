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

import com.microservicio.servicio_productos.assemblers.MarcaModelAssembler;
import com.microservicio.servicio_productos.dto.MarcaDTO;
import com.microservicio.servicio_productos.model.Marca;
import com.microservicio.servicio_productos.services.MarcaService;

@RestController
@RequestMapping("/api/v2/marca")
public class MarcaControllerV2 {

    @Autowired
    private MarcaService marcaService;

    @Autowired
    private MarcaModelAssembler assembler;

    @GetMapping(produces = MediaTypes.HAL_JSON_VALUE)
    public CollectionModel<EntityModel<MarcaDTO>> getAllMarcas() {
        List<EntityModel<MarcaDTO>> marcas = marcaService.obtenerTodos().stream()
                .map(assembler::toModel)
                .collect(Collectors.toList());

        return CollectionModel.of(marcas,
                linkTo(methodOn(MarcaControllerV2.class).getAllMarcas()).withSelfRel());
    }

    @GetMapping(value = "/{id}", produces = MediaTypes.HAL_JSON_VALUE)
    public ResponseEntity<EntityModel<MarcaDTO>> getMarcaByCodigo(@PathVariable Integer id) {
        MarcaDTO marcaDto = marcaService.buscarPorId(id);
        if (marcaDto == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(assembler.toModel(marcaDto));
    }

    @PostMapping(produces = MediaTypes.HAL_JSON_VALUE)
    public ResponseEntity<EntityModel<MarcaDTO>> guardarMarcaDTO(@RequestBody MarcaDTO dto) {
        Marca entidad = new Marca();
        entidad.setIdMarca(dto.getIdMarca());

        Marca guardada = marcaService.guardarMarca(entidad);

        MarcaDTO nuevaMarcaDTO = new MarcaDTO();
        nuevaMarcaDTO.setIdMarca(guardada.getIdMarca());

        return ResponseEntity
                .created(linkTo(methodOn(MarcaControllerV2.class)
                        .getMarcaByCodigo(nuevaMarcaDTO.getIdMarca())).toUri())
                .body(assembler.toModel(nuevaMarcaDTO));
    }

    @DeleteMapping(value = "/{id}", produces = MediaTypes.HAL_JSON_VALUE)
    public ResponseEntity<Void> deleteMarca(@PathVariable Integer id) {
        MarcaDTO marcaDto = marcaService.buscarPorId(id);
        if (marcaDto == null) {
            return ResponseEntity.notFound().build();
        }
        marcaService.eliminarMarca(id);
        return ResponseEntity.noContent().build();
    }
}
