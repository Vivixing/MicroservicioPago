package com.co.beca.microservicio_usuario.repository;

import org.springframework.data.repository.CrudRepository;

import com.co.beca.microservicio_usuario.entity.Usuario;

public interface UsuarioRepository extends CrudRepository<Usuario, Long>{

}
