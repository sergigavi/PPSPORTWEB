package es.sergigavi.PPSportAPI.MODEL.DTO

import es.sergigavi.PPSportAPI.MODEL.Deporte
import es.sergigavi.PPSportAPI.MODEL.EquipoPartido
import es.sergigavi.PPSportAPI.MODEL.EquipoTorneo
import es.sergigavi.PPSportAPI.MODEL.Jugador
import jakarta.persistence.*
import java.util.*

data class EquipoDTO(
    var id: UUID,
    var nombre: String,
    val deporte: Deporte,
    val jugadores: MutableSet<JugadorDTO>,
    val equipoTorneos: MutableSet<EquipoTorneoDTO>,
    val equipoPartidos: MutableSet<EquipoPartidoDTO>

)
