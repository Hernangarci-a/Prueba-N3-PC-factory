package com.prueba3.pc_factory.controller;

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

import com.prueba3.pc_factory.dto.TipoProductoDTO;
import com.prueba3.pc_factory.model.TipoProducto;
import com.prueba3.pc_factory.services.TipoProductoService;

@RestController
@RequestMapping("/api/v1/tipoproducto")
public class TipoProductoController {

    @Autowired
    private TipoProductoService tipoProductoService;

    @GetMapping
    public ResponseEntity<List<TipoProductoDTO>> todosLosProductos() {
        List<TipoProductoDTO> tipoPoducto = tipoProductoService.obtenerTodos();
        // si la lista está vacía devuelve un estado 204 No Content
        if (tipoPoducto.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        // si hay datos devuelve la lista con un estado 200 OK
        return new ResponseEntity<>(tipoPoducto, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<TipoProductoDTO> buscarPorId(@PathVariable Integer id) {
        try {
            TipoProductoDTO tipoproducto = tipoProductoService.buscarPorId(id);
            return new ResponseEntity<>(tipoproducto, HttpStatus.OK);
        } catch (RuntimeException e) {
            // Si el service lanza la excepción del producto no encontrado
            return ResponseEntity.notFound().build();
        }
    }

    @PostMapping
    public ResponseEntity<TipoProducto> agregarCategoria(@RequestBody TipoProducto tipoProducto) {
        try {
            TipoProducto guardado = tipoProductoService.guardarTipoProducto(tipoProducto);
            // retorna el producto guardado con el estado 201 creado
            return new ResponseEntity<>(guardado, HttpStatus.CREATED);
        } catch (Exception e) {
            // si algo falla en las validacion se retorna un estado 400 bad_request
            // solicitud incorrecta
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    @PutMapping("/{IdTipoProducto}/heroe/{idProductos}")
    public ResponseEntity<String> reclutarHeroe(@PathVariable Integer idTipoProducto,
            @PathVariable Integer idProductos) {
        try {
            String resultado = tipoProductoService.añadirTipoProductoAproductos(idTipoProducto, idProductos);
            return new ResponseEntity<>(resultado, HttpStatus.OK);
        } catch (RuntimeException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
        }
    }

    @DeleteMapping("/{idTipoProducto}")
    public ResponseEntity<?> deleteTipoProducto(@PathVariable Integer idTipoProducto) {
        try {
            String resultado = tipoProductoService.eliminarProductos(idTipoProducto);
            return new ResponseEntity<>(resultado, HttpStatus.OK);
        } catch (RuntimeException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
        }
    }
}
