package com.microservicio.servicio_ventas.service;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import java.time.LocalDateTime;
import java.util.Optional;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import com.microservicio.servicio_ventas.dto.VentasDTO;
import com.microservicio.servicio_ventas.model.Ventas;
import com.microservicio.servicio_ventas.repository.VentasRepository;
import com.microservicio.servicio_ventas.services.VentasService;
import jakarta.persistence.EntityNotFoundException;
import net.datafaker.Faker;



@ExtendWith(MockitoExtension.class)
public class VentasServiceTest {

    @Mock
    private VentasRepository ventasRepository;

    @InjectMocks
    private VentasService ventasService;

    private Faker faker;

    @BeforeEach
    void setUp(){
        faker = new Faker();
    }

    @Test
    void guardarVentaBien(){
        Ventas venta =new Ventas();
        venta.setIdVenta(faker.number().numberBetween(1,1000));
        venta.setFolio(faker.number().numberBetween(1,9999));
        venta.setFecha(LocalDateTime.now());
        venta.setTotal(faker.number().randomDouble(2,100,500000));
        when(ventasRepository.save(any(Ventas.class))).thenReturn(venta);
        Ventas resultado = ventasService.guardarVenta(venta);
        assertNotNull(resultado);
        assertEquals(venta.getIdVenta(),resultado.getIdVenta());
    }

    @Test
    void noGuardarVentaSinId(){
        Ventas venta =new Ventas();
        venta.setIdVenta(null);
        RuntimeException exception = assertThrows(RuntimeException.class,()-> ventasService.guardarVenta(venta));
        assertEquals("Error el el producto no debe estar vacio",exception.getMessage());
    }

    @Test
    void buscarPorIdExistente(){
        Integer idFalso =faker.number().numberBetween(1,1000);
        Ventas venta = new Ventas();
        venta.setIdVenta(idFalso);
        venta.setFecha(LocalDateTime.now());
        venta.setTotal(faker.number().randomDouble(2,100,500000));
        when(ventasRepository.findById(idFalso)).thenReturn(Optional.of(venta));
        VentasDTO resultado = ventasService.buscarPorId(idFalso);
        assertNotNull(resultado);
        assertEquals(idFalso, resultado.getIdVenta());
    }

    @Test
    void buscarPorIdInexistente(){
        when(ventasRepository.findById(99)).thenReturn(Optional.empty());
        assertThrows(EntityNotFoundException.class, ()-> ventasService.buscarPorId(99));
    }

    @Test
    void eliminarVentaExistente(){
        Integer idFalso =faker.number().numberBetween(1,1000);
        Ventas venta =new Ventas();
        venta.setIdVenta(idFalso);
        when(ventasRepository.findById(idFalso)).thenReturn(Optional.of(venta));
        String resultado = ventasService.eliminarVenta(idFalso);
        verify(ventasRepository).delete(venta);
        assertEquals("La venta " + idFalso + " se elimino",resultado);
    }

    @Test
    void actualizarVentaExistente(){
        Ventas ventaExistente =new Ventas();
        ventaExistente.setIdVenta(1);
        ventaExistente.setFolio(faker.number().numberBetween(1,999));
        ventaExistente.setTotal(faker.number().randomDouble(2,100,500000));
        Ventas datosFalsos =new Ventas();
        int folioFalso =faker.number().numberBetween(1000,9999);
        double nuevoTotal =faker.number().randomDouble(2,100,500000);
        datosFalsos.setFolio(folioFalso);
        datosFalsos.setTotal(nuevoTotal);
        when(ventasRepository.findById(1)).thenReturn(Optional.of(ventaExistente));
        when(ventasRepository.save(any(Ventas.class))).thenReturn(ventaExistente);
        Ventas resultado = ventasService.actualizarVenta(1,datosFalsos);
        assertNotNull(resultado);
        assertEquals(folioFalso,resultado.getFolio());
        assertEquals(nuevoTotal,resultado.getTotal());
        verify(ventasRepository).save(ventaExistente);
    }

    @Test
    void actualizarVentaInexistente(){
        Ventas datosFalsos =new Ventas();
        datosFalsos.setFolio(faker.number().numberBetween(1,999));
        when(ventasRepository.findById(99)).thenReturn(Optional.empty());
        RuntimeException exception = assertThrows(RuntimeException.class,()-> ventasService.actualizarVenta(99, datosFalsos));
        assertEquals("El producto no existe en el catalogo",exception.getMessage());
    }
}