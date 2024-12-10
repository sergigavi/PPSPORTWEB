package es.sergigavi.PPSportAPI.SERVICES

import es.sergigavi.PPSportAPI.MODEL.DTO.ClasificacionDTO
import es.sergigavi.PPSportAPI.MODEL.DTO.TorneoDTO
import es.sergigavi.PPSportAPI.MODEL.Equipo
import es.sergigavi.PPSportAPI.MODEL.EquipoTorneo
import es.sergigavi.PPSportAPI.MODEL.JugadorTorneo
import es.sergigavi.PPSportAPI.MODEL.Torneo
import java.util.*

interface ITorneoService {
    fun add(torneo: Torneo): Pair<Boolean, Torneo?>
    fun edit(torneo: Torneo): Boolean;
    fun findAll(): Iterable<TorneoDTO>;
    fun findById(torneoID: UUID): Optional<Torneo>
    fun existsById(torneoID: UUID): Boolean;
    fun agregarEquipos(equipos:Iterable<EquipoTorneo>):Boolean
    fun agregarJugadores(jugadores:Iterable<JugadorTorneo>):Boolean
    fun getClasificaciones(torneo: Torneo):Iterable<ClasificacionDTO>
}