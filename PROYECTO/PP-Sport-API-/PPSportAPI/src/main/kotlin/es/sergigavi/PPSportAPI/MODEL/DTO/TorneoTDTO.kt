package es.sergigavi.PPSportAPI.MODEL.DTO

import es.sergigavi.PPSportAPI.MODEL.Deporte
import es.sergigavi.PPSportAPI.MODEL.Estado
import es.sergigavi.PPSportAPI.MODEL.TipoTorneo
import java.time.LocalDateTime
import java.util.*

data class TorneoTDTO(
    val id: UUID,
    val deporte: Deporte,
    var nombre: String,
    val tipoTorneo: TipoTorneo,
    var estado: Estado,
    val fechaInicio: LocalDateTime,
    val fechaFin: LocalDateTime,
)
