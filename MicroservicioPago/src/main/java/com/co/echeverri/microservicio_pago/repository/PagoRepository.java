package com.co.echeverri.microservicio_pago.repository;

import org.springframework.data.repository.CrudRepository;

import com.co.echeverri.microservicio_pago.entity.Pago;

public interface PagoRepository extends CrudRepository<Pago, Long> {

}
