package com.microservicio.servicio_productos.services;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatusCode;
import org.springframework.stereotype.Service;

import com.microservicio.servicio_productos.dto.ProductosDTO;
import com.microservicio.servicio_productos.model.Categoria;
import com.microservicio.servicio_productos.model.Productos;
import com.microservicio.servicio_productos.repository.ProductosRepository;

import jakarta.transaction.Transactional;
import reactor.core.publisher.Mono;

@Service
@Transactional
public class ProductosService {

    @Autowired
    private ProductosRepository productosRepository;

    // para obtener todos los productos de las base de datos
    public List<ProductosDTO> obtenerTodos() {
        return productosRepository.findAll().stream()
                .map(this::convertirADTO)
                .toList();
    }

    // este metodo es para buscar un producto expecifico con el ID
    public ProductosDTO buscarPorId(Integer idProductos) {
        Productos producto = productosRepository.findById(idProductos)
                .orElseThrow(() -> new RuntimeException("producto no encontrado"));
        return convertirADTO(producto);
    }

    public Productos buscarProducto(Integer id) {
        return productosRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("producto no encontrada"));
    }

    private ProductosDTO convertirADTO(Productos producto) {
        // crear caja vacia
        // creamos el DTO donde vamos a poner las cosas limpias.
        ProductosDTO dto = new ProductosDTO();
        // copiamos los atributos simples (los que son texto o números directos)
        dto.setIdProducto(producto.getIdProductos());
        dto.setNombreProducto(producto.getNombreProducto());
        dto.setPrecioUnitario(producto.getPrecioUnitario());
        dto.setProcesador(producto.getProcesador());
        dto.setMemoriaRam(producto.getMemoriaRam());
        dto.setAlmacenamiento(producto.getAlmacenamiento());

        // manejo de Tipo de Producto relación manytoone
        // uso un IF para evitar que el programa explote si el producto no tiene tipo
        // osea Preguntamos si el producto tiene un tipo por ejemplo un Mouse
        if (producto.getTipoProducto() != null) {
            // si existe entramos al objeto TipoProducto y sacamos solo el nombre
            dto.setNombreTipoProducto(producto.getTipoProducto().getNombreTipoProducto().trim());
        } else {
            // si es nulo, ponemos un mensaje en lugar de un error
            dto.setNombreTipoProducto("producto no tiene un tipo definido");
        }

        // manejo de Marca relacion manytoone
        if (producto.getMarca() != null) {
            // saco el nombre de la marca desde el objeto relacionado
            dto.setNombreMarca(producto.getMarca().getNombreMarca().trim());
        } else {
            dto.setNombreMarca("no hay marca para este producto");
        }

        // nota
        // .stream() es como abrir la lista de ventas
        // .map(Ventas::getFolio) es como sacar de cada venta solo el folio
        // .toList() es guardar todos esos folios en una lista nueva para el cliente

        if (producto.getCategorias() != null) {
            dto.setNombresCategorias(producto.getCategorias().stream()
                    .map(Categoria::getNombreCategoria)
                    .toList());
        } else {
            dto.setNombresCategorias(new ArrayList<>());
        }

        try {
            VentasDTO ventasRecuperado = webClientBuilder.build().get()
                    .uri("http://localhost:8083/api/v1/ventas/{id}" + ventas.getId())
                    .retrieve()
                    .onStatus(HttpStatusCode::is4xxClientError, response -> Mono.empty()) // importante
                    .bodyToMono(VentasDTO.class)
                    .block();

            dto.setVentas(ventasRecuperado);

        } catch (Exception e) {
            dto.setVentas(null);
        }
        // y aca se entrega DTO ya armado con toda la información filtrada caja
        // terminada

        return dto;
    }

    // para guardar los productos
    public Productos guardarProductos(Productos producto) {
        // validaciones de nombres
        if (producto.getNombreProducto() == null || producto.getNombreProducto().trim().isEmpty()) {
            // throw significa lanzar o reportar un error
            // y new se usa para crear un objeto son objetos que se guardan informacion
            // sobre que fallo
            // el RuntimeException lo que hace es frenar el codigo en ejecucion y coloca un
            // mensaje explicativo

            throw new RuntimeException("Error el nombre no puede estar vacio");
        }
        if (producto.getPrecioUnitario() <= 0.0) {
            throw new RuntimeException("Error el precio no puede ser menor o igual a zero");
        }
        // si pasa las validaciones guarda
        return productosRepository.save(producto);

    }

    // para eliminar un producto
    public String eliminarProductos(Integer id) {
        // se usa try porque borrar algo es una proceso delicado puede fallar
        try {
            // busco el producto en la base de datos usando su ID
            // .orElseThrow() es un freno de mano si el ID no existe
            // se lanza un error la excepción personalizada y el código se detiene aquí
            Productos producto = productosRepository.findById(id).orElseThrow(
                    () -> new RuntimeException("¡Imposible eliminar! El producto con ID " + id + " no existe."));
            productosRepository.delete(producto);
            return "El producto " + producto.getNombreProducto() + " a sido retirado";

        } catch (RuntimeException e) {
            // si algo sale mal con id y no existe
            // el programa salta directamente aquí y nos devuelve el texto de error
            return e.getMessage();
        }
    }

    public Productos actualizarProductos(Integer id, Productos producto) {
        // busco el original en la base de datos el producto1 el que el usuario no a
        // modificado
        Productos producto1 = productosRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("El producto no existe en el catalogo"));
        // solo se cambia lo que el usuario mandó
        // si el nombre no viene vacío, actualizamos el original con el dato nuevo
        if (producto.getNombreProducto() != null) {
            // Primero limpiamos el texto de espacios extras y lo guardamos en una variable
            String nombreSinEspacios = producto.getNombreProducto().trim();
            // se valida usando la variable que ya está sin espacios que debe ser mayor a 3
            // caracteres
            if (nombreSinEspacios.length() < 3) {
                throw new RuntimeException("El nuevo nombre es muy corto, debe tener mas 3 caracteres");
            }
            // aqui se guarda la variable sin sin espacios
            producto1.setNombreProducto(nombreSinEspacios);
        }
        // manejo del primitivo 'double'
        // como no puede ser null verificamos que no sea 0.0 es double
        if (producto.getPrecioUnitario() != 0.0) {
            if (producto.getPrecioUnitario() <= 0.0) {
                throw new RuntimeException("Error el precio de ser mayor a zero");
            }
            producto1.setPrecioUnitario(producto.getPrecioUnitario());
        }
        if (producto.getProcesador() != null) {
            producto1.setProcesador(producto.getProcesador());
        }
        if (producto.getMemoriaRam() != null) {
            producto1.setMemoriaRam(producto.getMemoriaRam());
        }
        if (producto.getAlmacenamiento() != null) {
            producto1.setAlmacenamiento(producto.getAlmacenamiento());
        }
        // guarda el original los cambios del original que ya existe
        return productosRepository.save(producto1);
    }
}
