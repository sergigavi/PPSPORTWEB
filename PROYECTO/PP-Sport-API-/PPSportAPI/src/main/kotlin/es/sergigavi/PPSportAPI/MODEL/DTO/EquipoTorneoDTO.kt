package es.sergigavi.PPSportAPI.MODEL.DTO

import java.util.*

data class EquipoTorneoDTO(

    val id: UUID,
    val torneo: TorneoDTO,
    val equipo: EquipoDTO,
    var puntos:Int,
)
