package com.microservicio.servicio_usuarios.controller;

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

import com.microservicio.servicio_usuarios.model.Sucursal;
import com.microservicio.servicio_usuarios.services.SucursalService;

@RestController
@RequestMapping("api/v1/sucursales")
public class SucursalController {

    @Autowired
    SucursalService sucursalService;

    public ResponseEntity<?> getSucursales() {
        if (sucursalService.obtenerTodos().isEmpty()) {
            return ResponseEntity.status(HttpStatus.NO_CONTENT).body("No se encuentran sucursales");
        }
        return new ResponseEntity<>(sucursalService.obtenerTodos(), HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<?> agregarSucursal(@RequestBody Sucursal s) {
        try {
            return new ResponseEntity<>("Sucursal guardada correctamente" + sucursalService.guardarSucursal(s),
                    HttpStatus.CREATED);
        } catch (Exception e) {
            return new ResponseEntity<>("ERROR", HttpStatus.BAD_REQUEST);
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> actualizarSucursal(@PathVariable Integer id, @RequestBody Sucursal s) {
        try {
            Sucursal sucursalNueva = sucursalService.actualizarSucursal(id, s);
            return new ResponseEntity<>(sucursalNueva, HttpStatus.OK);
        } catch (RuntimeException e) {
            return new ResponseEntity<>("Sucursal no encontrada", HttpStatus.NOT_FOUND);
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> eliminarSucursal(@PathVariable Integer id) {
        String resultado = sucursalService.eliminarSucursal(id);

        if (resultado.contains("correctamente")) {
            return new ResponseEntity<>(resultado, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(resultado, HttpStatus.NOT_FOUND);
        }
    }
}
