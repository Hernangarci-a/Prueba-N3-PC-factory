package com.microservicio.servicio_ventas.controller;

import java.util.List;
import java.util.stream.Collectors;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.MediaTypes;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.microservicio.servicio_ventas.Assembler.TipoDespachoModelAssembler;
import com.microservicio.servicio_ventas.model.TipoDespacho;
import com.microservicio.servicio_ventas.services.TipoDespachoService;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;



@Slf4j
@RestController("tipoDespachoControllerV2")
@RequestMapping("api/v2/tipodespacho")
public class TipoDespachoControllerV2 {

    @Autowired
    private TipoDespachoService tipoDespachoService;

    @Autowired
    private TipoDespachoModelAssembler assembler;

    @GetMapping(produces = MediaTypes.HAL_JSON_VALUE)
    public ResponseEntity<CollectionModel<EntityModel<TipoDespacho>>>getTipoDespacho(){
        List<EntityModel<TipoDespacho>> tipoDespacho =tipoDespachoService.findAll().stream()
                .map(assembler::toModel)
                .collect(Collectors.toList());

        if(tipoDespacho.isEmpty()){
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok(CollectionModel.of(tipoDespacho,
                linkTo(methodOn(TipoDespachoControllerV2.class).getTipoDespacho()).withSelfRel()));
    }

    @GetMapping(value = "/{id}",produces =MediaTypes.HAL_JSON_VALUE)
    public ResponseEntity<EntityModel<TipoDespacho>> porId(@PathVariable Integer id){
        try{
            TipoDespacho tipo =tipoDespachoService.findById(id);
            return ResponseEntity.ok(assembler.toModel(tipo));
        }catch (RuntimeException e){
            return ResponseEntity.notFound().build();
        }
    }

    @PostMapping(produces = MediaTypes.HAL_JSON_VALUE)
    public ResponseEntity<EntityModel<TipoDespacho>> registrar(@Valid @RequestBody TipoDespacho tipoDespacho){
        try{
            TipoDespacho nuevo = tipoDespachoService.guardarTipoDespacho(tipoDespacho);
            return ResponseEntity
                    .created(linkTo(methodOn(TipoDespachoControllerV2.class).porId(nuevo.getIdTipoDespacho())).toUri())
                    .body(assembler.toModel(nuevo));
        }catch (Exception e){
            return ResponseEntity.badRequest().build();
        }
    }
}