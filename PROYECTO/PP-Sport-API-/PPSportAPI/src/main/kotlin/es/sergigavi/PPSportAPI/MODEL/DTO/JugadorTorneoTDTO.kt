package es.sergigavi.PPSportAPI.MODEL.DTO

import java.util.*

data class JugadorTorneoTDTO(
    val id: UUID,
    val jugadorId: UUID,
    val nombreJugador:String,
    var puntos:Int=0,
)
