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

import com.microservicio.servicio_usuarios.model.TipoColaborador;
import com.microservicio.servicio_usuarios.services.TipoColaboradorService;

@RestController()
@RequestMapping("api/v1/tipocolaboradores")
public class TipoColaboradorController {

    @Autowired
    TipoColaboradorService tipoColaboradorService;

    @GetMapping
    public ResponseEntity<?> getTipoColaborador() {
        if (tipoColaboradorService.obtenerTodos().isEmpty()) {
            return ResponseEntity.status(HttpStatus.NO_CONTENT).body("No se encuentran tipos de colaboradores");
        }
        return new ResponseEntity<>(tipoColaboradorService.obtenerTodos(), HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<TipoColaborador> agregarTipoColaborador(@RequestBody TipoColaborador tipoColaborador) {
        try {
            TipoColaborador guardado = tipoColaboradorService.guardarTipoColaborador(tipoColaborador);
            // retorna el producto guardado con el estado 201 creado
            return new ResponseEntity<>(guardado, HttpStatus.CREATED);
        } catch (Exception e) {
            // si algo falla en las validacion se retorna un estado 400 bad_request
            // solicitud incorrecta
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> actualizarTipoColaborador(@PathVariable Integer id, @RequestBody TipoColaborador t) {
        try {
            TipoColaborador tipoColaboradorNuevo = tipoColaboradorService.actualizarTipoColaborador(id, t);
            return new ResponseEntity<>(tipoColaboradorNuevo, HttpStatus.OK);
        } catch (RuntimeException e) {
            return new ResponseEntity<>("Tipo de colaborador no encontrado", HttpStatus.NOT_FOUND);
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> eliminarTipoColaborador(@PathVariable Integer id) {
        String resultado = tipoColaboradorService.eliminarTipoColaborador(id);

        if (resultado.contains("correctamente")) {
            return new ResponseEntity<>(resultado, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(resultado, HttpStatus.NOT_FOUND);
        }
    }
}
