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

import com.microservicio.servicio_usuarios.assemblers.ColaboradorModelAssembler;
import com.microservicio.servicio_usuarios.dto.ColaboradorDTO;
import com.microservicio.servicio_usuarios.model.Colaborador;
import com.microservicio.servicio_usuarios.services.ColaboradorService;

@RestController
@RequestMapping("api/v2/pcfactory/colaboradores")
public class ColaboradorControllerV2 {

    @Autowired
    ColaboradorService colaboradorService;

    @Autowired
    ColaboradorModelAssembler assembler;

    @GetMapping(produces = MediaTypes.HAL_JSON_VALUE)
    public ResponseEntity<CollectionModel<EntityModel<ColaboradorDTO>>> getAllColaboradores() {
        List<EntityModel<ColaboradorDTO>> clientes = colaboradorService.obtenerTodos().stream()
                .map(assembler::toModel)
                .collect(Collectors.toList());

        if (clientes.isEmpty()) {
            return ResponseEntity.noContent().build();
        }

        return ResponseEntity.ok(CollectionModel.of(
                clientes,
                linkTo(methodOn(ColaboradorControllerV2.class).getAllColaboradores()).withSelfRel()));
    }

    @GetMapping(value = "/{codigo}", produces = MediaTypes.HAL_JSON_VALUE)
    public ResponseEntity<EntityModel<ColaboradorDTO>> getColaboradorByCodigo(@PathVariable Integer codigo) {
        ColaboradorDTO colaborador = colaboradorService.buscarPorId(codigo);
        if (colaborador == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(assembler.toModel(colaborador));
    }

    @PostMapping(produces = MediaTypes.HAL_JSON_VALUE)
    public ResponseEntity<EntityModel<ColaboradorDTO>> addColaborador(@RequestBody Colaborador colaborador) {
        ColaboradorDTO colaboradorNuevo = colaboradorService.guardarColaborador(colaborador);
        return ResponseEntity
                .created(linkTo(
                        methodOn(ColaboradorControllerV2.class)
                                .getColaboradorByCodigo(colaboradorNuevo.getIdColaborador()))
                        .toUri())
                .body(assembler.toModel(colaboradorNuevo));
    }

    @PutMapping(value = "/{codigo}", produces = MediaTypes.HAL_JSON_VALUE)
    public ResponseEntity<EntityModel<ColaboradorDTO>> updateColaborador(@PathVariable Integer codigo,
            @RequestBody ColaboradorDTO colaborador) {
        Colaborador c = colaboradorService.convertirColaborador(colaborador);
        ColaboradorDTO updatedColaborador = colaboradorService.actualizarColaborador(codigo, c);
        return ResponseEntity.ok(assembler.toModel(updatedColaborador));
    }

    @DeleteMapping(value = "/{codigo}", produces = MediaTypes.HAL_JSON_VALUE)
    public ResponseEntity<Void> deleteColaborador(@PathVariable Integer codigo) {
        ColaboradorDTO colaborador = colaboradorService.buscarPorId(codigo);
        if (colaborador == null) {
            return ResponseEntity.notFound().build();
        }
        colaboradorService.eliminarColaborador(codigo);
        return ResponseEntity.noContent().build();
    }
}
