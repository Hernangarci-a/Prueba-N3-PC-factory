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
import com.microservicio.servicio_ventas.dto.TipoVentaDTO;
import com.microservicio.servicio_ventas.model.TipoVenta;
import com.microservicio.servicio_ventas.repository.TipoVentaRepository;
import com.microservicio.servicio_ventas.services.TipoVentaService;
import jakarta.persistence.EntityNotFoundException;
import net.datafaker.Faker;



@ExtendWith(MockitoExtension.class)
public class TipoVentaServiceTest {

    @Mock
    private TipoVentaRepository tipoVentaRepository;

    @InjectMocks
    private TipoVentaService tipoVentaService;

    private Faker faker;

    @BeforeEach
    void setUp(){
        faker = new Faker();
    }

    private String nombreTipoVentaAleatorio(){
        return faker.options().option("presencial","en linea","telefonica","mayorista");
    }

    @Test
    void buscarPorIdExistente() {
        Integer idFalso =faker.number().numberBetween(1,1000);
        String nombreFalso =nombreTipoVentaAleatorio();
        TipoVenta tipoVenta =new TipoVenta();
        tipoVenta.setIdTipoVenta(idFalso);
        tipoVenta.setNombre(nombreFalso);
        when(tipoVentaRepository.findById(idFalso)).thenReturn(Optional.of(tipoVenta));
        TipoVentaDTO resultado =tipoVentaService.buscarPorId(idFalso);
        assertNotNull(resultado);
        assertEquals(idFalso,resultado.getIdTipoVenta());
        assertEquals(nombreFalso,resultado.getNombre());
    }

    @Test
    void buscarPorIdInexistente(){
        when(tipoVentaRepository.findById(99)).thenReturn(Optional.empty());
        assertThrows(EntityNotFoundException.class, ()-> tipoVentaService.buscarPorId(99));
    }

    @Test
    void obtenerTodosConRegistros(){
        TipoVenta tipoVenta =new TipoVenta();
        tipoVenta.setIdTipoVenta(faker.number().numberBetween(1,1000));
        tipoVenta.setNombre(nombreTipoVentaAleatorio());
        when(tipoVentaRepository.findAll()).thenReturn(List.of(tipoVenta));
        List<TipoVentaDTO> resultado =tipoVentaService.obtenerTodos();
        assertNotNull(resultado);
        assertEquals(1,resultado.size());
        assertEquals(tipoVenta.getNombre(),resultado.get(0).getNombre());
    }

    @Test
    void obtenerTodosListaVacia(){
        when(tipoVentaRepository.findAll()).thenReturn(List.of());
        List<TipoVentaDTO> resultado =tipoVentaService.obtenerTodos();
        assertNotNull(resultado);
        assertEquals(0,resultado.size());
    }
}