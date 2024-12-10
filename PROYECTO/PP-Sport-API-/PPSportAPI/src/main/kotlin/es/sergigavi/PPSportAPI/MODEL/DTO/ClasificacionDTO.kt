package es.sergigavi.PPSportAPI.MODEL.DTO

import java.util.UUID

data class ClasificacionDTO(
    val nombreEquipo:String,
    val nombreTorneo:String,
    val puntos:Int,
    val torneoId:UUID,
    val equipoId:UUID

)
