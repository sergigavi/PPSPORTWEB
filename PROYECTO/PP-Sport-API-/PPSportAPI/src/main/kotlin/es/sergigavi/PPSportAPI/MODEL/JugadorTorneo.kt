package es.sergigavi.PPSportAPI.MODEL

import jakarta.persistence.*
import java.util.*

@Entity
data class JugadorTorneo(
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    val id: UUID?=null,

    @ManyToOne
    @JoinColumn(name = "torneo_id", nullable = false)
    val torneo: Torneo,

    @ManyToOne
    @JoinColumn(name = "jugador_id", nullable = false)
    val jugador: Jugador,

    @Column(name="puntos")
    var puntos:Int=0,
    )
