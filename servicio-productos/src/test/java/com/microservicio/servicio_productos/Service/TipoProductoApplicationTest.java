package com.microservicio.servicio_productos.Service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;

import com.microservicio.servicio_productos.dto.TipoProductoDTO;
import com.microservicio.servicio_productos.model.TipoProducto;
import com.microservicio.servicio_productos.repository.TipoProductoRepository;
import com.microservicio.servicio_productos.services.TipoProductoService;

import net.datafaker.Faker;

@ExtendWith(MockitoExtension.class)
public class TipoProductoApplicationTest {

    @Mock
    private TipoProductoRepository tipoProductoRepository;

    @InjectMocks
    private TipoProductoService tipoProductoService;
    private Faker faker = new Faker();

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testBuscarPorId_Exitoso() {

        TipoProducto tipoProductofalso1 = new TipoProducto();
        Integer idSimulado1 = 1;
        String nombreTipoProductoFalso = "Computacion";

        tipoProductofalso1.setIdTipoProducto(idSimulado1);
        tipoProductofalso1.setNombreTipoProducto(nombreTipoProductoFalso);

        when(tipoProductoRepository.findById(idSimulado1)).thenReturn(Optional.of(tipoProductofalso1));

        TipoProductoDTO resultado = tipoProductoService.buscarPorId(idSimulado1);

        assertNotNull(resultado, "El DTO no deberia ser nulo ");
        assertEquals(nombreTipoProductoFalso, resultado.getNombreTipoProducto(),
                "El nombre transformado al DTO debe coincidir con el de la DB");

    }

    @Test
    void testObtenerTodos_Exitoso() {

        TipoProducto tipoProductofalso1 = new TipoProducto();
        Integer idSimulado1 = 1;
        String nombreTipoProductoFalso = "Componentes";

        tipoProductofalso1.setIdTipoProducto(idSimulado1);
        tipoProductofalso1.setNombreTipoProducto(nombreTipoProductoFalso);

        TipoProducto tipoProductofalso2 = new TipoProducto();
        Integer idSimulado2 = 2;
        String nombreTipoProductoFalso2 = "Perifericos";

        tipoProductofalso2.setIdTipoProducto(idSimulado2);
        tipoProductofalso2.setNombreTipoProducto(nombreTipoProductoFalso2);

        when(tipoProductoRepository.findAll()).thenReturn(List.of(tipoProductofalso1, tipoProductofalso2));

        List<TipoProductoDTO> resultado = tipoProductoService.obtenerTodos();

        assertNotNull(resultado);
        assertEquals(2, resultado.size(), "Deberia devolverme exactamente 2 categorias");

        verify(tipoProductoRepository, times(1)).findAll();
    }

    @Test
    void testGuardarTipoProducto() {

        TipoProducto tiporproductoGuardar = new TipoProducto();
        Integer idSimulado = 1;
        String nombreTipoProductoFalso = "Perifericos";

        tiporproductoGuardar.setIdTipoProducto(idSimulado);
        tiporproductoGuardar.setNombreTipoProducto(nombreTipoProductoFalso);

        when(tipoProductoRepository.save(tiporproductoGuardar)).thenReturn(tiporproductoGuardar);

        TipoProducto resultado = tipoProductoService.guardarTipoProducto(tiporproductoGuardar);

        assertNotNull(resultado, "El tipo de producto no puede estar vacio para guardarlo ");
        assertEquals(1, resultado.getIdTipoProducto());
        assertEquals(nombreTipoProductoFalso, resultado.getNombreTipoProducto());
        verify(tipoProductoRepository, times(1)).save(tiporproductoGuardar);
    }

    @Test
    void testEliminarTipoProducto() {

        TipoProducto tiporproductoEliminar = new TipoProducto();
        Integer idSimulado = 1;
        String nombreTipoProductoFalso = "Perifericos";

        tiporproductoEliminar.setIdTipoProducto(idSimulado);
        tiporproductoEliminar.setNombreTipoProducto(nombreTipoProductoFalso);

        when(tipoProductoRepository.findById(idSimulado)).thenReturn(Optional.of(tiporproductoEliminar));

        tipoProductoService.eliminarTipoProdcucto(idSimulado);

        verify(tipoProductoRepository, times(1)).delete(tiporproductoEliminar);
    }
}
