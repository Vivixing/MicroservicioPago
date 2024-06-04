package com.co.echeverri.microservicio_pago.entity;

import lombok.Data;

@Data
public class Reserva {
	private Long idReserva;
	private Long idUsuario;
    private String fechaServicio;
    private String horaServicio;
    private int duracion;
    private int numeroPersonas;
    private String tipoServicio;
    private String createReserva;
}
