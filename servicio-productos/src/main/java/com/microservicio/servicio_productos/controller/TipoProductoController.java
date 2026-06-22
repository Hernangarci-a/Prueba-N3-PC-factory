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

import com.microservicio.servicio_productos.dto.TipoProductoDTO;
import com.microservicio.servicio_productos.model.TipoProducto;
import com.microservicio.servicio_productos.services.TipoProductoService;

import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
@RequestMapping("/api/v1/tipoproducto")
public class TipoProductoController {

    @Autowired
    private TipoProductoService tipoProductoService;

    @GetMapping
    public ResponseEntity<List<TipoProductoDTO>> todosLosTipos() {
        log.info("metodo GET Listar todas las Tipo de prodcutos ");
        List<TipoProductoDTO> tipoPoducto = tipoProductoService.obtenerTodos();
        // si la lista está vacía devuelve un estado 204 No Content
        if (tipoPoducto.isEmpty()) {
            log.info("respuesta 204 No Contentenido No hay tipo de productos registradas que mostrar");
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        log.info("respuesta 200 OK devuelve la lisa de tipo de productos");
        // si hay datos devuelve la lista con un estado 200 OK
        return new ResponseEntity<>(tipoPoducto, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<TipoProductoDTO> buscarPorId(@PathVariable Integer id) {
        log.info("Buscar tipo de producto por ID: {}", id);
        try {
            TipoProductoDTO tipoproducto = tipoProductoService.buscarPorId(id);
            log.info("Respuesta 200 OK tipo de producto encrotrado con el id: {}", id);
            return new ResponseEntity<>(tipoproducto, HttpStatus.OK);
        } catch (RuntimeException e) {
            log.error("Respuesta 404 not found el tipo de producto no fue encontrado: {}", e.getMessage());
            // Si el service lanza la excepción del producto no encontrado
            return ResponseEntity.notFound().build();
        }
    }

    @PostMapping
    public ResponseEntity<?> agregarCategoria(@Valid @RequestBody TipoProducto tipoProducto) {
        log.info("Petición POST guardar nuevo tipo de producto");
        try {
            TipoProducto guardado = tipoProductoService.guardarTipoProducto(tipoProducto);
            log.info("metodo 201 Creado tipo de producto registrada con ID: {}", guardado.getIdTipoProducto());
            // retorna el producto guardado con el estado 201 creado
            return new ResponseEntity<>(guardado, HttpStatus.CREATED);
        } catch (Exception e) {
            log.error("Respuesta 400 Bad Request fallo alguna validacion: {}", e.getMessage());
            // si algo falla en las validacion se retorna un estado 400 bad_request
            // solicitud incorrecta
            return new ResponseEntity<>("ERROR validaciones no respetadas", HttpStatus.BAD_REQUEST);
        }
    }

    @PutMapping("/{IdTipoProducto}/producto/{idProductos}")
    public ResponseEntity<String> reclutarTipoProductoUnProducto(@Valid @PathVariable Integer idTipoProducto,
            @PathVariable Integer idProductos) {
        log.info("metodo PUT añadiendo producto: {} a categoria: {}", idTipoProducto,
                idProductos);
        try {
            String resultado = tipoProductoService.añadirTipoProductoAproductos(idTipoProducto, idProductos);
            log.info("Repuesta 200 OK se a podido añadir tipo de productos a un producto con exito");
            return new ResponseEntity<>(resultado, HttpStatus.OK);
        } catch (RuntimeException e) {
            log.error("Respuesta 404 Not Found no se pudo anadir tipo de productos a un producto:{}", e.getMessage());
            return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
        }
    }

    @DeleteMapping("/{idTipoProducto}")
    public ResponseEntity<?> deleteTipoProducto(@PathVariable Integer idTipoProducto) {
        log.info("metodo DELETE eliminacion de tipo de producto: {}", idTipoProducto);
        try {
            String resultado = tipoProductoService.eliminarTipoProdcucto(idTipoProducto);
            log.info("Respuesta 200 OK tipo de producto fue eliminado exitosamente ID: {}", idTipoProducto);
            return new ResponseEntity<>(resultado, HttpStatus.OK);
        } catch (RuntimeException e) {
            log.error("Respuesta 404 Not Found eliminacion fallida: {}", e.getMessage());
            return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
        }
    }
}
