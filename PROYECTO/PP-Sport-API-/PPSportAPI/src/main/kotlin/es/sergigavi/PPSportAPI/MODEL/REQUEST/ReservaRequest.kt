package es.sergigavi.PPSportAPI.MODEL.REQUEST
import java.time.LocalDate
import java.time.LocalDateTime
import java.time.LocalTime
import java.util.*

data class ReservaRequest(
    val fecha: LocalDate,
    val horaInicio: LocalTime,
    val horaFin: LocalTime,
    val pistaID: UUID,
    val usuarioID: UUID
)
