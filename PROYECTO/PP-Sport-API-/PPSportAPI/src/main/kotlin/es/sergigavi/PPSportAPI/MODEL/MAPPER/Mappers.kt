package es.sergigavi.PPSportAPI.MODEL.MAPPER

import es.sergigavi.PPSportAPI.MODEL.*
import es.sergigavi.PPSportAPI.MODEL.DTO.*

//fun Torneo.toDTO():TorneoDTO{
//    return TorneoDTO(
//        id=this.id!!,
//        deporte=this.deporte,
//        nombre=this.nombre,
//        tipoTorneo=this.tipoTorneo,
//        estado=this.estado,
//        fechaInicio=this.fechaInicio,
//        fechaFin = this.fechaFin,
//        equipoTorneos = this.equipoTorneos.map { it.toDTO() }.toMutableSet(),
//        partidos = this.partidos.map { it.toDTO() }.toMutableSet()
//    )
//}
//
//fun Equipo.toDTO():EquipoDTO{
//    return EquipoDTO(
//        id=this.id!!,
//        nombre = this.nombre,
//        deporte = this.deporte,
//        equipoTorneos = this.equipoTorneos.map { it.toDTO() }.toMutableSet(),
//        equiopoPartidos = this.equipoPartidos.map { it.toDTO() }.toMutableSet()
//    )
//}
//
//fun Partido.toDTO():PartidoDTO{
//    return PartidoDTO(
//
//    )
//}
//
//fun EquipoPartido.toDTO():EquipoPartidoDTO{
//    return PartidoDTO(
//
//    )
//}

//fun EquipoTorneo.toDTO():EquipoTorneoDTO{
//    return EquipoTorneoDTO(
//
//    )
//}
fun Reserva.toDTO():ReservaDTO{
    return ReservaDTO(
        id = this.id!!,
        fecha = this.fecha,
        horaInicio=this.horaInicio,
        horaFin = this.horaFin,
        usuarioID = this.usuario.id!!,
        pista = this.pista.toDTO()
    )
}

fun Pista.toDTO():PistaDTO{
    return PistaDTO(
        id = this.id!!,
        nombre = this.nombre,
        tipoPista=this.tipoPista,
        nombrePolideportivo = this.polideportivo.nombre,
        polideportivoID = this.polideportivo.id!!
    )
}