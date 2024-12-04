package es.sergigavi.PPSportAPI.MODEL.REQUEST

import es.sergigavi.PPSportAPI.MODEL.Deporte

data class EquipoRequest(
    var nombre: String,
    val deporte: Deporte
)
