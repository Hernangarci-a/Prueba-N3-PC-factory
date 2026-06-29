package com.microservicio.servicio_usuarios.Service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
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

import com.microservicio.servicio_usuarios.dto.ComunaDTO;
import com.microservicio.servicio_usuarios.model.Comuna;
import com.microservicio.servicio_usuarios.model.Region;
import com.microservicio.servicio_usuarios.repository.ComunaRepository;
import com.microservicio.servicio_usuarios.repository.RegionRepository;
import com.microservicio.servicio_usuarios.services.ComunaService;

import net.datafaker.Faker;

@ExtendWith(MockitoExtension.class)
class ServicioComunaApplicationTest {

    @Mock
    private ComunaRepository comunaRepository;

    @Mock
    private RegionRepository regionRepository;

    @InjectMocks
    private ComunaService comunaService;

    private Faker faker = new Faker();

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    private Region crearRegion() {
        Region region = new Region();
        region.setId_region(1);
        region.setNombre_region("Metropolitana");
        return region;
    }

    private Comuna crearComuna() {
        Comuna comuna = new Comuna();
        comuna.setId_comuna(1);
        comuna.setNombre_comuna(faker.address().city());
        comuna.setRegion(crearRegion());
        return comuna;
    }

    private ComunaDTO crearComunaDTO() {
        ComunaDTO dto = new ComunaDTO();
        dto.setIdComuna(1);
        dto.setNombreComuna(faker.address().city());
        dto.setNombreRegion("Metropolitana");
        return dto;
    }

    @Test
    void testBuscarPorId() {

        Comuna comuna = crearComuna();

        when(comunaRepository.findById(1))
                .thenReturn(Optional.of(comuna));

        ComunaDTO resultado = comunaService.buscarPorId(1);

        assertNotNull(resultado);
        assertEquals(1, resultado.getIdComuna());

        verify(comunaRepository, times(1)).findById(1);
    }

    @Test
    void testGuardarComuna() {

        ComunaDTO dto = crearComunaDTO();

        Comuna entidad = new Comuna();
        entidad.setId_comuna(dto.getIdComuna());
        entidad.setNombre_comuna(dto.getNombreComuna());
        entidad.setRegion(crearRegion());

        when(regionRepository.findAll())
                .thenReturn(List.of(crearRegion()));

        when(comunaRepository.save(any(Comuna.class)))
                .thenReturn(entidad);

        ComunaDTO resultado = comunaService.guardarComuna(dto);

        assertNotNull(resultado);
        assertEquals(dto.getNombreComuna(), resultado.getNombreComuna());

        verify(regionRepository, times(1)).findAll();
        verify(comunaRepository, times(1)).save(any(Comuna.class));
    }

    @Test
    void testEliminarComuna() {

        Comuna comuna = crearComuna();

        when(comunaRepository.findById(1))
                .thenReturn(Optional.of(comuna));

        doNothing().when(comunaRepository).delete(comuna);

        String resultado = comunaService.eliminarComuna(1);

        assertEquals("la comuna " + comuna.getNombre_comuna() + " se elimino", resultado);

        verify(comunaRepository, times(1)).delete(comuna);
    }

}