package com.prueba3.pc_factory.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.prueba3.pc_factory.model.TipoDespacho;
import com.prueba3.pc_factory.services.TipoDespachoService;

@RequestMapping("api/v1/tipodespacho")
@RestController
public class TipoDespachoController {
    @Autowired
    TipoDespachoService tipoDespachoService;

    public ResponseEntity<?> getTipoDespacho() {
        if (tipoDespachoService.obtenerTodos().isEmpty()) {
            return ResponseEntity.status(HttpStatus.NO_CONTENT).body("No se encuentran tipos de despacho");
        }
        return new ResponseEntity<>(tipoDespachoService.obtenerTodos(), HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<?> agregarRegion(@RequestBody TipoDespacho t) {
        try {
            return new ResponseEntity<>("Region guardada correctamente" + tipoDespachoService.guardarTipoDespacho(t),
                    HttpStatus.CREATED);
        } catch (Exception e) {
            return new ResponseEntity<>("ERROR", HttpStatus.BAD_REQUEST);
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> actualizarCliente(@PathVariable Integer id, @RequestBody TipoDespacho t) {
        try {
            TipoDespacho regionNueva = tipoDespachoService.actualizarTipoDespacho(id, t);
            return new ResponseEntity<>(regionNueva, HttpStatus.OK);
        } catch (RuntimeException e) {
            return new ResponseEntity<>("Tipo de despacho no encontrado", HttpStatus.NOT_FOUND);
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> eliminarCliente(@PathVariable Integer id) {
        String resultado = tipoDespachoService.eliminartipoDespacho(id);

        if (resultado.contains("correctamente")) {
            return new ResponseEntity<>(resultado, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(resultado, HttpStatus.NOT_FOUND);
        }
    }
}
