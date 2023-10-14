package cl.ucn.codecrafters.entities;

import jakarta.persistence.*;

@Entity
@Table(name = "habitacion")
public class Habitacion {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(name = "capacidad_maxima_personas")
    private int capacidad_maxima_personas;

    @Column(name = "cant_camas_individuales")
    private int cant_camas_individuales;

    @Column(name = "cant_camas_dobles")
    private int cant_camas_dobles;

    @Column(name = "tipo_bano")
    private int tipo_bano;

    @Column(name = "precio_por_noche")
    private double precio_por_noche;
}
