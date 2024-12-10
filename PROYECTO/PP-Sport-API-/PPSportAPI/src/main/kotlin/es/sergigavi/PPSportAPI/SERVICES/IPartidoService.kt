package es.sergigavi.PPSportAPI.SERVICES

import es.sergigavi.PPSportAPI.MODEL.EquipoPartido
import es.sergigavi.PPSportAPI.MODEL.Partido
import es.sergigavi.PPSportAPI.MODEL.Pista
import java.util.*

interface IPartidoService {
    fun add(partido: Partido): Pair<Boolean,Partido?>;
    fun edit(partido: Partido): Boolean;
    fun findAll(): Iterable<Partido>;
    fun findAllByTorneoId(torneoId: UUID):Iterable<Partido>
    fun findById(partidoID: UUID): Optional<Partido>;
    fun existsById(partidoID: UUID): Boolean;
    fun deleteById(partidoID: UUID): Boolean;
    fun guardarEquiposPartido(equipos:Iterable<EquipoPartido>):Boolean
    fun addAll(partidos: Iterable<Partido>):Pair<Boolean, Iterable<Partido>>
    fun findAllEquipoPartidoByPartidoId(partidoID: UUID):Iterable<EquipoPartido>
    fun findAllEquipoPartidoByEquipoId(equipoID: UUID):Iterable<EquipoPartido>
    fun editEquipoPartido(equipoPartido: EquipoPartido):Boolean

}