package com.co.zamora.microservicio_reserva.service;


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

import com.co.zamora.microservicio_reserva.controller.ReservaController;
import com.co.zamora.microservicio_reserva.entity.Reserva;
import com.co.zamora.microservicio_reserva.repository.ReservaRepository;

import io.github.resilience4j.circuitbreaker.CircuitBreakerRegistry;
import io.github.resilience4j.circuitbreaker.CircuitBreaker.State;
import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import io.github.resilience4j.timelimiter.annotation.TimeLimiter;

@Service
public class ReservaServiceImpl implements ReservaService{

	@Autowired
	ReservaRepository dao;

	@Autowired
    CircuitBreakerRegistry circuitBreakerRegistry;
	 private static final Logger logger = LoggerFactory.getLogger(ReservaController.class);
	 RestTemplate restTemplate = new RestTemplate();
		
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

	@Override
	@CircuitBreaker(name = "usuariosCircuitBreaker", fallbackMethod = "usuariosFallido")
	@TimeLimiter(name = "offersTimeLimiter")
	public CompletableFuture<List<Object>> getUsuarios() throws Exception {
	    return CompletableFuture.supplyAsync(() -> {
	        Object[] usuarios = null;
	        State estado = circuitBreakerRegistry.circuitBreaker("usuariosCircuitBreaker").getState();
			logger.info("Circuit Breaker State : {} -> {}", estado);
			circuitBreakerRegistry.circuitBreaker("usuariosCircuitBreaker").getEventPublisher().onEvent(event -> {
				   logger.info("State change {}", event);
			});
	        try {
	            usuarios = restTemplate.getForObject("http://localhost:8081/obtenerUsuarios", Object[].class);
	        } catch (Exception e) {
	            throw new RuntimeException("Failed to fetch users", e);
	        }
	        return Arrays.asList(usuarios);
	    });
	}
	
	public CompletableFuture<List<Object>> usuariosFallido(Throwable throwable) {
	    List<Object> fallbackResponse = new ArrayList<>();
	    fallbackResponse.add("El microservicio de usuarios no está disponible!");
	    State estado = circuitBreakerRegistry.circuitBreaker("usuariosCircuitBreaker").getState();
		logger.info("Circuit Breaker State : {} -> {}", estado);
		circuitBreakerRegistry.circuitBreaker("usuariosCircuitBreaker").getEventPublisher().onEvent(event -> {
			   logger.info("State change {}", event);
		});
	    return CompletableFuture.completedFuture(fallbackResponse);
	}

	
}
