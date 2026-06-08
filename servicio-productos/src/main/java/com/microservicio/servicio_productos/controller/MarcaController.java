package com.microservicio.servicio_productos.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.microservicio.servicio_productos.model.Marca;
import com.microservicio.servicio_productos.services.MarcaService;

import jakarta.validation.Valid;

@RequestMapping("api/v1/marca")
@RestController
public class MarcaController {
    @Autowired
    MarcaService marcaService;

    @GetMapping
    public ResponseEntity<?> getMarcas() {
        if (marcaService.obtenerTodos().isEmpty()) {
            return ResponseEntity.status(HttpStatus.NO_CONTENT).body("No se encuentran marcas");
        }
        return new ResponseEntity<>(marcaService.obtenerTodos(), HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<?> agregarMarca(@Valid @RequestBody Marca m) {
        try {
            return new ResponseEntity<>("Marca guardada correctamente" + marcaService.guardarMarca(m),
                    HttpStatus.CREATED);
        } catch (Exception e) {
            return new ResponseEntity<>("ERROR validaciones no respetadas", HttpStatus.BAD_REQUEST);
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> eliminarMarca(@PathVariable Integer id) {
        String resultado = marcaService.eliminarMarca(id);

        if (resultado.contains("correctamente")) {
            return new ResponseEntity<>(resultado, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(resultado, HttpStatus.NOT_FOUND);
        }
    }
}
