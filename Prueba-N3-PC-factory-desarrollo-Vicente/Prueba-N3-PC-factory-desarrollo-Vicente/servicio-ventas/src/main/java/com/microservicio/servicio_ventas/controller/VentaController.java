package com.microservicio.servicio_ventas.controller;

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
import lombok.extern.slf4j.Slf4j;

import com.microservicio.servicio_ventas.model.Ventas;
import com.microservicio.servicio_ventas.services.VentasService;

@RestController
@Slf4j
@RequestMapping("api/v1/ventas")
public class VentaController {

    @Autowired
    VentasService ventaService;

    @GetMapping
    public ResponseEntity<?> getVentas(){
        log.info("viendo lista de vaentas");
        if (ventaService.obtenerTodos().isEmpty()){
            log.warn("no se encontraron ventas");
            return ResponseEntity.status(HttpStatus.NO_CONTENT).body("no se encuentran ventas");
        }
        log.info("ventas obtenidas");
        return new ResponseEntity<>(ventaService.obtenerTodos(), HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<?> agregarVenta(@RequestBody Ventas v){
    log.info("intentando agregar venta");
    try{
        return new ResponseEntity<>(
                "venta guardada correctamente" + ventaService.guardarVenta(v),
                HttpStatus.CREATED);
    } catch (Exception e){
        log.error("error al registrar venta", e.getMessage());
        return new ResponseEntity<>("error", HttpStatus.BAD_REQUEST);
    }
}

    @PutMapping("/{id}")
    public ResponseEntity<?> actualizarVenta(@PathVariable Integer id, @RequestBody Ventas v){
        log.info("intentando actualizar venta id", id);
        try{
            Ventas ventaNueva = ventaService.actualizarVenta(id, v);
            log.info("venta actualizada id", id);
            return new ResponseEntity<>(ventaNueva, HttpStatus.OK);
        } catch (RuntimeException e){
            log.warn("No se encontro la venta id", id);
            return new ResponseEntity<>("venta no encontrada", HttpStatus.NOT_FOUND);
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> eliminarVenta(@PathVariable Integer id){
        log.info("intentando eliminar venta id", id);
        String resultado = ventaService.eliminarVenta(id);

        if (resultado.contains("correctamente")){
            log.info("venta eliminada id", id);
            return new ResponseEntity<>(resultado, HttpStatus.OK);
        } else{
            log.warn("no se pudo eliminar id", id);
            return new ResponseEntity<>(resultado, HttpStatus.NOT_FOUND);
        }
    }
}
