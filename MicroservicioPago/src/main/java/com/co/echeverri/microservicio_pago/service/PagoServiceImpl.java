package com.co.echeverri.microservicio_pago.service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import com.co.echeverri.microservicio_pago.entity.Pago;
import com.co.echeverri.microservicio_pago.repository.PagoRepository;

public class PagoServiceImpl implements PagoService {

	@Autowired
	PagoRepository dao;
	
	@Override
	@Transactional(readOnly = true)
	public Iterable<Pago> findAll() {
		return dao.findAll();
	}

	@Override
	@Transactional(readOnly = true)
	public Optional<Pago> findById(Long id) {
		return dao.findById(id);
	}

	@Override
	@Transactional
	public Pago save(Pago pago) {
		return dao.save(pago);
	}

	@Override
	@Transactional
	public void deleteById(Long id) {
		dao.deleteById(id);
		
	}

}
