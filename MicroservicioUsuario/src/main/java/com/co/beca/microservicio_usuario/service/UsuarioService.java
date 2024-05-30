package com.co.beca.microservicio_usuario.service;

import java.util.Optional;

import com.co.beca.microservicio_usuario.entity.Usuario;

public interface UsuarioService {
		
	public Iterable<Usuario> findAll();
	public Optional<Usuario> findById(Long id);
	public Usuario save(Usuario usuario);
	public void deleteById(Long id);
	
}
