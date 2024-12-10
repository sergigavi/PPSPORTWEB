package es.sergigavi.PPSportAPI.MODEL.DTO

import es.sergigavi.PPSportAPI.MODEL.DesenlacePartido
import es.sergigavi.PPSportAPI.MODEL.Equipo
import es.sergigavi.PPSportAPI.MODEL.Partido
import jakarta.persistence.Column
import jakarta.persistence.JoinColumn
import jakarta.persistence.ManyToOne
import java.util.*

data class EquipoPartidoDTO(
    val id: UUID,
    val partidoId: UUID,
    val equipoId: UUID,
    val nombreEquipo:String,
    var resultado: String?,
    var desenlacePartido: DesenlacePartido,
    val ronda:Int
)
