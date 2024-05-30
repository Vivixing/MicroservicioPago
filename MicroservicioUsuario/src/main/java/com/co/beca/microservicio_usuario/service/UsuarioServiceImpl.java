package com.co.beca.microservicio_usuario.service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.co.beca.microservicio_usuario.entity.Usuario;
import com.co.beca.microservicio_usuario.repository.UsuarioRepository;

@Service
public class UsuarioServiceImpl implements UsuarioService{
	@Autowired
	UsuarioRepository dao;
	
	@Override
	@Transactional(readOnly = true)
	public Iterable<Usuario> findAll() {
		
		return dao.findAll();
	}

	@Override
	@Transactional(readOnly = true)
	public Optional<Usuario> findById(Long id) {
		
		return dao.findById(id);
	}

	@Override
	@Transactional
	public Usuario save(Usuario usuario) {
		
		return dao.save(usuario);
	}

	@Override
	@Transactional
	public void deleteById(Long id) {
		dao.deleteById(id);
		
	}

}
