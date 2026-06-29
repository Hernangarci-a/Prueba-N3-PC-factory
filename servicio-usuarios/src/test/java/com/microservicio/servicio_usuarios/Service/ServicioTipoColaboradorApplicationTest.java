package com.microservicio.servicio_usuarios.Service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
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

import com.microservicio.servicio_usuarios.dto.TipoColaboradorDTO;
import com.microservicio.servicio_usuarios.model.Colaborador;
import com.microservicio.servicio_usuarios.model.TipoColaborador;
import com.microservicio.servicio_usuarios.repository.ColaboradorRepository;
import com.microservicio.servicio_usuarios.repository.TipoColaboradorRepository;
import com.microservicio.servicio_usuarios.services.TipoColaboradorService;

@ExtendWith(MockitoExtension.class)
class ServicioTipoColaboradorApplicationTest {

    @Mock
    private TipoColaboradorRepository tipoColaboradorRepository;

    @Mock
    private ColaboradorRepository colaboradorRepository;

    @InjectMocks
    private TipoColaboradorService tipoColaboradorService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testBuscarPorId() {

        Integer id = 1;

        Colaborador colaborador = new Colaborador();
        colaborador.setNombreColaborador("Juan Perez");

        TipoColaborador tipo = new TipoColaborador();
        tipo.setIdTipoColaborador(id);
        tipo.setNombre("Vendedor");
        tipo.setColaboradores(Collections.singletonList(colaborador));

        when(tipoColaboradorRepository.findById(id)).thenReturn(Optional.of(tipo));

        TipoColaboradorDTO resultado = tipoColaboradorService.buscarPorId(id);

        assertNotNull(resultado);
        assertEquals(id, resultado.getIdTipoColaborador());

        verify(tipoColaboradorRepository, times(1)).findById(id);
    }

    @Test
    void testGuardarTipoColaborador() {

        Integer id = 2;

        Colaborador colaborador = new Colaborador();
        colaborador.setNombreColaborador("Juan Perez");

        TipoColaboradorDTO dto = new TipoColaboradorDTO();
        dto.setIdTipoColaborador(id);
        dto.setNombre("Administrador");
        dto.setNombresColaboradores(Collections.singletonList("Juan Perez"));

        TipoColaborador entidad = new TipoColaborador();
        entidad.setIdTipoColaborador(id);
        entidad.setNombre("Administrador");
        entidad.setColaboradores(Collections.singletonList(colaborador));

        when(colaboradorRepository.findAll()).thenReturn(Collections.singletonList(colaborador));
        when(tipoColaboradorRepository.save(any(TipoColaborador.class))).thenReturn(entidad);

        TipoColaboradorDTO resultado = tipoColaboradorService.guardarTipoColaborador(dto);

        assertNotNull(resultado);
        assertEquals(id, resultado.getIdTipoColaborador());

        verify(colaboradorRepository, times(1)).findAll();
        verify(tipoColaboradorRepository, times(1)).save(any(TipoColaborador.class));
    }

    @Test
    void testActualizarTipoColaborador() {

        Integer id = 3;

        TipoColaborador tipo = new TipoColaborador();
        tipo.setIdTipoColaborador(id);
        tipo.setNombre("Vendedor");
        tipo.setColaboradores(new ArrayList<>());

        TipoColaboradorDTO dto = new TipoColaboradorDTO();
        dto.setNombre("Supervisor");

        when(tipoColaboradorRepository.findById(id)).thenReturn(Optional.of(tipo));
        when(tipoColaboradorRepository.save(any(TipoColaborador.class))).thenReturn(tipo);

        TipoColaboradorDTO resultado = tipoColaboradorService.actualizarTipoColaborador(id, dto);

        assertNotNull(resultado);
        assertEquals("Supervisor", resultado.getNombre());

        verify(tipoColaboradorRepository, times(1)).findById(id);
        verify(tipoColaboradorRepository, times(1)).save(tipo);
    }

    @Test
    void testDeleteById() {

        Integer id = 4;

        TipoColaborador tipo = new TipoColaborador();
        tipo.setIdTipoColaborador(id);
        tipo.setNombre("Administrador");

        when(tipoColaboradorRepository.findById(id)).thenReturn(Optional.of(tipo));
        doNothing().when(tipoColaboradorRepository).delete(tipo);

        tipoColaboradorService.eliminarTipoColaborador(id);

        verify(tipoColaboradorRepository, times(1)).findById(id);
        verify(tipoColaboradorRepository, times(1)).delete(tipo);
    }

}