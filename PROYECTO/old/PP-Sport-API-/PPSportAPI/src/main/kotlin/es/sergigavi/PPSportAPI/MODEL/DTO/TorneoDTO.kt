package es.sergigavi.PPSportAPI.MODEL.DTO

import es.sergigavi.PPSportAPI.MODEL.*
import java.time.LocalDateTime
import java.util.*

data class TorneoDTO(

    val id: UUID,
    val deporte: Deporte,
    var nombre: String,
    val tipoTorneo: TipoTorneo,
    var estado: Estado,
    val fechaInicio: LocalDateTime,
    val fechaFin: LocalDateTime,
    val partidos: MutableSet<PartidoDTO>,
    val equipoTorneos: MutableSet<EquipoTorneoDTO>,
    val jugadores: MutableSet<JugadorTorneoTDTO>
    )
