package es.sergigavi.PPSportAPI.MODEL.REQUEST

import es.sergigavi.PPSportAPI.MODEL.Deporte
import es.sergigavi.PPSportAPI.MODEL.TipoTorneo
import java.time.LocalDateTime

data class TorneoRequest(
    val deporte: Deporte,
    val nombre: String,
    val tipoTorneo: TipoTorneo,
    val fechaInicio: LocalDateTime,
    val fechaFin: LocalDateTime,
)
