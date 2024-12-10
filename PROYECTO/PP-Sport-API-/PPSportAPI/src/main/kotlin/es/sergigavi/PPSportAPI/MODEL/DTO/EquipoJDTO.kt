package es.sergigavi.PPSportAPI.MODEL.DTO

import es.sergigavi.PPSportAPI.MODEL.Deporte
import es.sergigavi.PPSportAPI.MODEL.EquipoPartido
import java.util.*

data class EquipoJDTO(
    var id: UUID,
    var nombre: String,
    val deporte: Deporte,
)
