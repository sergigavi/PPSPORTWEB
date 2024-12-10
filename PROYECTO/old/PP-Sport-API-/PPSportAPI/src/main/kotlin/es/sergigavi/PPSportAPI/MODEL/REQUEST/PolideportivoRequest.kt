package es.sergigavi.PPSportAPI.MODEL.REQUEST

import java.time.LocalTime

data class PolideportivoRequest(
    var nombre: String,
    var direccion: String,
    var localidad: String,
    var cp: String,
    var horaApertura: LocalTime,
    var horaCierre: LocalTime,
)
