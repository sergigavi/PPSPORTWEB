package es.sergigavi.PPSportAPI.MODEL.DTO

import es.sergigavi.PPSportAPI.MODEL.DesenlacePartido
import java.util.*

data class JugadorPartidoJDTO(
    val id: UUID,
    val partidoID: UUID,
    val desenlacePartido: DesenlacePartido,
    var resultado: String?
)