package com.co.echeverri.microservicio_pago.entity;

import lombok.Data;

@Data
public class Reserva {
	private Long IdReserva;
	private Long IdUsuario;
	private String fechaReserva;
    private String fechaServicio;
    private String horaServicio;
    private int duracion;
    private int numeroPersonas;
    private String tipoServicio;
    private String numeroReserva;
}
