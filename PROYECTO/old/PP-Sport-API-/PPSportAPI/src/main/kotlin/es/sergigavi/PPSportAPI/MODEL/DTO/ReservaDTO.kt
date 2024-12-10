package es.sergigavi.PPSportAPI.MODEL.DTO

import es.sergigavi.PPSportAPI.MODEL.Pista
import es.sergigavi.PPSportAPI.MODEL.Usuario
import jakarta.persistence.*
import java.time.LocalDate
import java.time.LocalDateTime
import java.time.LocalTime
import java.util.*

data class ReservaDTO(
    val id: UUID,
    val fecha: LocalDate,
    val horaInicio: LocalTime,
    val horaFin: LocalTime,
    val pista: PistaDTO,
    val usuarioID: UUID
)
