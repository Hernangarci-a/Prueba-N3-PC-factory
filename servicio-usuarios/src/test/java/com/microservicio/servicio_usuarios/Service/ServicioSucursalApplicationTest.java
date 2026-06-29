package com.microservicio.servicio_usuarios.Service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;

import com.microservicio.servicio_usuarios.dto.SucursalDTO;
import com.microservicio.servicio_usuarios.model.Colaborador;
import com.microservicio.servicio_usuarios.model.Comuna;
import com.microservicio.servicio_usuarios.model.Sucursal;
import com.microservicio.servicio_usuarios.repository.ColaboradorRepository;
import com.microservicio.servicio_usuarios.repository.ComunaRepository;
import com.microservicio.servicio_usuarios.repository.SucursalRepository;
import com.microservicio.servicio_usuarios.services.SucursalService;

import net.datafaker.Faker;

@ExtendWith(MockitoExtension.class)
class ServicioSucursalApplicationTest {

    @Mock
    private SucursalRepository sucursalRepository;

    @Mock
    private ComunaRepository comunaRepository;

    @Mock
    private ColaboradorRepository colaboradorRepository;

    @InjectMocks
    private SucursalService sucursalService;

    private Faker faker = new Faker();

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testBuscarPorId() {

        Integer id = 1;

        Comuna comuna = new Comuna();
        comuna.setNombre_comuna("Santiago");

        Sucursal sucursal = new Sucursal();
        sucursal.setIdSucursal(id);
        sucursal.setNombreSucursal(faker.company().name());
        sucursal.setDireccionSucursal(faker.address().streetAddress());
        sucursal.setComuna(comuna);
        sucursal.setColaboradores(new ArrayList<>());

        when(sucursalRepository.findById(id)).thenReturn(Optional.of(sucursal));

        SucursalDTO resultado = sucursalService.buscarPorId(id);

        assertNotNull(resultado);
        assertEquals(id, resultado.getIdSucursal());

        verify(sucursalRepository, times(1)).findById(id);
    }

    @Test
    void testGuardarSucursal() {

        Integer id = 2;

        Comuna comuna = new Comuna();
        comuna.setNombre_comuna("Santiago");

        Colaborador colaborador = new Colaborador();
        colaborador.setNombreColaborador("Juan Perez");

        SucursalDTO dto = new SucursalDTO();
        dto.setIdSucursal(id);
        dto.setNombreSucursal(faker.company().name());
        dto.setDireccionSucursal(faker.address().streetAddress());
        dto.setNombreComuna("Santiago");
        dto.setNombresColaboradores(Collections.singletonList("Juan Perez"));

        Sucursal entidad = new Sucursal();
        entidad.setIdSucursal(id);
        entidad.setNombreSucursal(dto.getNombreSucursal());
        entidad.setDireccionSucursal(dto.getDireccionSucursal());
        entidad.setComuna(comuna);
        entidad.setColaboradores(Collections.singletonList(colaborador));

        when(comunaRepository.findAll()).thenReturn(Collections.singletonList(comuna));
        when(colaboradorRepository.findAll()).thenReturn(Collections.singletonList(colaborador));
        when(sucursalRepository.save(org.mockito.ArgumentMatchers.any(Sucursal.class)))
                .thenReturn(entidad);

        SucursalDTO resultado = sucursalService.guardarSucursal(dto);

        assertNotNull(resultado);
        assertEquals(id, resultado.getIdSucursal());

        verify(comunaRepository, times(1)).findAll();
        verify(colaboradorRepository, times(1)).findAll();
        verify(sucursalRepository, times(1))
                .save(org.mockito.ArgumentMatchers.any(Sucursal.class));
    }

    @Test
    void testDeleteById() {

        Integer id = 3;

        Sucursal sucursal = new Sucursal();
        sucursal.setIdSucursal(id);
        sucursal.setNombreSucursal("Sucursal Test");

        when(sucursalRepository.findById(id)).thenReturn(Optional.of(sucursal));
        doNothing().when(sucursalRepository).delete(sucursal);

        sucursalService.eliminarSucursal(id);

        verify(sucursalRepository, times(1)).findById(id);
        verify(sucursalRepository, times(1)).delete(sucursal);
    }

}