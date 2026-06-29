package com.microservicio.servicio_ventas.service;

import java.util.List;
import java.util.Optional;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import static org.mockito.ArgumentMatchers.any;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import org.mockito.junit.jupiter.MockitoExtension;
import com.microservicio.servicio_ventas.model.TipoDespacho;
import com.microservicio.servicio_ventas.repository.TipoDespachoRepository;
import com.microservicio.servicio_ventas.services.TipoDespachoService;
import net.datafaker.Faker;



@ExtendWith(MockitoExtension.class)
public class TipoDespachoServiceTest {
    @Mock
    private TipoDespachoRepository tipoDespachoRepository;
    @InjectMocks
    private TipoDespachoService tipoDespachoService;

    private Faker faker;

    @BeforeEach
    void setUp(){
        faker = new Faker();
    }

    private String nombreDespachoAleatorio(){
        return faker.options().option("delivery", "retiro en tienda", "despacho", "envio a domicilio");
    }

    @Test
    void encontrarTodoTipoDespacho(){
        TipoDespacho falso =new TipoDespacho();
        falso.setNombreTipoDespacho(nombreDespachoAleatorio());
        when(tipoDespachoRepository.findAll()).thenReturn(List.of(falso));
        List<TipoDespacho> tipos =tipoDespachoService.findAll();
        assertNotNull(tipos);
        assertEquals(1,tipos.size());
    }

    @Test
    void guardarTipoDespachoBien(){
        String nombreFake =nombreDespachoAleatorio();
        TipoDespacho tipo =new TipoDespacho();
        tipo.setNombreTipoDespacho(nombreFake);
        when(tipoDespachoRepository.save(any(TipoDespacho.class))).thenReturn(tipo);
        TipoDespacho resultado = tipoDespachoService.guardarTipoDespacho(tipo);
        assertNotNull(resultado);
        assertEquals(nombreFake,resultado.getNombreTipoDespacho());
    }

    @Test
    void noGuardarNombreVacio(){
        TipoDespacho tipoFalso =new TipoDespacho();
        tipoFalso.setNombreTipoDespacho("");
        RuntimeException exception = assertThrows(RuntimeException.class,()-> tipoDespachoService.guardarTipoDespacho(tipoFalso));
        assertEquals("Error el nombre no puede estar vacio",exception.getMessage());
    }

    @Test
    void buscarPorIdExistente(){
        Integer idFalso =faker.number().numberBetween(1,1000);
        TipoDespacho tipo =new TipoDespacho();
        tipo.setIdTipoDespacho(idFalso);
        tipo.setNombreTipoDespacho(nombreDespachoAleatorio());
        when(tipoDespachoRepository.findById(idFalso)).thenReturn(Optional.of(tipo));
        var resultado =tipoDespachoService.buscarPorId(idFalso);
        assertEquals(idFalso,resultado.getIdTipoDespacho());
    }

    @Test
    void eliminarTipoDespachoExistente(){
        Integer idFalso =faker.number().numberBetween(1,1000);
        TipoDespacho tipo =new TipoDespacho();
        tipo.setIdTipoDespacho(idFalso);
        when(tipoDespachoRepository.findById(idFalso)).thenReturn(Optional.of(tipo));
        String resultado =tipoDespachoService.eliminartipoDespacho(idFalso);
        verify(tipoDespachoRepository).delete(tipo);
        assertEquals("El tipo colaborador a sido eliminado",resultado);
    }

    @Test
    void actualizarTipoDespachoExistente(){
        TipoDespacho tipoExistente =new TipoDespacho();
        tipoExistente.setIdTipoDespacho(1);
        tipoExistente.setNombreTipoDespacho("delivery");
        String nombreFalso =nombreDespachoAleatorio();
        TipoDespacho datosFalso =new TipoDespacho();
        datosFalso.setNombreTipoDespacho(nombreFalso);
        when(tipoDespachoRepository.findById(1)).thenReturn(Optional.of(tipoExistente));
        when(tipoDespachoRepository.save(any(TipoDespacho.class))).thenReturn(tipoExistente);
        TipoDespacho resultado = tipoDespachoService.actualizarTipoDespacho(1,datosFalso);
        assertNotNull(resultado);
        assertEquals(nombreFalso,resultado.getNombreTipoDespacho());
        verify(tipoDespachoRepository).save(tipoExistente);
    }

    @Test
    void actualizarTipoDespachoInexistente(){
        TipoDespacho datosFalso =new TipoDespacho();
        datosFalso.setNombreTipoDespacho(nombreDespachoAleatorio());
        when(tipoDespachoRepository.findById(99)).thenReturn(Optional.empty());
        RuntimeException exception = assertThrows(RuntimeException.class,()-> tipoDespachoService.actualizarTipoDespacho(99, datosFalso));
        assertEquals("El despacho no existe ",exception.getMessage());
    }

    @Test
    void actualizarTipoDespachoNombreVacio(){
        TipoDespacho tipoExistente =new TipoDespacho();
        tipoExistente.setIdTipoDespacho(1);
        tipoExistente.setNombreTipoDespacho("delivery");
        TipoDespacho datosFalso =new TipoDespacho();
        datosFalso.setNombreTipoDespacho("   ");
        when(tipoDespachoRepository.findById(1)).thenReturn(Optional.of(tipoExistente));
        RuntimeException exception = assertThrows(RuntimeException.class,()-> tipoDespachoService.actualizarTipoDespacho(1, datosFalso));
        assertEquals("El nuevo nombre no puede estar vacio", exception.getMessage());
    }
}