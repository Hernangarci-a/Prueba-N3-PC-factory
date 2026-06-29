package com.microservicio.servicio_usuarios.controller.v2;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.*;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.MediaTypes;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.microservicio.servicio_usuarios.assemblers.ComunaModelAssembler;
import com.microservicio.servicio_usuarios.dto.ComunaDTO;
import com.microservicio.servicio_usuarios.services.ComunaService;

@RestController
@RequestMapping("api/v2/pcfactory/comunas")
public class ComunaControllerV2 {
    @Autowired
    private ComunaService comunaService;

    @Autowired
    private ComunaModelAssembler assembler;

    @GetMapping(produces = MediaTypes.HAL_JSON_VALUE)
    public ResponseEntity<CollectionModel<EntityModel<ComunaDTO>>> getAllComunas() {
        List<EntityModel<ComunaDTO>> comunas = comunaService.obtenerTodos().stream()
                .map(assembler::toModel)
                .collect(Collectors.toList());

        if (comunas.isEmpty()) {
            return ResponseEntity.noContent().build();
        }

        return ResponseEntity.ok(CollectionModel.of(
                comunas,
                linkTo(methodOn(ComunaControllerV2.class).getAllComunas()).withSelfRel()));
    }

    @GetMapping(value = "/{codigo}", produces = MediaTypes.HAL_JSON_VALUE)
    public ResponseEntity<EntityModel<ComunaDTO>> getComunaByCodigo(@PathVariable Integer codigo) {
        ComunaDTO comuna = comunaService.buscarPorId(codigo);
        if (comuna == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(assembler.toModel(comuna));
    }

    @PostMapping(produces = MediaTypes.HAL_JSON_VALUE)
    public ResponseEntity<EntityModel<ComunaDTO>> addComuna(@RequestBody ComunaDTO comuna) {
        ComunaDTO comunaNueva = comunaService.guardarComuna(comuna);
        return ResponseEntity
                .created(linkTo(methodOn(ComunaControllerV2.class).getComunaByCodigo(comunaNueva.getIdComuna()))
                        .toUri())
                .body(assembler.toModel(comunaNueva));
    }

    @DeleteMapping(value = "/{codigo}", produces = MediaTypes.HAL_JSON_VALUE)
    public ResponseEntity<Void> deleteComuna(@PathVariable Integer codigo) {
        ComunaDTO comuna = comunaService.buscarPorId(codigo);
        if (comuna == null) {
            return ResponseEntity.notFound().build();
        }
        comunaService.eliminarComuna(codigo);
        return ResponseEntity.noContent().build();
    }

}
