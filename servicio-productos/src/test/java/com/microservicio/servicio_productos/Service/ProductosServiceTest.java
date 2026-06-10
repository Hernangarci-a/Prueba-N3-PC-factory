package com.microservicio.servicio_productos.Service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;

import com.microservicio.servicio_productos.dto.ProductosDTO;
import com.microservicio.servicio_productos.model.Productos;
import com.microservicio.servicio_productos.repository.ProductosRepository;
import com.microservicio.servicio_productos.services.ProductosService;

import net.datafaker.Faker;

@ExtendWith(MockitoExtension.class)
public class ProductosServiceTest {

    @Mock
    private ProductosRepository productosRepository; // este es un clon del repositorio con ento testiamos sin tocar la
                                                     // BD real

    @InjectMocks
    private ProductosService productosService; // El servicio verdadero donde metere el repositorio simulado
    private Faker faker = new Faker(); // Generador de datos falsos aleatoriamente

    @BeforeEach
    void setUp() {
        // Inicializa los componentes de simulación antes de ejecutar cada prueba
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testBuscarPorId_Exitoso() {
        // Escenario donde preparamos un producto ficticio con datos falsos que se
        // generara aleatoriamente
        Productos productosFalso = new Productos();
        Integer idSimulado = 10;
        String nombreProductoFalso = faker.commerce().productName(); // la libreria de faker commerce sirve para asignar
                                                                     // nombres a los productos
        Double precioUnitarioFalso = faker.number().randomDouble(2, 5000, 1000000); // la libreria de number ayuda a
                                                                                    // buscar numeros duoble primero se
                                                                                    // coloca el total de decimales
                                                                                    // monto minimo y maximo que se
                                                                                    // generara
        String procesadorFlaso = faker.expression("Apple M2"); // la libreria de expression de faker me ayuda a colocar
                                                               // lo que quiera en string
        String almacenamientoFlaso = faker.expression("256GB SSD");

        productosFalso.setIdProductos(idSimulado);
        productosFalso.setNombreProducto(nombreProductoFalso);
        productosFalso.setPrecioUnitario(precioUnitarioFalso);
        productosFalso.setProcesador(procesadorFlaso);
        productosFalso.setAlmacenamiento(almacenamientoFlaso);

        when(productosRepository.findById(idSimulado)).thenReturn(Optional.of(productosFalso));

        ProductosDTO resultado = productosService.buscarPorId(idSimulado);
        // Entonces validamos que las compuertas de datos funcionen de forma idónea
        assertNotNull(resultado, "El DTO resultante no deberia ser nulo");
        assertEquals(productosFalso, resultado.getNombreProducto(),
                "El nombre transformado al DTO debe coincidir con el de la DB");
        assertEquals(productosFalso, resultado.getPrecioUnitario(),
                "El precio transformado al DTO debe coincidir con el de la DB");
        assertEquals(productosFalso, resultado.getProcesador(),
                "El procesador transformado al DTO debe coincidir con el de la DB");
        assertEquals(productosFalso, resultado.getAlmacenamiento(),
                "El nombre transformado al DTO debe coincidir con el de la DB");
        // Verificamos que el servicio realmente haya consultado al repositorio
        // exactamente 1 vez
        verify(productosRepository, times(1)).findById(idSimulado);
    }
}
