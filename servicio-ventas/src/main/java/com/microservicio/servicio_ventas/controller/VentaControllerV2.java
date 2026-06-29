package com.microservicio.servicio_ventas.controller;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.MediaTypes;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.microservicio.servicio_ventas.Assembler.VentasModelAssembler;
import com.microservicio.servicio_ventas.dto.VentasDTO;
import com.microservicio.servicio_ventas.model.Ventas;
import com.microservicio.servicio_ventas.services.VentasService;

import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@Slf4j
@RestController("ventaControllerV2")
@RequestMapping("api/v2/ventas")
public class VentaControllerV2 {

    @Autowired
    private VentasService ventaService;

    @Autowired
    private VentasModelAssembler assembler;

    @GetMapping(produces = MediaTypes.HAL_JSON_VALUE)
    public ResponseEntity<CollectionModel<EntityModel<VentasDTO>>> todas() {
        List<EntityModel<VentasDTO>> ventas = ventaService.obtenerTodos().stream()
                .map(assembler::toModel)
                .collect(Collectors.toList());

        if (ventas.isEmpty()) {
            return ResponseEntity.noContent().build();
        }

        return ResponseEntity.ok(CollectionModel.of(ventas,
                linkTo(methodOn(VentaControllerV2.class).todas()).withSelfRel()));
    }

    @GetMapping(value = "/{id}", produces = MediaTypes.HAL_JSON_VALUE)
    public ResponseEntity<EntityModel<VentasDTO>> porId(@PathVariable Integer id) {
        try {
            VentasDTO dto = ventaService.buscarPorId(id);
            return ResponseEntity.ok(assembler.toModel(dto));
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @PostMapping(produces = MediaTypes.HAL_JSON_VALUE)
    public ResponseEntity<?> registrar(@Valid @RequestBody Ventas venta) {
        try {
            Ventas guardada = ventaService.guardarVenta(venta);
            VentasDTO dto = ventaService.buscarPorId(guardada.getIdVenta());
            return ResponseEntity
                    .created(linkTo(methodOn(VentaControllerV2.class).porId(dto.getIdVenta())).toUri())
                    .body(assembler.toModel(dto));
        } catch (Exception e) {
            return ResponseEntity.badRequest().build();
        }
    }
}