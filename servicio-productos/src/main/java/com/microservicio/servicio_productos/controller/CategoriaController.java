package com.microservicio.servicio_productos.controller;

import java.util.List;

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

import com.microservicio.servicio_productos.dto.CategoriaDTO;
import com.microservicio.servicio_productos.model.Categoria;
import com.microservicio.servicio_productos.services.CategoriaService;

import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
@RequestMapping("/api/v1/categoria")
public class CategoriaController {

    @Autowired
    private CategoriaService categoriaService;

    @GetMapping
    public ResponseEntity<List<CategoriaDTO>> todosLasCategorias() {
        log.info("metodo GET Listar todas las categorías ");
        List<CategoriaDTO> categorias = categoriaService.obtenerTodos();
        // si la lista está vacía devuelve un estado 204 No Content
        if (categorias.isEmpty()) {
            log.info("respuesta 204 No Contentenido No hay categorías registradas que mostrar");
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        // si hay datos devuelve la lista con un estado 200 OK
        return new ResponseEntity<>(categorias, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<CategoriaDTO> buscarPorId(@PathVariable Integer id) {
        log.info("metodo GET Buscar categoría por ID: {}", id);
        try {
            CategoriaDTO categ = categoriaService.buscarPorId(id);
            return new ResponseEntity<>(categ, HttpStatus.OK);
        } catch (RuntimeException e) {
            log.error("Respuesta 404 not found el producto no fue encontrado: {}", e.getMessage());
            // Si el service lanza la excepción del producto no encontrado
            return ResponseEntity.notFound().build();
        }
    }

    @PostMapping
    public ResponseEntity<?> agregarCategoria(@Valid @RequestBody Categoria categoria) {
        log.info("Petición POST guardar nueva categoría");
        try {
            Categoria guardado = categoriaService.guardarCategoria(categoria);
            log.info("metodo 201 Creado Categoría registrada con ID: {}", guardado.getIdCategoria());
            // retorna el producto guardado con el estado 201 creado
            return new ResponseEntity<>(guardado, HttpStatus.CREATED);
        } catch (Exception e) {
            // si algo falla en las validacion se retorna un estado 400 bad_request
            // solicitud incorrecta
            log.error("Respuesta 400 Bad Request fallo alguna validacion: {}", e.getMessage());
            return new ResponseEntity<>("ERROR validaciones no respetadas", HttpStatus.BAD_REQUEST);
        }
    }

    @PutMapping("/{idCategoria}/productos/{idProductos}")
    public ResponseEntity<?> añadirPrductoUnaCtegortia(@Valid @PathVariable Integer idCategoria,
            @PathVariable Integer idProductos) {
        log.info("metodo PUT añadiendo producto: {} a categoria: {}", idCategoria,
                idProductos);
        try {
            Categoria resultado = categoriaService.añadirProductoACategoria(idCategoria, idProductos);
            return new ResponseEntity<>(resultado, HttpStatus.OK);
        } catch (RuntimeException e) {
            log.error("Respuesta 404 Not Found no se pudo anadir producto a categoria{}", e.getMessage());
            return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> eliminarCategoria(@PathVariable Integer id) {
        log.info("metodo DELETE eliminacion de categoria: {}", id);
        try {
            categoriaService.eliminarCategoria(id);
            log.info("Respuesta 200 OK Categoría ID: {} eliminada con éxito", id);
            return new ResponseEntity<>("La categoría con ID " + id + " ha sido retirada exitosamente.", HttpStatus.OK);
        } catch (RuntimeException e) {
            log.error("Respuesta 404 Not Found eliminacion fallida: {}", e.getMessage());
            return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
        }
    }

}
