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

import com.microservicio.servicio_usuarios.assemblers.RegionModelAssembler;
import com.microservicio.servicio_usuarios.dto.RegionDTO;
import com.microservicio.servicio_usuarios.services.RegionService;

@RestController
@RequestMapping("api/v2/pcfactory/regiones")
public class RegionControllerV2 {
    @Autowired
    private RegionService regionService;

    @Autowired
    private RegionModelAssembler assembler;

    @GetMapping(produces = MediaTypes.HAL_JSON_VALUE)
    public ResponseEntity<CollectionModel<EntityModel<RegionDTO>>> getAllRegiones() {
        List<EntityModel<RegionDTO>> regiones = regionService.obtenerTodos().stream()
                .map(assembler::toModel)
                .collect(Collectors.toList());

        if (regiones.isEmpty()) {
            return ResponseEntity.noContent().build();
        }

        return ResponseEntity.ok(CollectionModel.of(
                regiones,
                linkTo(methodOn(RegionControllerV2.class).getAllRegiones()).withSelfRel()));
    }

    @GetMapping(value = "/{codigo}", produces = MediaTypes.HAL_JSON_VALUE)
    public ResponseEntity<EntityModel<RegionDTO>> getRegionByCodigo(@PathVariable Integer codigo) {
        RegionDTO region = regionService.buscarPorId(codigo);
        if (region == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(assembler.toModel(region));
    }

    @PostMapping(produces = MediaTypes.HAL_JSON_VALUE)
    public ResponseEntity<EntityModel<RegionDTO>> addRegion(@RequestBody RegionDTO region) {
        RegionDTO regionNueva = regionService.guardarRegion(region);
        return ResponseEntity
                .created(linkTo(methodOn(ComunaControllerV2.class).getComunaByCodigo(regionNueva.getIdRegion()))
                        .toUri())
                .body(assembler.toModel(regionNueva));
    }

    @DeleteMapping(value = "/{codigo}", produces = MediaTypes.HAL_JSON_VALUE)
    public ResponseEntity<Void> deleteRegion(@PathVariable Integer codigo) {
        RegionDTO comuna = regionService.buscarPorId(codigo);
        if (comuna == null) {
            return ResponseEntity.notFound().build();
        }
        regionService.eliminarRegion(codigo);
        return ResponseEntity.noContent().build();
    }
}
