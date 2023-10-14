package cl.ucn.codecrafters.entities;

import jakarta.persistence.*;

@Entity
@Table(name= "rol")
public class Rol {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(name = "nombre_rol")
    private String nombre_rol;
}
