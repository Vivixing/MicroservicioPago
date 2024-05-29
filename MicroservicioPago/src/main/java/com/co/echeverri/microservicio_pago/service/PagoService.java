package com.co.echeverri.microservicio_pago.service;

import java.util.Optional;

import com.co.echeverri.microservicio_pago.entity.Pago;

public interface PagoService {
	public Iterable<Pago> findAll();
	public Optional<Pago> findById(Long id);
	public Pago save(Pago pago);
	public void deleteById(Long id);
}
