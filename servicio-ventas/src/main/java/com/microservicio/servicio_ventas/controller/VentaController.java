package com.microservicio.servicio_ventas.controller;

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

import com.microservicio.servicio_ventas.model.Ventas;
import com.microservicio.servicio_ventas.services.VentasService;

@RestController
@RequestMapping("api/v1/ventas")
public class VentaController {

    @Autowired
    VentasService ventaService;

    public ResponseEntity<?> getVentas() {
        if (ventaService.obtenerTodos().isEmpty()) {
            return ResponseEntity.status(HttpStatus.NO_CONTENT).body("No se encuentran ventas");
        }
        return new ResponseEntity<>(ventaService.obtenerTodos(), HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<?> agregarVenta(@RequestBody Ventas v) {
        try {
            return new ResponseEntity<>("Venta guardada correctamente" + ventaService.guardarVenta(v),
                    HttpStatus.CREATED);
        } catch (Exception e) {
            return new ResponseEntity<>("ERROR", HttpStatus.BAD_REQUEST);
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> actualizarVenta(@PathVariable Integer id, @RequestBody Ventas v) {
        try {
            Ventas ventaNueva = ventaService.actualizarVenta(id, v);
            return new ResponseEntity<>(ventaNueva, HttpStatus.OK);
        } catch (RuntimeException e) {
            return new ResponseEntity<>("Venta no encontrada", HttpStatus.NOT_FOUND);
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> eliminarVenta(@PathVariable Integer id) {
        String resultado = ventaService.eliminarVenta(id);

        if (resultado.contains("correctamente")) {
            return new ResponseEntity<>(resultado, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(resultado, HttpStatus.NOT_FOUND);
        }
    }
}
