package es.sergigavi.PPSportAPI.SERVICES

import es.sergigavi.PPSportAPI.MODEL.Equipo
import java.util.*

interface IEquipoService {

    fun add(equipo: Equipo): Pair<Boolean,Equipo?>
    fun edit(equipo: Equipo): Boolean;
    fun findAll(): Iterable<Equipo>;
    fun findById(equipoID: UUID): Optional<Equipo>
    fun deleteById(equipoID: UUID): Boolean;
    fun existsById(equipoID: UUID): Boolean;
}