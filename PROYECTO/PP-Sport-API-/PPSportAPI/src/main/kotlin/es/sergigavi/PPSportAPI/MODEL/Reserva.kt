package es.sergigavi.PPSportAPI.MODEL

import jakarta.persistence.*
import java.time.LocalDate
import java.time.LocalDateTime
import java.time.LocalTime
import java.util.*

@Entity
data class Reserva(
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    val id: UUID?=null,

    @Column(name = "fecha", unique = false, nullable = false)
    val fecha:LocalDate,

    @Column(name = "hora_inicio", unique = false, nullable = false)
    val horaInicio:LocalTime,

    @Column(name = "hora_fin", unique = false, nullable = false)
    val horaFin:LocalTime,

    @ManyToOne(optional = false, cascade = [CascadeType.ALL], fetch = FetchType.EAGER)
    val pista: Pista,

    @ManyToOne(optional = false, cascade = [CascadeType.ALL], fetch = FetchType.EAGER)
    val usuario: Usuario

    )
