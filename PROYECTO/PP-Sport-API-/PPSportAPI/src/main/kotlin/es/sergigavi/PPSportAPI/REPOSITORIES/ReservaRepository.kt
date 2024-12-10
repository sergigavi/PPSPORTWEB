package es.sergigavi.PPSportAPI.REPOSITORIES

import es.sergigavi.PPSportAPI.MODEL.Pista
import es.sergigavi.PPSportAPI.MODEL.Reserva
import es.sergigavi.PPSportAPI.MODEL.Usuario
import org.springframework.data.domain.Sort

import org.springframework.data.repository.CrudRepository
import java.time.LocalDate
import java.time.LocalDateTime
import java.time.LocalTime
import java.util.*

interface ReservaRepository: CrudRepository<Reserva, UUID> {
    fun existsByFechaAndHoraInicioAndPistaId(fecha:LocalDate, horaInicio: LocalTime, pistaID: UUID): Boolean
    fun existsByFechaAndHoraFinAndPistaId(fecha: LocalDate, horaFin: LocalTime, pistaID: UUID): Boolean
    fun findAllByUsuario(usuario: Usuario,sort: Sort):Iterable<Reserva>
    fun findAllByFechaAndPistaId(fecha:LocalDate, pistaID: UUID, sort:Sort):Iterable<Reserva>

}
