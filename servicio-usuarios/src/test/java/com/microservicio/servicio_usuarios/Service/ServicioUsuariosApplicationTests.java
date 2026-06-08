package com.microservicio.servicio_usuarios.Service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;

import com.microservicio.servicio_usuarios.dto.ClienteDTO;
import com.microservicio.servicio_usuarios.model.Cliente;
import com.microservicio.servicio_usuarios.model.Comuna;
import com.microservicio.servicio_usuarios.repository.ClienteRepository;
import com.microservicio.servicio_usuarios.services.ClienteService;


import net.datafaker.Faker;

@ExtendWith(MockitoExtension.class)
class ServicioUsuariosApplicationTests {

	@Mock
	private ClienteRepository clienteRepository;

	@InjectMocks
	private ClienteService clienteService;
	private Faker faker = new Faker();
	@BeforeEach
	void setUp(){
		MockitoAnnotations.openMocks(this);
	}


	@Test
	void testBuscarPorId_Exitoso(){
		Integer idSimulado = 20;
		Comuna comuna = new Comuna();
		String nombreAleatorio = faker.name().firstName();
		String apellidoFalso = faker.name().lastName();
		String correoFalso = faker.internet().emailAddress();
		String direccionFalsa = faker.address().city();
		String telefonoFalso = faker.phoneNumber().cellPhone();
		Cliente clienteFalso = new Cliente();
		clienteFalso.setIdCliente(idSimulado);
		clienteFalso.setRut("12345678-9");
		clienteFalso.setNombreCliente(nombreAleatorio);
		clienteFalso.setApellidoCliente(apellidoFalso);
		clienteFalso.setCorreo(correoFalso);
		clienteFalso.setDireccion(direccionFalsa);
		clienteFalso.setTelefono(telefonoFalso);
		clienteFalso.setComuna(comuna);

		when(clienteRepository.findById(idSimulado)).thenReturn(Optional.of(clienteFalso));

		ClienteDTO resultado = clienteService.buscarPorId(idSimulado);

		assertNotNull(resultado, "El DTO resultante no deberia ser nulo");
		assertEquals(nombreAleatorio, resultado.getPrimerNombre(), "El nombre transformado al DTO debe coincidir con el de la DB");

		verify(clienteRepository, times(1)).findById(idSimulado);
	}
}
