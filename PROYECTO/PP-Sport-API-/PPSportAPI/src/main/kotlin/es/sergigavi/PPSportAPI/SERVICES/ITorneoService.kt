package es.sergigavi.PPSportAPI.SERVICES

import es.sergigavi.PPSportAPI.MODEL.Equipo
import es.sergigavi.PPSportAPI.MODEL.EquipoTorneo
import es.sergigavi.PPSportAPI.MODEL.Torneo
import java.util.*

interface ITorneoService {
    fun add(torneo: Torneo): Pair<Boolean, Torneo?>
    fun edit(torneo: Torneo): Boolean;
    fun findAll(): Iterable<Torneo>;
    fun findById(torneoID: UUID): Optional<Torneo>
    fun existsById(torneoID: UUID): Boolean;
    fun agregarEquipos(equipos:Iterable<EquipoTorneo>):Boolean
}