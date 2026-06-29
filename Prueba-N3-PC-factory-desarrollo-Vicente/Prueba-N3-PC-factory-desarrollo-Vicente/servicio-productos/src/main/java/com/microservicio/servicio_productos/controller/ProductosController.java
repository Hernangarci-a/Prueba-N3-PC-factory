package com.microservicio.servicio_productos.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.microservicio.servicio_productos.dto.ProductosDTO;
import com.microservicio.servicio_productos.model.Productos;
import com.microservicio.servicio_productos.services.ProductosService;

@RestController
@RequestMapping("/api/v1/productos")
public class ProductosController {

    @Autowired
    private ProductosService productosService;

    @GetMapping
    public ResponseEntity<List<ProductosDTO>> todosLosProductos() {
        List<ProductosDTO> productos = productosService.obtenerTodos();
        // si la lista está vacía devuelve un estado 204 No Content
        if (productos.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        // si hay datos devuelve la lista con un estado 200 OK
        return new ResponseEntity<>(productos, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ProductosDTO> buscarPorId(@PathVariable Integer id) {
        try {
            ProductosDTO product = productosService.buscarPorId(id);
            return new ResponseEntity<>(product, HttpStatus.OK);
        } catch (RuntimeException e) {
            // Si el service lanza la excepción del producto no encontrado
            return ResponseEntity.notFound().build();
        }
    }

    @PostMapping
    public ResponseEntity<Productos> agregarProductos(@RequestBody Productos producto) {
        try {
            Productos guardado = productosService.guardarProductos(producto);
            // retorna el producto guardado con el estado 201 creado
            return new ResponseEntity<>(guardado, HttpStatus.CREATED);
        } catch (Exception e) {
            // si algo falla en las validacion se retorna un estado 400 bad_request
            // solicitud incorrecta
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    @PatchMapping("/{id}")
    public ResponseEntity<Productos> editarHeroe(@PathVariable Integer id, @RequestBody Productos product) {
        try {
            Productos editado = productosService.guardarProductos(product);
            return new ResponseEntity<>(editado, HttpStatus.OK);
        } catch (RuntimeException e) {
            // si el service da un error por que el id no existe devuelve un estado 404
            // not_found no encontrado
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<Productos> actualizarProductos(@PathVariable Integer id, @RequestBody Productos product) {
        try {
            Productos nuevoProducto = productosService.actualizarProductos(id, product);
            return new ResponseEntity<>(nuevoProducto, HttpStatus.OK);
        } catch (RuntimeException e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> eliminarProductos(@PathVariable Integer id) {
        String resultado = productosService.eliminarProductos(id);

        // Si el mensaje contiene exitosamente es un éxito
        if (resultado.contains("exitosamente")) {
            return new ResponseEntity<>(resultado, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(resultado, HttpStatus.NOT_FOUND);
        }
    }

}
