package com.microservicio.servicio_usuarios.Service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;

import com.microservicio.servicio_usuarios.dto.ColaboradorDTO;
import com.microservicio.servicio_usuarios.model.Colaborador;
import com.microservicio.servicio_usuarios.model.Sucursal;
import com.microservicio.servicio_usuarios.model.TipoColaborador;
import com.microservicio.servicio_usuarios.repository.ColaboradorRepository;
import com.microservicio.servicio_usuarios.services.ColaboradorService;

import net.datafaker.Faker;

@ExtendWith(MockitoExtension.class)
class ServicioColaboradorApplicationTest {

	@Mock
	private ColaboradorRepository colaboradorRepository;

	@InjectMocks
	private ColaboradorService colaboradorService;
	private Faker faker = new Faker();

	@BeforeEach
	void setUp() {
		MockitoAnnotations.openMocks(this);
	}

	private Colaborador createColaborador() {
		List<TipoColaborador> tipoColaboradores = new ArrayList<>();
		List<Sucursal> sucursales = new ArrayList<>();
		Colaborador colaborador = new Colaborador(1, faker.name().firstName(), "12345678-9",
				faker.internet().emailAddress(), faker.phoneNumber().cellPhone(), true, sucursales, tipoColaboradores);
		return colaborador;
	}

	@Test
	void testSave() {
		Colaborador colaborador = createColaborador();

		when(colaboradorRepository.save(colaborador)).thenReturn(colaborador);

		ColaboradorDTO resultado = colaboradorService.guardarColaborador(colaborador);

		assertNotNull(resultado);
		assertEquals(colaborador.getIdColaborador(), resultado.getIdColaborador());

		verify(colaboradorRepository, times(1)).save(colaborador);
	}

	@Test
	void testDeleteById() {
		doNothing().when(colaboradorRepository).deleteById(1);

		colaboradorService.eliminarColaborador(1);

		verify(colaboradorRepository, times(1)).deleteById(1);
	}

	@Test
	void testFindById() {
		Colaborador colaborador = createColaborador();

		when(colaboradorRepository.findById(1))
				.thenReturn(java.util.Optional.of(colaborador));

		ColaboradorDTO resultado = colaboradorService.buscarPorId(1);

		assertNotNull(resultado);
		assertEquals(1, resultado.getIdColaborador());

		verify(colaboradorRepository, times(1)).findById(1);
	}

}