package es.sergigavi.PPSportAPI.MODEL.DTO

import java.util.*

data class EquipoTorneoDTO(

    val id: UUID,
    val torneoId: UUID?,
    val equipoId: UUID?,
    val nombreEquipo:String?,
    var puntos:Int,
)
