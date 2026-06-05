package com.microservicio.servicio_usuarios.controller;

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

import com.microservicio.servicio_usuarios.model.Cliente;
import com.microservicio.servicio_usuarios.services.ClienteService;

@RestController
@RequestMapping("api/v1/pcfactory/clientes")
public class ClienteController {
    @Autowired
    ClienteService clienteService;

    @GetMapping
    public ResponseEntity<?> getClientes() {
        if (clienteService.obtenerTodos().isEmpty()) {
            return ResponseEntity.status(HttpStatus.NO_CONTENT).body("No se encuentran clientes");
        }
        return new ResponseEntity<>(clienteService.obtenerTodos(), HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<?> agregarCliente(@RequestBody Cliente cliente) {
        try {
            return new ResponseEntity<>("Cliente guardado correctamente" + clienteService.guardarCliente(cliente),
                    HttpStatus.CREATED);
        } catch (Exception e) {
            return new ResponseEntity<>("ERROR", HttpStatus.BAD_REQUEST);
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> actualizarCliente(@PathVariable Integer id, @RequestBody Cliente c) {
        try {
            Cliente clienteNuevo = clienteService.actualizarCliente(id, c);
            return new ResponseEntity<>(clienteNuevo, HttpStatus.OK);
        } catch (RuntimeException e) {
            return new ResponseEntity<>("Cliente no encontrado", HttpStatus.NOT_FOUND);
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> eliminarCliente(@PathVariable Integer id) {
        String resultado = clienteService.eliminarCliente(id);

        if (resultado.contains("correctamente")) {
            return new ResponseEntity<>(resultado, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(resultado, HttpStatus.NOT_FOUND);
        }
    }

}