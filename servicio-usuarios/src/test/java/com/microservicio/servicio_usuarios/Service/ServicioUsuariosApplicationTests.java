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

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.microservicio.servicio_usuarios.dto.ClienteDTO;
import com.microservicio.servicio_usuarios.model.Cliente;
import com.microservicio.servicio_usuarios.model.Comuna;
import com.microservicio.servicio_usuarios.repository.ClienteRepository;
import com.microservicio.servicio_usuarios.repository.ComunaRepository;
import com.microservicio.servicio_usuarios.services.ClienteService;

import net.datafaker.Faker;

@ExtendWith(MockitoExtension.class)
class ServicioUsuariosApplicationTests {

	@Mock
	private ClienteRepository clienteRepository;

	@Mock
	private ComunaRepository comunaRepository;

	@InjectMocks
	private ClienteService clienteService;

	private Faker faker = new Faker();

	@Test
	void testBuscarPorId_Exitoso() {

		Integer idSimulado = 20;

		Comuna comuna = new Comuna();
		comuna.setNombre_comuna("Santiago");

		Cliente clienteFalso = new Cliente();
		clienteFalso.setIdCliente(idSimulado);
		clienteFalso.setRut("12345678-9");
		clienteFalso.setNombreCliente(faker.name().firstName());
		clienteFalso.setApellidoCliente(faker.name().lastName());
		clienteFalso.setCorreo(faker.internet().emailAddress());
		clienteFalso.setDireccion(faker.address().city());
		clienteFalso.setTelefono(faker.phoneNumber().cellPhone());
		clienteFalso.setComuna(comuna);

		when(clienteRepository.findById(idSimulado))
				.thenReturn(Optional.of(clienteFalso));

		ClienteDTO resultado = clienteService.buscarPorId(idSimulado);

		assertNotNull(resultado);
		assertEquals(idSimulado, resultado.getIdCliente());

		verify(clienteRepository, times(1)).findById(idSimulado);
	}

	@Test
	void testGuardarCliente_Exitoso() {

		Integer idSimulado = 30;

		Comuna comuna = new Comuna();
		comuna.setNombre_comuna("Santiago");

		ClienteDTO dto = new ClienteDTO();
		dto.setIdCliente(idSimulado);
		dto.setPrimerNombre(faker.name().firstName());
		dto.setApellidoCliente(faker.name().lastName());
		dto.setCorreo(faker.internet().emailAddress());
		dto.setDireccion(faker.address().city());
		dto.setTelefono(faker.phoneNumber().cellPhone());
		dto.setComuna("Santiago");

		Cliente entidad = new Cliente();
		entidad.setIdCliente(idSimulado);
		entidad.setNombreCliente(dto.getPrimerNombre());
		entidad.setApellidoCliente(dto.getApellidoCliente());
		entidad.setCorreo(dto.getCorreo());
		entidad.setDireccion(dto.getDireccion());
		entidad.setTelefono(dto.getTelefono());
		entidad.setComuna(comuna);

		when(comunaRepository.findAll()).thenReturn(List.of(comuna));
		when(clienteRepository.save(any(Cliente.class))).thenReturn(entidad);

		ClienteDTO resultado = clienteService.guardarCliente(dto);

		assertNotNull(resultado);
		assertEquals(idSimulado, resultado.getIdCliente());

		verify(clienteRepository, times(1)).save(any(Cliente.class));
	}

	@Test
	void testDeleteById() {

		doNothing().when(clienteRepository).deleteById(1);

		clienteService.eliminarCliente(1);

		verify(clienteRepository, times(1)).deleteById(1);
	}
}