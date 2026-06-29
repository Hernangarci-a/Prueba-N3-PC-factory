package com.microservicio.servicio_usuarios.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.microservicio.servicio_usuarios.dto.ColaboradorDTO;
import com.microservicio.servicio_usuarios.model.Colaborador;
import com.microservicio.servicio_usuarios.services.ColaboradorService;

@RequestMapping("api/v1/colaboradores")
@RestController
public class ColaboradorController {

    @Autowired
    ColaboradorService colaboradorService;

    @GetMapping
    public ResponseEntity<?> getColaboradores() {
        if (colaboradorService.obtenerTodos().isEmpty()) {
            return ResponseEntity.status(HttpStatus.NO_CONTENT).body("No se encuentran colaboradores");
        }
        return new ResponseEntity<>(colaboradorService.obtenerTodos(), HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<?> agregarColaborador(@RequestBody Colaborador c) {
        try {
            return new ResponseEntity<>("Colaborador guardado correctamente" + colaboradorService.guardarColaborador(c),
                    HttpStatus.CREATED);
        } catch (Exception e) {
            return new ResponseEntity<>("ERROR: ", HttpStatus.BAD_REQUEST);
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> actualizarColaborador(@PathVariable Integer id, @RequestBody Colaborador c) {
        try {
            ColaboradorDTO colaboradorNuevo = colaboradorService.actualizarColaborador(id, c);
            return new ResponseEntity<>(colaboradorNuevo, HttpStatus.OK);
        } catch (RuntimeException e) {
            return new ResponseEntity<>("Colaborador no encontrado", HttpStatus.NOT_FOUND);
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> eliminarColaborador(@PathVariable Integer id) {
        String resultado = colaboradorService.eliminarColaborador(id);

        if (resultado.contains("correctamente")) {
            return new ResponseEntity<>(resultado, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(resultado, HttpStatus.NOT_FOUND);
        }
    }
}
