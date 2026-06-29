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
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.microservicio.servicio_usuarios.assemblers.TipoColaboradoresModelAssembler;
import com.microservicio.servicio_usuarios.dto.TipoColaboradorDTO;
import com.microservicio.servicio_usuarios.services.TipoColaboradorService;

@RestController
@RequestMapping("api/v2/pcfactory/tipocolaboradores")
public class TipoColaboradorControllerV2 {
    @Autowired
    private TipoColaboradorService tipoColaboradorService;

    @Autowired
    private TipoColaboradoresModelAssembler assembler;

    @GetMapping(produces = MediaTypes.HAL_JSON_VALUE)
    public ResponseEntity<CollectionModel<EntityModel<TipoColaboradorDTO>>> getAllTipoColaboradores() {
        List<EntityModel<TipoColaboradorDTO>> tipoColaboradores = tipoColaboradorService.obtenerTodos().stream()
                .map(assembler::toModel)
                .collect(Collectors.toList());

        if (tipoColaboradores.isEmpty()) {
            return ResponseEntity.noContent().build();
        }

        return ResponseEntity.ok(CollectionModel.of(
                tipoColaboradores,
                linkTo(methodOn(TipoColaboradorControllerV2.class).getAllTipoColaboradores()).withSelfRel()));
    }

    @GetMapping(value = "/{codigo}", produces = MediaTypes.HAL_JSON_VALUE)
    public ResponseEntity<EntityModel<TipoColaboradorDTO>> getTipoColaboradorByCodigo(@PathVariable Integer codigo) {
        TipoColaboradorDTO tipoColaborador = tipoColaboradorService.buscarPorId(codigo);
        if (tipoColaborador == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(assembler.toModel(tipoColaborador));
    }

    @PostMapping(produces = MediaTypes.HAL_JSON_VALUE)
    public ResponseEntity<EntityModel<TipoColaboradorDTO>> addTipoColaborador(
            @RequestBody TipoColaboradorDTO tipoColaborador) {
        TipoColaboradorDTO tipoColaboradorNuevo = tipoColaboradorService.guardarTipoColaborador(tipoColaborador);
        return ResponseEntity
                .created(linkTo(
                        methodOn(TipoColaboradorControllerV2.class)
                                .getTipoColaboradorByCodigo(tipoColaboradorNuevo.getIdTipoColaborador()))
                        .toUri())
                .body(assembler.toModel(tipoColaboradorNuevo));
    }

    @PutMapping(value = "/{codigo}", produces = MediaTypes.HAL_JSON_VALUE)
    public ResponseEntity<EntityModel<TipoColaboradorDTO>> updateTipoColaborador(@PathVariable Integer codigo,
            @RequestBody TipoColaboradorDTO tipoColaborador) {
        TipoColaboradorDTO updatedTipoCliente = tipoColaboradorService.actualizarTipoColaborador(codigo,
                tipoColaborador);
        return ResponseEntity.ok(assembler.toModel(updatedTipoCliente));
    }

    @DeleteMapping(value = "/{codigo}", produces = MediaTypes.HAL_JSON_VALUE)
    public ResponseEntity<Void> deleteTipoColaborador(@PathVariable Integer codigo) {
        TipoColaboradorDTO tipoColaborador = tipoColaboradorService.buscarPorId(codigo);
        if (tipoColaborador == null) {
            return ResponseEntity.notFound().build();
        }
        tipoColaboradorService.eliminarTipoColaborador(codigo);
        return ResponseEntity.noContent().build();
    }
}
