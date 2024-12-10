package es.sergigavi.PPSportAPI.MODEL.DTO

import es.sergigavi.PPSportAPI.MODEL.*
import java.util.*

data class JugadorDTO (
    val id: UUID,
    val deporteFavorito: Deporte?,
    val equipos:MutableSet<EquipoDTO>?,
    val polideportivoAsociadoId: UUID?,
    val usuarioID: UUID,
    val torneos:MutableSet<JugadorTorneoJDTO>,
    val jugadorPartido :MutableSet<JugadorPartidoJDTO>

)