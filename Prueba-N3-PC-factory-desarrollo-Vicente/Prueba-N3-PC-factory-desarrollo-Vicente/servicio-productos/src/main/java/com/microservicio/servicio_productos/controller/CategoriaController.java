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

@RestController
@RequestMapping("/api/v1/categoria")
public class CategoriaController {

    @Autowired
    private CategoriaService categoriaService;

    @GetMapping
    public ResponseEntity<List<CategoriaDTO>> todosLosProductos() {
        List<CategoriaDTO> categorias = categoriaService.obtenerTodos();
        // si la lista está vacía devuelve un estado 204 No Content
        if (categorias.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        // si hay datos devuelve la lista con un estado 200 OK
        return new ResponseEntity<>(categorias, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<CategoriaDTO> buscarPorId(@PathVariable Integer id) {
        try {
            CategoriaDTO categ = categoriaService.buscarPorId(id);
            return new ResponseEntity<>(categ, HttpStatus.OK);
        } catch (RuntimeException e) {
            // Si el service lanza la excepción del producto no encontrado
            return ResponseEntity.notFound().build();
        }
    }

    @PostMapping
    public ResponseEntity<Categoria> agregarCategoria(@RequestBody Categoria categoria) {
        try {
            Categoria guardado = categoriaService.guardarCategoria(categoria);
            // retorna el producto guardado con el estado 201 creado
            return new ResponseEntity<>(guardado, HttpStatus.CREATED);
        } catch (Exception e) {
            // si algo falla en las validacion se retorna un estado 400 bad_request
            // solicitud incorrecta
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    @PutMapping("/{idCategoria}/productos/{idProductos}")
    public ResponseEntity<?> reclutarHeroe(@PathVariable Integer idCategoria, @PathVariable Integer idProductos) {
        try {
            Categoria resultado = categoriaService.añadirProductoACategoria(idCategoria, idProductos);
            return new ResponseEntity<>(resultado, HttpStatus.OK);
        } catch (RuntimeException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> eliminarCategoria(@PathVariable Integer id) {
        String resultado = categoriaService.eliminarCategoria(id);

        // Si el mensaje contiene exitosamente es un éxito
        if (resultado.contains("exitosamente")) {
            return new ResponseEntity<>(resultado, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(resultado, HttpStatus.NOT_FOUND);
        }
    }

}
