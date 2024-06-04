package com.co.echeverri.microservicio_pago.service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.CompletableFuture;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.client.RestTemplate;

import com.co.echeverri.microservicio_pago.controller.PagoController;
import com.co.echeverri.microservicio_pago.entity.Pago;
import com.co.echeverri.microservicio_pago.repository.PagoRepository;


import io.github.resilience4j.circuitbreaker.CircuitBreakerRegistry;
import io.github.resilience4j.circuitbreaker.CircuitBreaker.State;
import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import io.github.resilience4j.timelimiter.annotation.TimeLimiter;

@Service
public class PagoServiceImpl implements PagoService {

	@Autowired
	PagoRepository dao;
	
	@Autowired
    CircuitBreakerRegistry circuitBreakerRegistry;
	private static final Logger logger = LoggerFactory.getLogger(PagoController.class);
	RestTemplate restTemplate = new RestTemplate();
	
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
	
	@Override
	@CircuitBreaker(name = "reservasCircuitBreaker", fallbackMethod = "reservasFallido")
	@TimeLimiter(name = "offersTimeLimiter")
	public CompletableFuture<List<Object>> getReservas() throws Exception {
	    return CompletableFuture.supplyAsync(() -> {
	        Object[] reservas = null;
	        State estado = circuitBreakerRegistry.circuitBreaker("reservasCircuitBreaker").getState();
			logger.info("Circuit Breaker State : {} -> {}", estado);
			circuitBreakerRegistry.circuitBreaker("reservasCircuitBreaker").getEventPublisher().onEvent(event -> {
				   logger.info("State change {}", event);
			});
	        try {
	            reservas = restTemplate.getForObject("http://localhost:8083/obtenerReservas", Object[].class);
	        } catch (Exception e) {
	            throw new RuntimeException("Failed to fetch reservas", e);
	        }
	        return Arrays.asList(reservas);
	    });
	}
	
	public CompletableFuture<List<Object>> reservasFallido(Throwable throwable) {
	    List<Object> fallbackResponse = new ArrayList<>();
	    fallbackResponse.add("El microservicio de reserva no está disponible!");
	    State estado = circuitBreakerRegistry.circuitBreaker("reservasCircuitBreaker").getState();
		logger.info("Circuit Breaker State : {} -> {}", estado);
		circuitBreakerRegistry.circuitBreaker("reservasCircuitBreaker").getEventPublisher().onEvent(event -> {
			   logger.info("State change {}", event);
		});
	    return CompletableFuture.completedFuture(fallbackResponse);
	}

}
