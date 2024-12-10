package es.sergigavi.PPSportAPI.SERVICES

import es.sergigavi.PPSportAPI.MODEL.DTO.ReservaDTO
import es.sergigavi.PPSportAPI.MODEL.REQUEST.ReservaRequest
import es.sergigavi.PPSportAPI.MODEL.Reserva
import es.sergigavi.PPSportAPI.MODEL.Usuario
import java.time.LocalDate
import java.time.LocalDateTime
import java.time.LocalTime
import java.util.*

interface IReservaService {

    fun add(reservaRequest: ReservaRequest):  Pair<Boolean,Reserva?>;
    fun findAll(): Iterable<Reserva>;
    fun findById(reservaID: UUID): Optional<ReservaDTO>;
    fun existsById(reservaID: UUID): Boolean;
    fun deleteById(reservaID: UUID): Boolean;
    fun existsByFechaAndHoraInicioAndPistaId(fecha:LocalDate, horaInicio: LocalTime, pistaID: UUID): Boolean
    fun existsByFechaAndHoraFinAndPistaId(fecha:LocalDate, horaFin: LocalTime, pistaID: UUID): Boolean
    fun findByUsuario(usuario: Usuario):Iterable<ReservaDTO>
    fun findByFechaAndPistaId(fecha:LocalDate, pistaID: UUID):Iterable<ReservaDTO>
}