package com.microservicio.servicio_productos.services;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.microservicio.servicio_productos.dto.TipoProductoDTO;
import com.microservicio.servicio_productos.model.Productos;
import com.microservicio.servicio_productos.model.TipoProducto;
import com.microservicio.servicio_productos.repository.ProductosRepository;
import com.microservicio.servicio_productos.repository.TipoProductoRepository;

import jakarta.transaction.Transactional;

@Service
@Transactional
public class TipoProductoService {

    @Autowired
    private TipoProductoRepository tipoProductoRepository;

    @Autowired
    private ProductosRepository productosRepository;

    public List<TipoProductoDTO> obtenerTodos() {
        return tipoProductoRepository.findAll().stream()
                .map(this::convertirADTO)
                .toList();
    }

    public TipoProductoDTO buscarPorId(Integer idTipoProductos) {
        TipoProducto tipoProducto = tipoProductoRepository.findById(idTipoProductos)
                .orElseThrow(() -> new RuntimeException("producto no encontrado"));
        return convertirADTO(tipoProducto);
    }

    public TipoProducto guardarTipoProducto(TipoProducto tipoProducto) {
        if (tipoProducto.getNombreTipoProducto() == null || tipoProducto.getNombreTipoProducto().trim().isEmpty()) {
            throw new RuntimeException("Error el nombre no puede estar vacio");
        }
        return tipoProductoRepository.save(tipoProducto);
    }

    public String añadirTipoProductoAproductos(Integer idTipoProducto, Integer idProductos) {
        /*
         * TipoProducto tipoProducto = tipoProductoRepository.findById(idTipoProducto)
         * .orElseThrow(() -> new EntityNotFoundException("Categoría no encontrada"));
         * Productos producto = tipoProductoRepository.buscarPorId(idProductos);
         * categoria.getProductos().add(producto);
         * return tipoProductoRepository.save(categoria);
         */
        TipoProducto tipoProducto = tipoProductoRepository.findById(idTipoProducto)
                .orElseThrow(() -> new RuntimeException("Error: El tipo de producto no existe."));
        Productos producto = productosRepository.findById(idProductos)
                .orElseThrow(() -> new RuntimeException("Error: El producto no existe."));
        producto.setTipoProducto(tipoProducto);
        productosRepository.save(producto);

        return "El producto '" + producto.getNombreProducto() + "' es tipo  "
                + tipoProducto.getNombreTipoProducto();
    }

    private TipoProductoDTO convertirADTO(TipoProducto tipoProducto) {
        TipoProductoDTO dto = new TipoProductoDTO();
        dto.setId(tipoProducto.getIdTipoProducto());
        dto.setNombreTipoProducto(tipoProducto.getNombreTipoProducto());

        if (tipoProducto.getProductos() != null) {
            dto.setNombresProductos(tipoProducto.getProductos().stream()// abrimos la lista
                    .map(Productos::getNombreProducto)// de cada venta tomamos solo el folio
                    .toList());// volvemos a cerrar todo en una lista para el DTO
        } else {
            // Si no hay ventas devolvemos una lista vacía en vez de que devuleva un null
            dto.setNombresProductos(new ArrayList<>());
        }

        return dto;
    }

    public String eliminarProductos(Integer id) {
        // se usa try porque borrar algo es una proceso delicado puede fallar
        try {
            TipoProducto tipoProducto = tipoProductoRepository.findById(id).orElseThrow(
                    () -> new RuntimeException("¡Imposible eliminar! El tipoPorducto con ID " + id + " no existe."));
            tipoProductoRepository.delete(tipoProducto);
            return "El tipo " + tipoProducto.getNombreTipoProducto() + " a sido retirado";
        } catch (RuntimeException e) {
            return e.getMessage();
        }
    }
}
