package com.co.zamora.microservicio_reserva.controller;


import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;

import com.co.zamora.microservicio_reserva.entity.Reserva;
import com.co.zamora.microservicio_reserva.service.ReservaService;

public class ReservaController {
	@Autowired
	ReservaService reservaService;
	
	@GetMapping
	public ResponseEntity<?> listarReserva(){
		return ResponseEntity.ok().body(reservaService.findAll());
	}
	
	@GetMapping("/")
	public ResponseEntity<?> verReservaById(@PathVariable Long id){
		Optional<Reserva> ob = reservaService.findById(id);
		
		if(ob.isEmpty()) {
			return ResponseEntity.noContent().build();
		}
		return ResponseEntity.ok().body(ob.get());
	}
	
	@PostMapping
	public ResponseEntity<?> crearReserva(@RequestBody Reserva reserva, Long id){
		Reserva reservaDb = reservaService.save(reserva);
		return ResponseEntity.status(HttpStatus.CREATED).body(reservaService.save(reservaDb));
	}
	
	@PutMapping("/{id}")
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
	
	@DeleteMapping("/{id}")
	private ResponseEntity<?> eliminarReserva(@PathVariable Long id){
		
		reservaService.deleteById(id);
		return ResponseEntity.noContent().build();
	}
}
