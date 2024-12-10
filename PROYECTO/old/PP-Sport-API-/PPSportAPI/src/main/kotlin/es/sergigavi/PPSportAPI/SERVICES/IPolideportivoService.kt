package es.sergigavi.PPSportAPI.SERVICES

import es.sergigavi.PPSportAPI.MODEL.Polideportivo
import java.util.Optional
import java.util.UUID

interface IPolideportivoService {

    fun add(polideportivo: Polideportivo): Boolean;
    fun edit(polideportivo: Polideportivo): Boolean;
    fun findAll(): Iterable<Polideportivo>;
    fun findById(polideportivoID: UUID):Optional<Polideportivo>
    fun deleteById(polideportivoID: UUID): Boolean;
    fun existsById(polideportivoID: UUID): Boolean;
    fun saveAll(polideportivos: List<Polideportivo>): Iterable<Polideportivo>
}