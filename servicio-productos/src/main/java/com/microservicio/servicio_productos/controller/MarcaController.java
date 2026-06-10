package com.microservicio.servicio_productos.controller;

import java.util.List;

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

import com.microservicio.servicio_productos.dto.MarcaDTO;
import com.microservicio.servicio_productos.model.Marca;
import com.microservicio.servicio_productos.services.MarcaService;

import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RequestMapping("/api/v1/marca")
@RestController
public class MarcaController {

    @Autowired
    private MarcaService marcaService;

    @GetMapping
    public ResponseEntity<?> getMarcas() {
        List<MarcaDTO> marcas = marcaService.obtenerTodos();
        log.info("metodo GET listar todas las marcas");
        // si la lista está vacía devuelve un estado 204 No Content
        if (marcas.isEmpty()) {
            log.info("respuesta 204 no contenido no hay marcas registradas que mostrar");
            return ResponseEntity.status(HttpStatus.NO_CONTENT).body("No se encuentran marcas");
        }
        // si hay datos devuelve la lista con un estado 200 OK
        return new ResponseEntity<>(marcas, HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<?> agregarMarca(@Valid @RequestBody Marca marca) {
        log.info("metodo POST guardar nueva marca: {}", marca);
        try {
            Marca guardado = marcaService.guardarMarca(marca);
            log.info("respuesta 201 creado marca registrada ID: {}", guardado.getIdMarca());
            return new ResponseEntity<>(guardado, HttpStatus.CREATED);
        } catch (Exception e) {
            log.error("Respuesta 400 Bad request fallo de alguna validacion");
            return new ResponseEntity<>("ERROR validaciones no respetadas", HttpStatus.BAD_REQUEST);
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> eliminarMarca(@PathVariable Integer id) {
        log.info("metodo DETELE para eliminar una marca: {}", id);
        // En lugar de evaluar el texto del String, usamos el try catch de forma
        // efectiva
        // Si el service no lanza excepción, asumimos que se borró con éxito
        try {
            String resultado = marcaService.eliminarMarca(id);
            log.error("respuesta 400 no se pudo eliminar la marca ya que esta asociado a un producto");
            // este if valida la condicional definida en el services de que si esta
            // asocicado a un producto no se puede eliminar
            if (resultado.contains("No se puede eliminar")) {
                return new ResponseEntity<>(resultado, HttpStatus.BAD_REQUEST);
            }
            log.info("respuesta 200 la marca fue eliminada");
            return new ResponseEntity<>(resultado, HttpStatus.OK);
        } catch (Exception e) {
            log.error("Respuesta 404 not found no se encontro la marca para eliminar");
            return new ResponseEntity<>("Error al eliminar la marca o registro no encontrado", HttpStatus.NOT_FOUND);
        }
    }
}
