package es.sergigavi.PPSportAPI.MODEL.MAPPER

import es.sergigavi.PPSportAPI.MODEL.*
import es.sergigavi.PPSportAPI.MODEL.DTO.*

fun Torneo.toDTO():TorneoDTO{
    return TorneoDTO(
        id=this.id,
        deporte=this.deporte,
        nombre=this.nombre,
        tipoTorneo=this.tipoTorneo,
        estado=this.estado,
        fechaInicio=this.fechaInicio,
        fechaFin = this.fechaFin,
        equiposTorneo = this.equiposTorneo.map { it.toDTO() }.toMutableSet(),
        partidos = this.partidos.map { it.toDTO() }.toMutableSet(),
        jugadores = this.jugadoresTorneo.map { it.toTDTO() }.toMutableSet()
    )
}

fun Torneo.toTDTO():TorneoTDTO{
    return TorneoTDTO(
        id = this.id!!,
        fechaInicio = this.fechaInicio,
        fechaFin = this.fechaFin,
        tipoTorneo = this.tipoTorneo,
        estado = this.estado,
        deporte = this.deporte,
        nombre = this.nombre
    )

}
//
fun Equipo.toDTO():EquipoDTO{
    return EquipoDTO(
        id=this.id!!,
        nombre = this.nombre,
        deporte = this.deporte,
        equipoTorneos = this.equipoTorneos.map { it.toDTO() }.toMutableSet(),
        equipoPartidos = this.equipoPartidos.map { it.toDTO() }.toMutableSet(),
        jugadores = this.jugadores.map { it.toDTO() }.toMutableSet()
    )
}

fun Equipo.toJDTO():EquipoJDTO{
    return EquipoJDTO(
        id = this.id!!,
        nombre = this.nombre,
        deporte = this.deporte
    )
}

fun Jugador.toDTO():JugadorDTO{
    return JugadorDTO(
        id = this.id!!,
        deporteFavorito = this.deporteFavorito,
        usuarioID = this.usuario.id!!,
        equipos = this.equipos.map { it.toJDTO() }.toMutableSet(),
        jugadorPartido = this.jugadorPartido.map { it.toJDTO() }.toMutableSet(),
        torneos = this.torneos.map { it.toJDTO() }.toMutableSet(),
        polideportivoAsociadoId = this.polideportivoAsociado?.id


    )
}



fun JugadorPartido.toJDTO():JugadorPartidoJDTO{
    return JugadorPartidoJDTO(
        id = this.id!!,
        desenlacePartido = this.desenlacePartido,
        partidoID = this.partido.id!!,
        resultado = this.resultado
    )
}

fun JugadorPartido.toPDTO():JugadorPartidoPDTO{
    return JugadorPartidoPDTO(
        id = this.id!!,
        desenlacePartido = this.desenlacePartido,
        jugadorID = this.jugador.id!!,
        nombreJugador = this.jugador.usuario.nombre,
        resultado = this.resultado
    )
}

fun JugadorTorneo.toJDTO():JugadorTorneoJDTO{
    return JugadorTorneoJDTO(
        id = this.id!!,
        torneo = torneo.toTDTO(),
        puntos = this.puntos
    )
}

fun JugadorTorneo.toTDTO():JugadorTorneoTDTO{
    return JugadorTorneoTDTO(
        id = this.id!!,
        jugadorId = jugador.id!!,
        nombreJugador = jugador.usuario.nombre+ " " + jugador.usuario.apellido1+ " " + jugador.usuario.apellido2.orEmpty(),
        puntos = this.puntos
    )
}




fun Partido.toDTO():PartidoDTO{
    return PartidoDTO(
        id = this.id!!,
        fecha = this.fecha,
        resultado = this.resultado,
        ronda = this.ronda,
        nombreTorneo = this.torneo?.nombre,
        torneoID = this.torneo?.id,
        jugadoresPartidos = this.jugadoresPartidos.map { it.toPDTO() }.toMutableSet(),
        equiposPartidos = this.equiposPartidos.map { it.toDTO() }.toMutableSet()
    )
}

fun EquipoPartido.toDTO():EquipoPartidoDTO{
    return EquipoPartidoDTO(
        id= this.id!!,
        partidoId =  this.partido.id!!,
        equipoId = this.equipo.id!!,
        nombreEquipo = this.equipo.nombre,
        resultado = this.resultado,
        desenlacePartido = this.desenlacePartido,
        ronda = this.partido.ronda
    )
}

fun EquipoTorneo.toDTO():EquipoTorneoDTO{
    return EquipoTorneoDTO(
        id = this.id!!,
        torneoId = this.torneo.id,
        equipoId = this.equipo.id,
        nombreEquipo = this.equipo.nombre,
        puntos = this.puntos
    )
}
fun Reserva.toDTO(): ReservaDTO {
    return ReservaDTO(
        id = this.id!!,
        fecha = this.fecha,
        horaInicio = this.horaInicio,
        horaFin = this.horaFin,
        usuarioID = this.usuario.id!!,
        pista = this.pista.toDTO()
    )
}

fun Pista.toDTO(): PistaDTO {
    return PistaDTO(
        id = this.id!!,
        nombre = this.nombre,
        tipoPista = this.tipoPista,
        nombrePolideportivo = this.polideportivo.nombre,
        polideportivoID = this.polideportivo.id!!
    )
}

