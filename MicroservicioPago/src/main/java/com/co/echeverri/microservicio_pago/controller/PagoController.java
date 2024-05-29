package com.co.echeverri.microservicio_pago.controller;

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
import org.springframework.web.bind.annotation.RestController;

import com.co.echeverri.microservicio_pago.entity.Pago;
import com.co.echeverri.microservicio_pago.service.PagoService;

@RestController
public class PagoController {
	
	@Autowired
	PagoService service;
	
	@GetMapping
	public ResponseEntity<?> listarPago(){
		return ResponseEntity.ok().body(service.findAll());
	}
	
	@GetMapping("/")
	public ResponseEntity<?> ver(@PathVariable Long id){
		Optional<Pago> ob = service.findById(id);
		
		if(ob.isEmpty()) {
			return ResponseEntity.noContent().build();
		}
		return ResponseEntity.ok().body(ob.get());
	}
	
	@PostMapping
	public ResponseEntity<?> crear(@RequestBody Pago pago, Long id){
		Pago pagoDb = service.save(pago);
		return ResponseEntity.status(HttpStatus.CREATED).body(service.save(pagoDb));
	}
	
	@PutMapping("/{id}")
	public ResponseEntity<?> editar(@RequestBody Pago pago, @PathVariable Long id){
		Optional<Pago> ob = service.findById(id);
		
		if(ob.isEmpty()) {
			return ResponseEntity.noContent().build();
		}
		Pago pagoBd = ob.get();
		pagoBd.isEstado();
		
		return ResponseEntity.status(HttpStatus.CREATED).body(service.save(pagoBd));
	}
	
	@DeleteMapping("/{id}")
	private ResponseEntity<?> eliminar(@PathVariable Long id){
		
		service.deleteById(id);
		return ResponseEntity.noContent().build();
	}

}
