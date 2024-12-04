package es.sergigavi.PPSportAPI.SERVICES

import es.sergigavi.PPSportAPI.MODEL.Usuario

import es.sergigavi.PPSportAPI.REPOSITORIES.UsuarioRepository
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.stereotype.Service
import java.util.*

@Service
class UsuarioServiceImpl(private val encoder:PasswordEncoder): IUsuarioService {
    @Autowired
    lateinit var usuarioDAO: UsuarioRepository;

    override fun add(usuario: Usuario): Boolean {
        val encodedUser = usuario.copy(password = encoder.encode(usuario.password))
        var exito = false

        try {
            this.usuarioDAO.save(encodedUser);
            exito = true
        }catch (e: Exception){
            println("Error en servicio 'Add', añadiendo un jugador nuevo." + e.printStackTrace())
        }

        return exito

    }

    override fun edit(usuario: Usuario): Boolean {
        val encodedUser = usuario.copy(password = encoder.encode(usuario.password))

        var exito = false

        if (encodedUser.id?.let { usuarioDAO.existsById(it) } == true)
        {
            usuarioDAO.save(encodedUser)
            exito = true
        }

        return exito
    }

    override fun findAll(): Iterable<Usuario> {
        return usuarioDAO.findAll()
    }

    override fun findById(usuarioID: UUID): Optional<Usuario> {
        return usuarioDAO.findById(usuarioID)
    }

    override fun existsById(usuarioID: UUID): Boolean {
        return usuarioDAO.existsById(usuarioID)
    }

    override fun deleteById(usuarioID: UUID): Optional<Usuario> {

        val usuario : Optional<Usuario> = usuarioDAO.findById(usuarioID)// = Optional.empty();

        if (usuario.isPresent)
        {
            usuarioDAO.deleteById(usuarioID)
        }
        return usuario;

    }

    override fun existsByEmail(email: String): Boolean {
        return usuarioDAO.existsByEmail(email)
    }

    override fun findByEmail(email: String): Optional<Usuario> {
        return usuarioDAO.findByEmail(email)
    }

    override fun existsByDni(dni: String): Boolean {
        return usuarioDAO.existsByDni(dni)
    }

    override fun findByDniEmail(dni: String): Optional<Usuario> {
        return usuarioDAO.findByDni(dni)
    }

}