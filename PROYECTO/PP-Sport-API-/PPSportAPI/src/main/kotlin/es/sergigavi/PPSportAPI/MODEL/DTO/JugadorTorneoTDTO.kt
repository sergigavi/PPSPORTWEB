package es.sergigavi.PPSportAPI.MODEL.DTO

import java.util.*

data class JugadorTorneoTDTO(
    val id: UUID,
    val jugador: JugadorDTO,
    var puntos:Int=0,
)
