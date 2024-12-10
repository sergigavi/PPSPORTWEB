package es.sergigavi.PPSportAPI.MODEL.REQUEST

import java.time.LocalDateTime
import java.util.UUID

data class PartidoRequest(
    val fecha:LocalDateTime?,
    val resultado:String?,
    val equipoPerdedor:UUID,
    val equipoGanador:UUID

)