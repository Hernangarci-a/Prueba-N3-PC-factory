package com.microservicio.servicio_usuarios.controller.v2;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.MediaTypes;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.microservicio.servicio_usuarios.assemblers.ClienteModelAssembler;
import com.microservicio.servicio_usuarios.dto.ClienteDTO;
import com.microservicio.servicio_usuarios.model.Cliente;
import com.microservicio.servicio_usuarios.services.ClienteService;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@RestController
@RequestMapping("api/v2/pcfactory/clientes")
public class ClienteControllerV2 {
    @Autowired
    private ClienteService clienteService;

    @Autowired
    private ClienteModelAssembler assembler;

    @GetMapping(produces = MediaTypes.HAL_JSON_VALUE)
    public ResponseEntity<CollectionModel<EntityModel<ClienteDTO>>> getAllClientes() {
        List<EntityModel<ClienteDTO>> clientes = clienteService.obtenerTodos().stream()
                .map(assembler::toModel)
                .collect(Collectors.toList());

        if (clientes.isEmpty()) {
            return ResponseEntity.noContent().build();
        }

        return ResponseEntity.ok(CollectionModel.of(
                clientes,
                linkTo(methodOn(ClienteControllerV2.class).getAllClientes()).withSelfRel()));
    }

    @GetMapping(value = "/{codigo}", produces = MediaTypes.HAL_JSON_VALUE)
    public ResponseEntity<EntityModel<ClienteDTO>> getClienteByCodigo(@PathVariable Integer codigo) {
        ClienteDTO cliente = clienteService.buscarPorId(codigo);
        if (cliente == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(assembler.toModel(cliente));
    }

    @PostMapping(produces = MediaTypes.HAL_JSON_VALUE)
    public ResponseEntity<EntityModel<ClienteDTO>> addCliente(@RequestBody ClienteDTO cliente) {
        ClienteDTO clienteNuevo = clienteService.guardarCliente(cliente);
        return ResponseEntity
                .created(linkTo(methodOn(ClienteControllerV2.class).getClienteByCodigo(clienteNuevo.getIdCliente()))
                        .toUri())
                .body(assembler.toModel(clienteNuevo));
    }

    @PutMapping(value = "/{codigo}", produces = MediaTypes.HAL_JSON_VALUE)
    public ResponseEntity<EntityModel<ClienteDTO>> updateCliente(@PathVariable Integer codigo,
            @RequestBody ClienteDTO cliente) {
        Cliente c = clienteService.convertirCliente(cliente);
        ClienteDTO updatedCliente = clienteService.actualizarCliente(codigo, c);
        return ResponseEntity.ok(assembler.toModel(updatedCliente));
    }

    @DeleteMapping(value = "/{codigo}", produces = MediaTypes.HAL_JSON_VALUE)
    public ResponseEntity<Void> deleteCliente(@PathVariable Integer codigo) {
        ClienteDTO cliente = clienteService.buscarPorId(codigo);
        if (cliente == null) {
            return ResponseEntity.notFound().build();
        }
        clienteService.eliminarCliente(codigo);
        return ResponseEntity.noContent().build();
    }

}