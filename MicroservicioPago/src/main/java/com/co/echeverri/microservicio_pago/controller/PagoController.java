package com.co.echeverri.microservicio_pago.controller;

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

import com.co.echeverri.microservicio_pago.entity.Pago;
import com.co.echeverri.microservicio_pago.entity.Reserva;
import com.co.echeverri.microservicio_pago.service.PagoService;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.type.CollectionType;

@RestController
public class PagoController {
	
	@Autowired
	PagoService service;
	
	@GetMapping
	public ResponseEntity<?> listarPago(){
		return ResponseEntity.ok().body(service.findAll());
	}
	
	@GetMapping("/reservas")
	public CompletableFuture<List<Object>> getReservas() throws Exception {
		
	    return service.getReservas();
	}
	
	@GetMapping("/obtenerPago/{id}")
	public ResponseEntity<?> ver(@PathVariable Long id){
		Optional<Pago> ob = service.findById(id);
		
		if(ob.isEmpty()) {
			return ResponseEntity.noContent().build();
		}
		return ResponseEntity.ok().body(ob.get());
	}
	
	@PostMapping("crearPago")
	public ResponseEntity<?> crear(@RequestBody Pago pago, Long id)throws Exception {
		CompletableFuture<List<Object>> reservasJsonFuture = service.getReservas();
		List<Object> reservasJson = reservasJsonFuture.join();
	
		
		ObjectMapper mapper = new ObjectMapper();
		String jsonArray = mapper.writeValueAsString(reservasJson);
	
		
		CollectionType listType = mapper.getTypeFactory().constructCollectionType(List.class, Reserva.class);
		List<Reserva> reservas = mapper.readValue(jsonArray, listType);
	
		
		boolean idPresente = reservas.stream()
				.map(Reserva::getIdReserva)
				.anyMatch(IdReserva -> IdReserva.equals(pago.getIdReserva()));
	
		if (!idPresente) {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("El id de la reserva no está presente en la lista de Reservas");
		}
	
		return ResponseEntity.status(HttpStatus.CREATED).body(service.save(pago));
	}
	
	@PutMapping("editarReserva/{id}")
	public ResponseEntity<?> editar(@RequestBody Pago pago, @PathVariable Long id){
		Optional<Pago> ob = service.findById(id);
		
		if(ob.isEmpty()) {
			return ResponseEntity.noContent().build();
		}
		Pago pagoBd = ob.get();
		pagoBd.isEstado();
		
		return ResponseEntity.status(HttpStatus.CREATED).body(service.save(pagoBd));
	}
	
	@DeleteMapping("eliminarReserva/{id}")
	private ResponseEntity<?> eliminar(@PathVariable Long id){
		
		service.deleteById(id);
		return ResponseEntity.noContent().build();
	}

}
