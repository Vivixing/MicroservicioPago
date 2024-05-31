package com.co.zamora.microservicio_reserva.service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import com.co.zamora.microservicio_reserva.entity.Reserva;
import com.co.zamora.microservicio_reserva.repository.ReservaRepository;

public class ReservaServiceImpl implements ReservaService{
	@Autowired
	ReservaRepository dao;
	
	@Override
	@Transactional(readOnly = true)
	public Iterable<Reserva> findAll() {
		return dao.findAll();
	}

	@Override
	@Transactional(readOnly = true)
	public Optional<Reserva> findById(Long id) {
		return dao.findById(id);
	}

	@Override
	@Transactional
	public Reserva save(Reserva reserva) {
		return dao.save(reserva);
	}

	@Override
	@Transactional
	public void deleteById(Long id) {
		dao.deleteById(id);
		
	}
}
