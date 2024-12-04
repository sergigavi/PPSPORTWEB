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
data class EquipoTorneo(
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    val id: UUID? = null,

    @ManyToOne
    @JoinColumn(name = "torneo_id")
    val torneo: Torneo,

    @ManyToOne
    @JoinColumn(name = "equipo_id")
    val equipo: Equipo,

    @Column(name="puntos")
    var puntos:Int=0,

    )
