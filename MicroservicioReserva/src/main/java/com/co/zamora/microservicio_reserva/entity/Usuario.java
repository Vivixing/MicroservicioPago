package com.co.zamora.microservicio_reserva.entity;

import lombok.Data;

@Data
public class Usuario {
    private Long idUsuario;
    private String nombre;
    private String apellido;
    private String email;
    private String telefono;
    private String createAt;

}
