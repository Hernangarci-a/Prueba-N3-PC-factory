package com.microservicio.servicio_productos.Service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.doNothing;
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

import com.microservicio.servicio_productos.dto.ProductosDTO;
import com.microservicio.servicio_productos.model.Productos;
import com.microservicio.servicio_productos.repository.ProductosRepository;
import com.microservicio.servicio_productos.services.ProductosService;

import net.datafaker.Faker;

@ExtendWith(MockitoExtension.class) // Extiende este test usando la extensión de Mockito, Le avisa a JUnit (el motor
                                    // que corre los tests) que vas a usar simuladores virtuales los @Mock y
                                    // @InjectMocks
public class ProductosApplicationTest {

    @Mock // simulacion
    private ProductosRepository productosRepository; // este es un clon del repositorio con ento testiamos sin tocar
                                                     // la
                                                     // BD real
    @InjectMocks // Inyectar las simulaciones aquí
    private ProductosService productosService; // El servicio verdadero donde metere el repositorio simulado
    private Faker faker = new Faker(); // Generador de datos falsos aleatoriamente

    @BeforeEach // esta es la anotacion Junit significa Antes de cada uno
    void setUp() { // configurar o preparar
        // Inicializa los componentes de simulación antes de ejecutar cada prueba
        MockitoAnnotations.openMocks(this);
    }

    @Test // esta notacion significa que esto es una prueba
    void testBuscarPorId_Exitoso() { // void significa cuando termine el test de ejecutarse no tiene que devolver
                                     // nada el test solo se evalua y muere ay
        // Escenario donde preparamos un producto ficticio con datos falsos que se
        // generara aleatoriamente
        Productos productosFalso = new Productos();
        Integer idSimulado = 10;
        String nombreProductoFalso = faker.commerce().productName(); // la libreria de faker commerce sirve para
                                                                     // asignar
                                                                     // nombres a los productos
        Double precioUnitarioFalso = faker.number().randomDouble(2, 5000, 1000000); // la libreria de number
                                                                                    // ayuda a
                                                                                    // buscar numeros duoble
                                                                                    // primero se
                                                                                    // coloca el total de
                                                                                    // decimales
                                                                                    // monto minimo y maximo que
                                                                                    // se
                                                                                    // generara
        String procesadorFlaso = "Apple M2"; // la libreria de expression de faker me ayuda a
                                             // colocar
                                             // lo que quiera en string
        String almacenamientoFlaso = "256GB SSD";

        productosFalso.setIdProductos(idSimulado);
        productosFalso.setNombreProducto(nombreProductoFalso);
        productosFalso.setPrecioUnitario(precioUnitarioFalso);
        productosFalso.setProcesador(procesadorFlaso);
        productosFalso.setAlmacenamiento(almacenamientoFlaso);

        when(productosRepository.findById(idSimulado)).thenReturn(Optional.of(productosFalso));

        ProductosDTO resultado = productosService.buscarPorId(idSimulado);
        // Entonces validamos que las compuertas de datos funcionen de forma idónea
        assertNotNull(resultado, "El DTO resultante no deberia ser nulo");
        assertEquals(nombreProductoFalso, resultado.getNombreProducto(),
                "El nombre transformado al DTO debe coincidir con el de la DB");
        assertEquals(precioUnitarioFalso, resultado.getPrecioUnitario(),
                "El precio transformado al DTO debe coincidir con el de la DB");
        assertEquals(procesadorFlaso, resultado.getProcesador(),
                "El procesador transformado al DTO debe coincidir con el de la DB");
        assertEquals(almacenamientoFlaso, resultado.getAlmacenamiento(),
                "El nombre transformado al DTO debe coincidir con el de la DB");
        // Verificamos que el servicio realmente haya consultado al repositorio
        // exactamente 1 vez
        verify(productosRepository, times(1)).findById(idSimulado);
    }

    @Test
    void testObtenerTodos_Exitoso() {
        // se crea dos productos ficticios usando Faker
        Productos producto2 = new Productos();
        producto2.setIdProductos(1);
        producto2.setNombreProducto(faker.commerce().productName());
        producto2.setPrecioUnitario(faker.number().randomDouble(2, 100, 500));

        Productos porducto3 = new Productos();
        porducto3.setIdProductos(2);
        porducto3.setNombreProducto(faker.commerce().productName());
        porducto3.setPrecioUnitario(faker.number().randomDouble(2, 600, 1200));

        // se usa el repositorio simulado el mock
        // Le decimos: "Cuando el código del servicio llame a findAll no se va al MySQL
        // en su lugar, devuelve una Lista fija que contenga a producto2 y porducto3
        when(productosRepository.findAll()).thenReturn(List.of(producto2, porducto3));

        // en el when se coloca el metedo que se evaluaria del service y se usa una
        // lista para obtener los resultados
        List<ProductosDTO> resultado = productosService.obtenerTodos();

        // se evalua que la lista contenga los elementos correctos
        assertNotNull(resultado);
        assertEquals(2, resultado.size(), "Debería devolverme exactamente 2 productos");
        // Aqui se le pregunta al simulador si el método findAll
        // fue ejecutado exactamente una sola vez (times(1)) significa una vez durante
        // toda la prueba
        verify(productosRepository, times(1)).findAll();
    }

    @Test
    void testGuardarProductos_Exitoso() {

        String nombreFalso = faker.commerce().productName();
        Double precioFalso = faker.number().randomDouble(2, 100, 1000);

        Productos productoGuardado = new Productos();
        productoGuardado.setIdProductos(15); // La BD ficticia se asigna ID numero15
        productoGuardado.setNombreProducto(nombreFalso);
        productoGuardado.setPrecioUnitario(precioFalso);

        when(productosRepository.save(productoGuardado)).thenReturn(productoGuardado);

        Productos resultado = productosService.guardarProductos(productoGuardado);

        assertNotNull(resultado);
        assertEquals(15, resultado.getIdProductos());
        verify(productosRepository, times(1)).save(productoGuardado);
    }

    @Test
    void testEliminarProductos_Exitoso() {
        Integer idAEliminar = 12;
        Productos productoExistente = new Productos();
        Double precioFalso = faker.number().randomDouble(2, 100, 1000);
        String procesadorFlaso = "Apple M2";
        String memoriaranFalso = "8GB";
        String almacenamientoFlaso = "256GB SSD";

        productoExistente.setIdProductos(idAEliminar);
        productoExistente.setNombreProducto("Teclados Mecánico");
        productoExistente.setPrecioUnitario(precioFalso);
        productoExistente.setProcesador(procesadorFlaso);
        productoExistente.setMemoriaRam(memoriaranFalso);
        productoExistente.setAlmacenamiento(almacenamientoFlaso);

        when(productosRepository.findById(idAEliminar)).thenReturn(Optional.of(productoExistente));

        // Ejecutamos la eliminación
        productosService.eliminarProductos(idAEliminar);

        // se comprueba que se llamo el repositorio para eliminar producto
        verify(productosRepository, times(1)).delete(productoExistente);
    }

}
