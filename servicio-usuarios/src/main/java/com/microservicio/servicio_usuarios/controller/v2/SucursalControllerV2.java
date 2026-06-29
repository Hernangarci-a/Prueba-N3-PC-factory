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

import com.microservicio.servicio_usuarios.assemblers.SucursalModelAssembler;
import com.microservicio.servicio_usuarios.dto.SucursalDTO;
import com.microservicio.servicio_usuarios.services.SucursalService;

@RestController
@RequestMapping("api/v2/pcfactory/sucursales")
public class SucursalControllerV2 {
    @Autowired
    SucursalService sucursalService;

    @Autowired
    SucursalModelAssembler assembler;

    @GetMapping(produces = MediaTypes.HAL_JSON_VALUE)
    public ResponseEntity<CollectionModel<EntityModel<SucursalDTO>>> getAllSucursales() {
        List<EntityModel<SucursalDTO>> clientes = sucursalService.obtenerTodos().stream()
                .map(assembler::toModel)
                .collect(Collectors.toList());

        if (clientes.isEmpty()) {
            return ResponseEntity.noContent().build();
        }

        return ResponseEntity.ok(CollectionModel.of(
                clientes,
                linkTo(methodOn(SucursalControllerV2.class).getAllSucursales()).withSelfRel()));
    }

    @GetMapping(value = "/{codigo}", produces = MediaTypes.HAL_JSON_VALUE)
    public ResponseEntity<EntityModel<SucursalDTO>> getSucursalByCodigo(@PathVariable Integer codigo) {
        SucursalDTO sucursal = sucursalService.buscarPorId(codigo);
        if (sucursal == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(assembler.toModel(sucursal));
    }

    @PostMapping(produces = MediaTypes.HAL_JSON_VALUE)
    public ResponseEntity<EntityModel<SucursalDTO>> addSucursal(@RequestBody SucursalDTO sucursal) {
        SucursalDTO sucursalNueva = sucursalService.guardarSucursal(sucursal);
        return ResponseEntity
                .created(linkTo(
                        methodOn(SucursalControllerV2.class)
                                .getSucursalByCodigo(sucursalNueva.getIdSucursal()))
                        .toUri())
                .body(assembler.toModel(sucursalNueva));
    }

    @PutMapping(value = "/{codigo}", produces = MediaTypes.HAL_JSON_VALUE)
    public ResponseEntity<EntityModel<SucursalDTO>> updateSucursal(@PathVariable Integer codigo,
            @RequestBody SucursalDTO sucursal) {
        SucursalDTO updatedSucursal = sucursalService.actualizarSucursal(codigo, sucursal);
        return ResponseEntity.ok(assembler.toModel(updatedSucursal));
    }

    @DeleteMapping(value = "/{codigo}", produces = MediaTypes.HAL_JSON_VALUE)
    public ResponseEntity<Void> deleteSucursal(@PathVariable Integer codigo) {
        SucursalDTO sucursal = sucursalService.buscarPorId(codigo);
        if (sucursal == null) {
            return ResponseEntity.notFound().build();
        }
        sucursalService.eliminarSucursal(codigo);
        return ResponseEntity.noContent().build();
    }
}
