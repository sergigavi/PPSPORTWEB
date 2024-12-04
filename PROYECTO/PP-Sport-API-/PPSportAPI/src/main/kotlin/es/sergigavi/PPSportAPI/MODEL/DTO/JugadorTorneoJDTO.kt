package es.sergigavi.PPSportAPI.MODEL.DTO

import es.sergigavi.PPSportAPI.MODEL.Jugador
import es.sergigavi.PPSportAPI.MODEL.Torneo
import jakarta.persistence.*
import java.util.*

data class JugadorTorneoJDTO(
    val id: UUID,
    val torneo: TorneoTDTO,
    var puntos:Int=0,
)
