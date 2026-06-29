package com.microservicio.servicio_usuarios.Service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;

import com.microservicio.servicio_usuarios.dto.RegionDTO;
import com.microservicio.servicio_usuarios.model.Region;
import com.microservicio.servicio_usuarios.repository.ComunaRepository;
import com.microservicio.servicio_usuarios.repository.RegionRepository;
import com.microservicio.servicio_usuarios.services.RegionService;

import net.datafaker.Faker;

@ExtendWith(MockitoExtension.class)
class ServicioRegionApplicationTest {

    @Mock
    private RegionRepository regionRepository;

    @Mock
    private ComunaRepository comunaRepository;

    @InjectMocks
    private RegionService regionService;

    private Faker faker = new Faker();

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    private Region crearRegion() {
        Region region = new Region();
        region.setId_region(1);
        region.setNombre_region(faker.address().state());
        region.setComunas(new ArrayList<>());
        return region;
    }

    private RegionDTO crearRegionDTO() {
        RegionDTO dto = new RegionDTO();
        dto.setIdRegion(1);
        dto.setNombreRegion(faker.address().state());
        dto.setNombresComunas(new ArrayList<>());
        return dto;
    }

    @Test
    void testBuscarPorId() {

        Region region = crearRegion();

        when(regionRepository.findById(1))
                .thenReturn(Optional.of(region));

        RegionDTO resultado = regionService.buscarPorId(1);

        assertNotNull(resultado);
        assertEquals(region.getId_region(), resultado.getIdRegion());

        verify(regionRepository, times(1)).findById(1);
    }

    @Test
    void testGuardarRegion() {

        RegionDTO dto = crearRegionDTO();

        Region entidad = new Region();
        entidad.setId_region(dto.getIdRegion());
        entidad.setNombre_region(dto.getNombreRegion());
        entidad.setComunas(new ArrayList<>());

        when(regionRepository.save(any(Region.class)))
                .thenReturn(entidad);

        RegionDTO resultado = regionService.guardarRegion(dto);

        assertNotNull(resultado);
        assertEquals(dto.getNombreRegion(), resultado.getNombreRegion());

        verify(regionRepository, times(1)).save(any(Region.class));
    }

    @Test
    void testEliminarRegion() {

        Region region = crearRegion();

        when(regionRepository.findById(1))
                .thenReturn(Optional.of(region));

        doNothing().when(regionRepository).delete(region);

        String resultado = regionService.eliminarRegion(1);

        assertEquals("la region " + region.getNombre_region() + " se elimino", resultado);

        verify(regionRepository, times(1)).delete(region);
    }

}