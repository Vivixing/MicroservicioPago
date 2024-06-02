package com.co.beca.microservicio_usuario.controller;

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

import com.co.beca.microservicio_usuario.entity.Usuario;
import com.co.beca.microservicio_usuario.service.UsuarioService;

@RestController
public class UsuarioController {
	@Autowired
	UsuarioService service;
	
	@GetMapping("/obtenerUsuarios")
	public ResponseEntity<?> listarUsuarios(){
		return ResponseEntity.ok().body(service.findAll());
	}
	
	@GetMapping("/obtenerUsuario/{id}")
	public ResponseEntity<?> ver(@PathVariable Long id){
		Optional<Usuario> ob = service.findById(id);
		
		if(ob.isEmpty()) {
			return ResponseEntity.noContent().build();
		}
		return ResponseEntity.ok().body(ob.get());
	}
	
	@PostMapping("/agregarUsuario")
	public ResponseEntity<?> crear(@RequestBody Usuario usuario, Long id){
		Usuario usuarioBd= service.save(usuario);
		return ResponseEntity.status(HttpStatus.CREATED).body(service.save(usuarioBd));
	}
	
	@PutMapping("/actualizarUsuario/{id}")
	public ResponseEntity<?> editar(@RequestBody Usuario usuario, @PathVariable Long id){
		Optional<Usuario> ob = service.findById(id);
		
		if(ob.isEmpty()) {
			return ResponseEntity.noContent().build();
		}
		Usuario usuarioBd = ob.get();
		usuarioBd.setNombre(usuario.getNombre());
		usuarioBd.setApellido(usuario.getApellido());
		usuarioBd.setEmail(usuario.getEmail());
		usuarioBd.setTelefono(usuario.getTelefono());
		
		return ResponseEntity.status(HttpStatus.CREATED).body(service.save(usuarioBd));
	}
	
	@DeleteMapping("/eliminar/{id}")
	private ResponseEntity<?> eliminar(@PathVariable Long id){
		
		service.deleteById(id);
		return ResponseEntity.noContent().build();
	}

}
