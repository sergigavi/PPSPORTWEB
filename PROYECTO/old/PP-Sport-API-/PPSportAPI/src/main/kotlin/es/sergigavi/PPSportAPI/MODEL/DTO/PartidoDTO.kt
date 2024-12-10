package es.sergigavi.PPSportAPI.MODEL.DTO

import java.time.LocalDateTime
import java.util.*

data class PartidoDTO(
    val id: UUID,
    val fecha: LocalDateTime,
    val jugadoresPartidos :MutableSet<JugadorPartidoJDTO>,
    val equiposPartidos:MutableSet<EquipoPartidoDTO>,
    val torneoID: UUID?,
    val nombreTorneo:String,
    var resultado:String?,
)