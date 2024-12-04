package es.sergigavi.PPSportAPI.MODEL

import jakarta.persistence.Column
import jakarta.persistence.Entity
import jakarta.persistence.GeneratedValue
import jakarta.persistence.GenerationType
import jakarta.persistence.Id
import jakarta.persistence.JoinColumn
import jakarta.persistence.ManyToOne
import java.util.UUID

@Entity
data class JugadorPartido(
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    val id:UUID?=null,

    @ManyToOne
    @JoinColumn(name = "partido_id", nullable = false)
    val partido: Partido,

    @ManyToOne
    @JoinColumn(name = "jugador_id", nullable = false)
    val jugador: Jugador,

    @Column(name = "desenlace_partido")
    val desenlacePartido: DesenlacePartido = DesenlacePartido.EMPATE,

    @Column(name = "resultado")
    var resultado: String? = null,
    )
