package com.prueba3.pc_factory.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.prueba3.pc_factory.model.Region;
import com.prueba3.pc_factory.services.RegionService;

@RestController
@RequestMapping("api/v1/regiones")
public class RegionController {
    @Autowired
    RegionService regionService;

    public ResponseEntity<?> getRegiones() {
        if (regionService.obtenerTodos().isEmpty()) {
            return ResponseEntity.status(HttpStatus.NO_CONTENT).body("No se encuentran regiones");
        }
        return new ResponseEntity<>(regionService.obtenerTodos(), HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<?> agregarRegion(@RequestBody Region r) {
        try {
            return new ResponseEntity<>("Region guardada correctamente" + regionService.guardarRegion(r),
                    HttpStatus.CREATED);
        } catch (Exception e) {
            return new ResponseEntity<>("ERROR", HttpStatus.BAD_REQUEST);
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> eliminarRegion(@PathVariable Integer id) {
        String resultado = regionService.eliminarRegion(id);

        if (resultado.contains("correctamente")) {
            return new ResponseEntity<>(resultado, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(resultado, HttpStatus.NOT_FOUND);
        }
    }
}