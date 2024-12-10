package es.sergigavi.PPSportAPI.SERVICES

import es.sergigavi.PPSportAPI.MODEL.Jugador
import es.sergigavi.PPSportAPI.REPOSITORIES.JugadorRepository
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.stereotype.Service
import java.util.*

@Service
class JugadorServiceImpl(private val encoder: PasswordEncoder) : IJugadorService {

    @Autowired
    lateinit var jugadorDAO: JugadorRepository;
    override fun add(jugador: Jugador): Boolean {
        var exito = false

        try {
            jugador.usuario.password = encoder.encode(jugador.usuario.password)
            this.jugadorDAO.save(jugador);
            exito = true
        }catch (e: Exception){
            println("Error en servicio 'Add', añadiendo un jugador nuevo." + e.printStackTrace())
        }

        return exito
    }

    override fun edit(jugador: Jugador): Boolean {
        var exito = false

        if (jugador.id?.let { jugadorDAO.existsById(it) } == true)
        {
            jugadorDAO.save(jugador)
            exito = true
        }

        return exito
    }

    override fun findAll(): Iterable<Jugador> {
        return  jugadorDAO.findAll()
    }

    override fun findById(jugadorID: UUID): Optional<Jugador> {
        return jugadorDAO.findById(jugadorID)
    }

    override fun findByUsuarioId(usuarioId: UUID): Optional<Jugador> {
        return jugadorDAO.findByUsuarioId(usuarioId)
    }


}