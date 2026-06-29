package com.microservicio.servicio_productos.Service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.timeout;
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

import com.microservicio.servicio_productos.dto.MarcaDTO;
import com.microservicio.servicio_productos.model.Marca;
import com.microservicio.servicio_productos.repository.MarcaRepository;
import com.microservicio.servicio_productos.services.MarcaService;

import net.datafaker.Faker;

@ExtendWith(MockitoExtension.class)
public class MarcaApplicationTest {

    @Mock
    private MarcaRepository marcaRepository;

    @InjectMocks
    private MarcaService marcaService;
    private Faker faker = new Faker();

    @BeforeEach // esta es la anotacion Junit significa Antes de cada uno
    void setUp() { // configurar o preparar
        // Inicializa los componentes de simulación antes de ejecutar cada prueba
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testBuscarPorId_Exitoso() {

        Marca marcaFalso = new Marca();
        Integer idSimulado = 3;
        String nombreMarcaFalsa = faker.company().name();

        marcaFalso.setIdMarca(idSimulado);
        marcaFalso.setNombreMarca(nombreMarcaFalsa);

        when(marcaRepository.findById(idSimulado)).thenReturn(Optional.of(marcaFalso));

        MarcaDTO resultado = marcaService.buscarPorId(idSimulado);

        assertNotNull(resultado, "El DTO no deberia ser nulo ");
        assertEquals(nombreMarcaFalsa, resultado.getNombreMarca(),
                "El nombre transformado al DTO debe coincidir con el de la DB");

        verify(marcaRepository, times(1)).findById(idSimulado);

    }

    @Test
    void testObtenerTodos_Exitoso() {
        // se crea dos productos ficticios usando Faker
        Marca marcafalso1 = new Marca();
        Integer idSimulado1 = 8;
        String nombreMarcaFalso = faker.expression("AMD");

        marcafalso1.setIdMarca(idSimulado1);
        marcafalso1.setNombreMarca(nombreMarcaFalso);

        Marca marcafalso2 = new Marca();
        Integer idSimulado2 = 9;

        marcafalso2.setIdMarca(idSimulado2);
        marcafalso2.setNombreMarca(nombreMarcaFalso);

        when(marcaRepository.findAll()).thenReturn(List.of(marcafalso1, marcafalso2));

        // en el when se coloca el metedo que se evaluaria del service y se usa una
        // lista para obtener los resultados
        List<MarcaDTO> resultado = marcaService.obtenerTodos();

        // se evalua que la lista contenga los elementos correctos
        assertNotNull(resultado);
        assertEquals(2, resultado.size(), "Debería devolverme exactamente 2 productos");
        // Aqui se le pregunta al simulador si el método findAll
        // fue ejecutado exactamente una sola vez (times(1)) significa una vez durante
        // toda la prueba
        verify(marcaRepository, times(1)).findAll();
    }

    @Test
    void testGuardarMarca() {

        Marca marcafalsaGuardado = new Marca();
        Integer idSimulado1 = 1;
        String nombreMarcaFalsa1 = "Samsung";

        marcafalsaGuardado.setIdMarca(idSimulado1);
        marcafalsaGuardado.setNombreMarca(nombreMarcaFalsa1);

        when(marcaRepository.save(marcafalsaGuardado)).thenReturn(marcafalsaGuardado);

        Marca resultado = marcaService.guardarMarca(marcafalsaGuardado);

        assertNotNull(resultado, "La marca no puede estar vacio para el guadado ");
        assertEquals(idSimulado1, resultado.getIdMarca());
        assertEquals(nombreMarcaFalsa1, resultado.getNombreMarca());

        verify(marcaRepository, times(1)).save(marcafalsaGuardado);
    }

    @Test
    void testEliminarMarca() {

        Marca marcafalsaEliminar = new Marca();
        Integer idSimulado = 1;
        String nombreMarcaFalsa = "HP";

        marcafalsaEliminar.setIdMarca(idSimulado);
        marcafalsaEliminar.setNombreMarca(nombreMarcaFalsa);

        when(marcaRepository.findById(idSimulado)).thenReturn(Optional.of(marcafalsaEliminar));

        marcaService.eliminarMarca(idSimulado);

        verify(marcaRepository, timeout(1)).delete(marcafalsaEliminar);

    }

}
