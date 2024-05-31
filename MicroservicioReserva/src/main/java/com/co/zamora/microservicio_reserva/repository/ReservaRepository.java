package com.co.zamora.microservicio_reserva.repository;

import org.springframework.data.repository.CrudRepository;

import com.co.zamora.microservicio_reserva.entity.Reserva;

public interface ReservaRepository extends CrudRepository<Reserva, Long> {

}
