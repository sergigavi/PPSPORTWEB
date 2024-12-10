package es.sergigavi.PPSportAPI.SERVICES

import es.sergigavi.PPSportAPI.MODEL.Jugador
import es.sergigavi.PPSportAPI.MODEL.Usuario
import es.sergigavi.PPSportAPI.REPOSITORIES.JugadorRepository
import java.util.Optional
import java.util.UUID

interface IJugadorService {

    fun add(jugador: Jugador): Boolean;
    fun edit(jugador: Jugador): Boolean;
    fun findAll(): Iterable<Jugador>;
    fun findById(jugadorID: UUID): Optional<Jugador>;
    fun findByUsuarioId(usuarioId: UUID):Optional<Jugador>
}