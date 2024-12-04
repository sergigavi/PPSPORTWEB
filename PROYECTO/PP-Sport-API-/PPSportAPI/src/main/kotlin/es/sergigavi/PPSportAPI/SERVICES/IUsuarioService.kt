package es.sergigavi.PPSportAPI.SERVICES

import es.sergigavi.PPSportAPI.MODEL.Usuario

import java.util.Optional
import java.util.UUID

interface IUsuarioService {

    fun add(usuario: Usuario): Boolean;
    fun edit(usuario: Usuario): Boolean;
    fun findAll(): Iterable<Usuario>;
    fun findById(usuarioID: UUID): Optional<Usuario>;
    fun existsById(usuarioID: UUID): Boolean;
    fun deleteById(usuarioID: UUID): Optional<Usuario>;
    fun existsByEmail(email: String): Boolean
    fun findByEmail(email: String): Optional<Usuario>
    fun findByDniEmail(dni: String): Optional<Usuario>
    fun existsByDni(dni: String): Boolean


}