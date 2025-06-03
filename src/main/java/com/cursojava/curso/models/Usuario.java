package com.cursojava.curso.models;

import jakarta.persistence.*;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Entity
@Table(name = "Usuarios")
@ToString @EqualsAndHashCode
public class Usuario {

    @Getter @Setter @Column(name="Nombre")
    private String nombre;
    @Getter @Setter @Column(name="Apellidos")
    private String apellidos;
    @Getter @Setter @Column(name="Email")
    private String email;
    @Getter @Setter @Column(name="Telefono")
    private String telefono;
    @Getter @Setter @Column(name="Password")
    private String password;
    @Getter @Setter @Column(name="Id")
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY) // o AUTO, según el tipo de base de datos
    private Long id;


}
