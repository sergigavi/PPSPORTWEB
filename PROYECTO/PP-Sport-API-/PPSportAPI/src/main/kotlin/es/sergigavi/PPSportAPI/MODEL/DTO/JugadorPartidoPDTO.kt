package es.sergigavi.PPSportAPI.MODEL.DTO

import es.sergigavi.PPSportAPI.MODEL.DesenlacePartido
import java.util.*

data class JugadorPartidoPDTO(
    val id: UUID,
    val jugadorID: UUID,
    val nombreJugador:String,
    val desenlacePartido: DesenlacePartido,
    var resultado: String?
)
