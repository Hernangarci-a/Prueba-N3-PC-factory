package com.microservicio.servicio_ventas.service;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;
import java.util.List;
import java.util.Optional;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import com.microservicio.servicio_ventas.dto.TipoPagoDTO;
import com.microservicio.servicio_ventas.model.TipoPago;
import com.microservicio.servicio_ventas.repository.TipoPagoRepository;
import com.microservicio.servicio_ventas.services.TipoPagoService;
import net.datafaker.Faker;


@ExtendWith(MockitoExtension.class)
public class TipoPagoServiceTest {
    @Mock
    private TipoPagoRepository tipoPagoRepository;

    @InjectMocks
    private TipoPagoService tipoPagoService;

    private Faker faker;

    @BeforeEach
    void setUp(){
        faker = new Faker();
    }

    private String nombreTipoPagoAleatorio(){
        return faker.options().option("tarjeta","efectivo","transferencia","debito","credito");
    }

    @Test
    void buscarPorIdExiste(){
        Integer idFalso =faker.number().numberBetween(1,1000);
        String nombreFalso =nombreTipoPagoAleatorio();
        TipoPago tipoPago =new TipoPago();
        tipoPago.setIdTipoPago(idFalso);
        tipoPago.setNombreTipoPago(nombreFalso);
        when(tipoPagoRepository.findById(idFalso)).thenReturn(Optional.of(tipoPago));
        TipoPagoDTO resultado = tipoPagoService.buscarPorId(idFalso);
        assertNotNull(resultado);
        assertEquals(idFalso,resultado.getIdTipoPago());
        assertEquals(nombreFalso,resultado.getNombreTipoPago());
    }

    @Test
    void buscarPorIdInexistente(){
        when(tipoPagoRepository.findById(99)).thenReturn(Optional.empty());
        assertThrows(jakarta.persistence.EntityNotFoundException.class,()-> tipoPagoService.buscarPorId(99));
    }

    @Test
    void obtenerTodosConRegistros(){
        TipoPago tipoPago =new TipoPago();
        tipoPago.setIdTipoPago(faker.number().numberBetween(1,1000));
        tipoPago.setNombreTipoPago(nombreTipoPagoAleatorio());
        when(tipoPagoRepository.findAll()).thenReturn(List.of(tipoPago));
        List<TipoPagoDTO> resultado =tipoPagoService.obtenerTodos();
        assertNotNull(resultado);
        assertEquals(1,resultado.size());
        assertEquals(tipoPago.getNombreTipoPago(),resultado.get(0).getNombreTipoPago());
    }

    @Test
    void obtenerTodosListaVacia(){
        when(tipoPagoRepository.findAll()).thenReturn(List.of());
        List<TipoPagoDTO> resultado =tipoPagoService.obtenerTodos();
        assertNotNull(resultado);
        assertEquals(0,resultado.size());
    }
}