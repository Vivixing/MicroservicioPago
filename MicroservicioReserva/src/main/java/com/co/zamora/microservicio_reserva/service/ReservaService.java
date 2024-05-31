package com.co.zamora.microservicio_reserva.service;

import java.util.Optional;

import com.co.zamora.microservicio_reserva.entity.Reserva;

public interface ReservaService {
	public Iterable<Reserva> findAll();
	public Optional<Reserva> findById(Long id);
	public Reserva save(Reserva reserva);
	public void deleteById(Long id);
}
