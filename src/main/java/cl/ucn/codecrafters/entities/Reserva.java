package cl.ucn.codecrafters.entities;

import jakarta.persistence.*;

import java.sql.Time;
import java.util.Date;

@Entity
@Table(name = "reserva")
public class Reserva {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(name = "usuario_id")
    private int usuario_id;

    @Column(name = "habitacion_id")
    private int habitacion_id;

    @Column(name = "fecha_reserva")
    private Date fecha_reserva;

    @Column(name = "hora_reserva")
    private Time hora_reserva;

    @Column(name = "fecha_llegada")
    private Date fecha_llegada;

    @Column(name = "hora_llegada")
    private Time hora_llegada;

    @Column(name = "fecha_salida")
    private Date fecha_salida;

    @Column(name = "hora_salida")
    private Time hora_salida;

    @Column(name = "confirmada")
    private boolean confirmada;
}
