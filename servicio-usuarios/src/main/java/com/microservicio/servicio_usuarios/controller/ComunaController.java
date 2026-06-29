package com.microservicio.servicio_usuarios.controller;

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

import com.microservicio.servicio_usuarios.dto.ComunaDTO;
import com.microservicio.servicio_usuarios.model.Comuna;
import com.microservicio.servicio_usuarios.services.ComunaService;

@RestController
@RequestMapping("api/v1/comunas")
public class ComunaController {

    @Autowired
    ComunaService comunaService;

    @GetMapping
    public ResponseEntity<?> getComunas() {
        if (comunaService.obtenerTodos().isEmpty()) {
            return ResponseEntity.status(HttpStatus.NO_CONTENT).body("No se encuentran comunas");
        }
        return new ResponseEntity<>(comunaService.obtenerTodos(), HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<?> agregarComuna(@RequestBody ComunaDTO c) {
        try {
            return new ResponseEntity<>("Comuna guardada correctamente" + comunaService.guardarComuna(c),
                    HttpStatus.CREATED);
        } catch (Exception e) {
            return new ResponseEntity<>("ERROR", HttpStatus.BAD_REQUEST);
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> eliminarComuna(@PathVariable Integer id) {
        String resultado = comunaService.eliminarComuna(id);

        if (resultado.contains("correctamente")) {
            return new ResponseEntity<>(resultado, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(resultado, HttpStatus.NOT_FOUND);
        }
    }
}
