package com.co.zamora.microservicio_reserva.entity;

import java.util.Date; 

import jakarta.persistence.*;
import lombok.Data;


@Entity
@Table
@Data
public class Reserva {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long IdReserva;
	private Long IdUsuario;
    private String fechaServicio;
    private String horaServicio;
    private int duracion;
    private int numeroPersonas;
    private String tipoServicio;
    
    @Column(name = "create_at")
    @Temporal(TemporalType.TIMESTAMP)
    private Date createReserva;
    
    @PrePersist
    private void prePersiste(){
    	this.createReserva = new Date();
    }
}