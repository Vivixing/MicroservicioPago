package com.co.zamora.microservicio_reserva.controller;


import java.util.List;
import java.util.Optional;
import java.util.concurrent.CompletableFuture;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.co.zamora.microservicio_reserva.entity.Reserva;
import com.co.zamora.microservicio_reserva.entity.Usuario;
import com.co.zamora.microservicio_reserva.service.ReservaService;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.type.CollectionType;

@RestController
public class ReservaController {
	
	@Autowired
	ReservaService reservaService;

	@GetMapping
	public ResponseEntity<?> listarReserva(){
		return ResponseEntity.ok().body(reservaService.findAll());
	}
	
	@GetMapping("/obtenerReserva/{id}")
	public ResponseEntity<?> verReservaById(@PathVariable Long id){
		Optional<Reserva> ob = reservaService.findById(id);
		
		if(ob.isEmpty()) {
			return ResponseEntity.noContent().build();
		}
		return ResponseEntity.ok().body(ob.get());
	}
	

	@PostMapping("crearReserva")
	public ResponseEntity<?> crearReserva(@RequestBody Reserva reserva) throws Exception {
		CompletableFuture<List<Object>> usuariosJsonFuture = reservaService.getUsuarios();
		List<Object> usuariosJson = usuariosJsonFuture.join();
	
		// Convierte usuariosJson a una cadena JSON
		ObjectMapper mapper = new ObjectMapper();
		String jsonArray = mapper.writeValueAsString(usuariosJson);
	
		// Deserializa la cadena JSON a una lista de Usuario
		CollectionType listType = mapper.getTypeFactory().constructCollectionType(List.class, Usuario.class);
		List<Usuario> usuarios = mapper.readValue(jsonArray, listType);
	
		// Verifica si el idUsuario de la reserva está presente en la lista de usuarios
		boolean idPresente = usuarios.stream()
				.map(Usuario::getIdUsuario)
				.anyMatch(usuarioId -> usuarioId.equals(reserva.getIdUsuario()));
	
		if (!idPresente) {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("El id de usuario no está presente en la lista de usuarios");
		}
	
		return ResponseEntity.status(HttpStatus.CREATED).body(reservaService.save(reserva));
	}
	
	
	@PutMapping("editarReserva/{id}")
	public ResponseEntity<?> editarReserva(@RequestBody Reserva reserva, @PathVariable Long id){
		Optional<Reserva> ob = reservaService.findById(id);
		
		if(ob.isEmpty()) {
			return ResponseEntity.noContent().build();
		}
		Reserva reservaBd = ob.get();
		reservaBd.setFechaReserva(reserva.getFechaReserva());
		reservaBd.setFechaServicio(reserva.getFechaServicio());
		reservaBd.setHoraServicio(reserva.getHoraServicio());
		reservaBd.setDuracion(reserva.getDuracion());
		reservaBd.setNumeroPersonas(reserva.getNumeroPersonas());
		reservaBd.setTipoServicio(reserva.getTipoServicio());
		reservaBd.setNumeroReserva(reserva.getNumeroReserva());
		
		return ResponseEntity.status(HttpStatus.CREATED).body(reservaService.save(reservaBd));
	}
	
	@DeleteMapping("eliminarUsuario/{id}")
	private ResponseEntity<?> eliminarReserva(@PathVariable Long id){
		
		reservaService.deleteById(id);
		return ResponseEntity.noContent().build();
	}
	@GetMapping("/usuarios")
	public CompletableFuture<List<Object>> getUsuarios() throws Exception {
		
	    return reservaService.getUsuarios();
	}
}
