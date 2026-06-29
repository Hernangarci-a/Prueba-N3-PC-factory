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

import com.microservicio.servicio_productos.dto.CategoriaDTO;
import com.microservicio.servicio_productos.model.Categoria;
import com.microservicio.servicio_productos.repository.CategoriaRepository;
import com.microservicio.servicio_productos.services.CategoriaService;

import net.datafaker.Faker;

@ExtendWith(MockitoExtension.class)
public class CategoriaApplicationTest {

    @Mock
    private CategoriaRepository categoriaRepository;

    @InjectMocks
    private CategoriaService categoriaService;
    private Faker faker = new Faker();

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testBuscarPorId_Exitoso() {

        Categoria categoriaFalsa1 = new Categoria();
        Integer idSimulado1 = 1;
        String nombreCategoriaFalsa = faker.expression("Tarjetas de video");

        categoriaFalsa1.setIdCategoria(idSimulado1);
        categoriaFalsa1.setNombreCategoria(nombreCategoriaFalsa);

        when(categoriaRepository.findById(idSimulado1)).thenReturn(Optional.of(categoriaFalsa1));

        CategoriaDTO resultado = categoriaService.buscarPorId(idSimulado1);

        assertNotNull(resultado, "El DTO no deberia ser nulo ");
        assertEquals(nombreCategoriaFalsa, resultado.getNombreCategoria(),
                "El nombre transformado al DTO debe coincidir con el de la DB");

        verify(categoriaRepository, times(1)).findById(idSimulado1);

    }

    @Test
    void testObtenerTodos_Exitoso() {

        Categoria categoriaFalsa1 = new Categoria();
        Integer idSimulado1 = 7;
        String nombreCategoriaFalso = faker.expression("Notebook");

        categoriaFalsa1.setIdCategoria(idSimulado1);
        categoriaFalsa1.setNombreCategoria(nombreCategoriaFalso);

        Categoria categoriaFalsa2 = new Categoria();
        Integer idSimulado2 = 8;
        String nombreCategoriaFalso2 = faker.expression("Procesadores");

        categoriaFalsa2.setIdCategoria(idSimulado2);
        categoriaFalsa2.setNombreCategoria(nombreCategoriaFalso2);

        when(categoriaRepository.findAll()).thenReturn(List.of(categoriaFalsa1, categoriaFalsa2));

        List<CategoriaDTO> resultado = categoriaService.obtenerTodos();

        assertNotNull(resultado);
        assertEquals(2, resultado.size(), "Deberia devolverme exactamente 2 categorias");

        verify(categoriaRepository, times(1)).findAll();

    }

    @Test
    void testGuardarCategoria_Exitoso() {

        Categoria categoriaFalsa1 = new Categoria();
        Integer idSimulado1 = 1;
        String nombreCategoriaFalsa1 = faker.expression("Memorias ram");

        categoriaFalsa1.setIdCategoria(idSimulado1);
        categoriaFalsa1.setNombreCategoria(nombreCategoriaFalsa1);

        when(categoriaRepository.save(categoriaFalsa1)).thenReturn(categoriaFalsa1);

        Categoria resultado = categoriaService.guardarCategoria(categoriaFalsa1);

        assertNotNull(resultado);
        assertEquals(1, resultado.getIdCategoria());
        verify(categoriaRepository, times(1)).save(categoriaFalsa1);
    }

    @Test
    void testEliminarCategoria() {

        Categoria categoriaFalsa1 = new Categoria();
        Integer idSimulado1 = 1;
        String nombreCategoriaFalsa = "Almacenamiento SSD";

        categoriaFalsa1.setIdCategoria(idSimulado1);
        categoriaFalsa1.setNombreCategoria(nombreCategoriaFalsa);

        when(categoriaRepository.findById(idSimulado1)).thenReturn(Optional.of(categoriaFalsa1));

        categoriaService.eliminarCategoria(idSimulado1);

        verify(categoriaRepository, times(1)).delete(categoriaFalsa1);
    }

}
