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

import com.microservicio.servicio_ventas.model.TipoDespacho;
import com.microservicio.servicio_ventas.services.TipoDespachoService;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@RequestMapping("api/v1/tipodespacho")
@RestController
public class TipoDespachoController {

    @Autowired
    TipoDespachoService tipoDespachoService;

    @GetMapping
    public ResponseEntity<?> getTipoDespacho(){
        log.info("consultando tipos de despacho");
        if (tipoDespachoService.obtenerTodos().isEmpty()){
            log.warn("no se encuentran tipos de despacho");
            return ResponseEntity.status(HttpStatus.NO_CONTENT).body("No se encuentran tipos de despacho");
        }
        log.info("tipos de despacho obtenidos");
        return new ResponseEntity<>(tipoDespachoService.obtenerTodos(), HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<?> agregarRegion(@RequestBody TipoDespacho t){
        log.info("intentando registrar tipo de despacho");
        try{
            return new ResponseEntity<>("Region guardada correctamente" + tipoDespachoService.guardarTipoDespacho(t),
                    HttpStatus.CREATED);
        } catch (Exception e){
            log.error("error al buscar despacho", e.getMessage());
            return new ResponseEntity<>("ERROR", HttpStatus.BAD_REQUEST);
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> actualizarCliente(@PathVariable Integer id, @RequestBody TipoDespacho t){
        log.info("intentando actualizar tipo de despacho id", id);
        try{
            TipoDespacho regionNueva = tipoDespachoService.actualizarTipoDespacho(id, t);
            log.info("tipo de despacho actualizado id", id);
            return new ResponseEntity<>(regionNueva, HttpStatus.OK);
        } catch (RuntimeException e){
            log.warn("tipo de despacho no encontrado id", id);
            return new ResponseEntity<>("tipo de despacho no encontrado", HttpStatus.NOT_FOUND);
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> eliminarCliente(@PathVariable Integer id){
        log.info("intentando eliminar tipo de despacho id", id);
        String resultado = tipoDespachoService.eliminartipoDespacho(id);

        if (resultado.contains("correctamente")){
            log.info("tipo de despacho eliminado id", id);
            return new ResponseEntity<>(resultado, HttpStatus.OK);
        } else{
            log.warn("no se pudo eliminar el tipo de despacho id", id);
            return new ResponseEntity<>(resultado, HttpStatus.NOT_FOUND);
        }
    }
}
