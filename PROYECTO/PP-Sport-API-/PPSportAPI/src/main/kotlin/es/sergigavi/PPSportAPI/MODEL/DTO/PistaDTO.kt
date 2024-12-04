package es.sergigavi.PPSportAPI.MODEL.DTO

import es.sergigavi.PPSportAPI.MODEL.Deporte
import java.util.*

data class PistaDTO(
    var id: UUID,
    var nombre:String,
    var tipoPista: Deporte,
    var nombrePolideportivo:String,
    var polideportivoID: UUID,
)
