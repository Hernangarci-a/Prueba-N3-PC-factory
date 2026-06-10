package com.microservicio.servicio_productos.services;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.microservicio.servicio_productos.dto.CategoriaDTO;
import com.microservicio.servicio_productos.model.Categoria;
import com.microservicio.servicio_productos.model.Productos;
import com.microservicio.servicio_productos.repository.CategoriaRepository;

import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import lombok.extern.slf4j.Slf4j;

@Service
@Transactional
@Slf4j
public class CategoriaService {

    @Autowired
    private CategoriaRepository categoriaRepository;

    @Autowired
    private ProductosService productosService;

    // para obtener todos los productos de las base de datos
    public List<CategoriaDTO> obtenerTodos() {
        log.info("buscando el listado completo de categorías ");
        return categoriaRepository.findAll().stream()
                .map(this::convertirADTO)
                .toList();
    }

    public CategoriaDTO buscarPorId(Integer idCategoria) {
        log.info("Buscando categoría con ID: {}", idCategoria);
        Categoria categoria = categoriaRepository.findById(idCategoria)
                .orElseThrow(() -> new RuntimeException("categoria no encontrada"));
        return convertirADTO(categoria);
    }

    public Categoria guardarCategoria(Categoria categoria) {
        if (categoria.getNombreCategoria() == null || categoria.getNombreCategoria().trim().isEmpty()) {
            log.warn("Intento fallido de guardar categoría El nombre está vacío.");
            throw new RuntimeException("Error el nombre no puede estar vacio");
        }
        log.info("Guardando nueva categoría: '{}'", categoria.getNombreCategoria());
        return categoriaRepository.save(categoria);
    }

    public Categoria añadirProductoACategoria(Integer idCategoria, Integer idProductos) {
        log.info("Asociando producto ID {} a la categoría ID {}", idProductos, idCategoria);

        Categoria categoria = categoriaRepository.findById(idCategoria)
                .orElseThrow(() -> new EntityNotFoundException("Categoría no encontrada"));
        Productos producto = productosService.buscarProducto(idProductos);
        categoria.getProductos().add(producto);
        return categoriaRepository.save(categoria);
    }

    private CategoriaDTO convertirADTO(Categoria categoria) {
        CategoriaDTO dto = new CategoriaDTO();
        // copiamos los atributos simples (los que son texto o números directos)
        dto.setIdCategoria(categoria.getIdCategoria());
        dto.setNombreCategoria(categoria.getNombreCategoria());

        if (categoria.getProductos() != null) {
            dto.setNombresProductos(categoria.getProductos().stream().map(Productos::getNombreProducto)
                    .toList());
        } else {
            dto.setNombresProductos(new ArrayList<>());
        }

        return dto;
    }

    public String eliminarCategoria(Integer id) {
        log.info("Iniciando eliminación para la categoría ID: {}", id);
        try {
            Categoria categoria = categoriaRepository.findById(id).orElseThrow(
                    () -> new RuntimeException("¡Imposible eliminar! El producto con ID " + id + " no existe."));
            categoriaRepository.delete(categoria);
            return "El producto " + categoria.getNombreCategoria() + " a sido retirado";

        } catch (RuntimeException e) {
            return e.getMessage();
        }
    }

}
