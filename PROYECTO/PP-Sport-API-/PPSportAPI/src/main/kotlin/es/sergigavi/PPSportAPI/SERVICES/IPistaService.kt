package es.sergigavi.PPSportAPI.SERVICES

import es.sergigavi.PPSportAPI.MODEL.DTO.PistaDTO
import es.sergigavi.PPSportAPI.MODEL.Pista
import java.util.*

interface IPistaService {

    fun add(pista: Pista): Boolean;
    fun edit(pista: Pista): Boolean;
    fun findAll(): Iterable<PistaDTO>;
    fun findAllByPolideportivoId(polideportivoId:UUID):Iterable<PistaDTO>
    fun findById(pistaID: UUID): Optional<Pista>;
    fun existsById(pistaID: UUID): Boolean;
    fun deleteById(pistaID: UUID): Boolean;
}