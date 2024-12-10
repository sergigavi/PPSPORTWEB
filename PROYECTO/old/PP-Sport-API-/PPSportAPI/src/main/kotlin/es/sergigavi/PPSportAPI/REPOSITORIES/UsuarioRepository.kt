package es.sergigavi.PPSportAPI.REPOSITORIES

import es.sergigavi.PPSportAPI.MODEL.Usuario
import org.springframework.data.repository.CrudRepository
import java.util.*

interface UsuarioRepository: CrudRepository<Usuario, UUID> {

    fun existsByEmail(email: String): Boolean
    fun findByEmail(email: String): Optional<Usuario>
    fun existsByDni(dni: String): Boolean
    fun findByDni(dni: String): Optional<Usuario>

}