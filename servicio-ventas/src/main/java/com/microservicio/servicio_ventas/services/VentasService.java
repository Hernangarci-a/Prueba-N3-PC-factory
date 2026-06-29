package com.microservicio.servicio_ventas.services;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatusCode;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import com.microservicio.servicio_ventas.dto.ClienteExternoDTO;
import com.microservicio.servicio_ventas.dto.VentasDTO;
import com.microservicio.servicio_ventas.model.Ventas;
import com.microservicio.servicio_ventas.repository.VentasRepository;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import reactor.core.publisher.Mono;



@Service
@Transactional
public class VentasService {

    @Autowired
    private VentasRepository ventasRepository;

    @Autowired
    private WebClient.Builder webClientBuilder;

    public List<VentasDTO> obtenerTodos(){
        return ventasRepository.findAll().stream()
                .map(this::convertirADTO)
                .toList();
    }

    public VentasDTO buscarPorId(Integer id){
        Ventas venta = ventasRepository.findById(id).orElseThrow(()-> new EntityNotFoundException("no se encontro la venta con ID: " + id));
        return convertirADTO(venta);
    }

    public Ventas guardarVenta(Ventas venta){
        if (venta.getIdVenta() == null) {
            throw new RuntimeException("Error el el producto no debe estar vacio");
        }
        return ventasRepository.save(venta);
    }

    public String eliminarVenta(Integer id){
        try{
            Ventas venta = ventasRepository.findById(id)
                    .orElseThrow(() -> new RuntimeException("no se puede la venta con ID " + id + " no existe"));
            ventasRepository.delete(venta);
            return "La venta " + venta.getIdVenta() + " se elimino";
        } catch (RuntimeException e){
            return e.getMessage();
        }
    }

    public Ventas actualizarVenta(Integer id, Ventas ventas){
        Ventas vent = ventasRepository.findById(id).orElseThrow(()-> new RuntimeException("El producto no existe en el catalogo"));
        if (ventas.getFolio() != 0) vent.setFolio(ventas.getFolio());
        if (ventas.getFecha() != null) vent.setFecha(ventas.getFecha());
        if (ventas.getTotal() != 0.0) vent.setTotal(ventas.getTotal());
        return ventasRepository.save(vent);
    }

    public ClienteExternoDTO obtenerCliente(Integer id){
        ClienteExternoDTO fallback = new ClienteExternoDTO();
        try{
            ClienteExternoDTO resultado = webClientBuilder.build()
                    .get()
                    .uri("http://servicio-usuarios/api/v1/cliente/" + id)
                    .retrieve()
                    .onStatus(HttpStatusCode::is4xxClientError, response -> Mono.empty())
                    .bodyToMono(ClienteExternoDTO.class)
                    .block();
            if (resultado != null) return resultado;
            fallback.setId(id);
            fallback.setNombre("Cliente no encontrado");
            fallback.setEmail("sin email");
            return fallback;
        } catch (Exception e){
            fallback.setId(id);
            fallback.setNombre("No se pudo conectar con servicio-usuarios");
            fallback.setEmail("sin email");
            return fallback;
        }
    }

    private VentasDTO convertirADTO(Ventas venta){
        VentasDTO dto = new VentasDTO();
        dto.setIdVenta(venta.getIdVenta());
        dto.setFecha(venta.getFecha());
        dto.setTotal(venta.getTotal());
        if (venta.getTipoPago() != null)
            dto.setNombreTipoPago(venta.getTipoPago().getNombreTipoPago());
        if (venta.getTipoVenta() != null)
            dto.setNombreTipoVenta(venta.getTipoVenta().getNombre());
        if (venta.getTipoDespacho() != null)
            dto.setNombreTipoDespacho(venta.getTipoDespacho().getNombreTipoDespacho());
        dto.setCliente(obtenerCliente(venta.getIdVenta()));
        return dto;
    }
}