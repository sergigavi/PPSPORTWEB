package es.sergigavi.PPSportAPI.SERVICES

import es.sergigavi.PPSportAPI.MODEL.DTO.ReservaDTO
import es.sergigavi.PPSportAPI.MODEL.MAPPER.toDTO
import es.sergigavi.PPSportAPI.MODEL.REQUEST.ReservaRequest
import es.sergigavi.PPSportAPI.MODEL.Reserva
import es.sergigavi.PPSportAPI.MODEL.Usuario
import es.sergigavi.PPSportAPI.REPOSITORIES.PistaRepository
import es.sergigavi.PPSportAPI.REPOSITORIES.ReservaRepository
import es.sergigavi.PPSportAPI.REPOSITORIES.UsuarioRepository
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service
import java.time.LocalDate
import java.time.LocalDateTime
import java.time.LocalTime
import java.util.*

@Service
class ReservaServiceImpl : IReservaService {
    @Autowired
    lateinit var reservaDAO: ReservaRepository;

    @Autowired
    lateinit var usuarioDAO: UsuarioRepository;

    @Autowired
    lateinit var pistaDAO: PistaRepository;

    override fun add(reservaRequest: ReservaRequest): Pair<Boolean,Reserva?> {

        var exito = false
        var respuesta:Pair<Boolean,Reserva?> = Pair(false,null)
        var usuario = Optional.empty<Usuario>()
        val usuarios = usuarioDAO.findAll()

        if(usuarioDAO.existsById(reservaRequest.usuarioID)){

            usuario = usuarioDAO.findById(reservaRequest.usuarioID)
        }

        val pista = pistaDAO.findById(reservaRequest.pistaID)

        if(usuario.isPresent && pista.isPresent){
            val reserva = Reserva(
                id = null,
                fecha = reservaRequest.fecha,
                horaInicio = reservaRequest.horaInicio,
                horaFin = reservaRequest.horaFin,
                usuario = usuario.get(),
                pista = pista.get()
            )
            try {
                reservaDAO.save(reserva);


                respuesta = Pair(true,reserva)
            } catch (e: Exception) {
                println("Error en servicio 'Add', añadiendo un jugador nuevo." + e.printStackTrace())
            }
        }else{
            println("Error, usuario o pista no encontrados")
        }

        return respuesta

    }

    override fun findAll(): Iterable<Reserva> {
        return reservaDAO.findAll()
    }

    override fun findById(reservaID: UUID): Optional<ReservaDTO> {
        return reservaDAO.findById(reservaID).map { it.toDTO() }
    }

    override fun existsById(reservaID: UUID): Boolean {
        return reservaDAO.existsById(reservaID)

    }

    override fun deleteById(reservaID: UUID): Boolean {
        val reserva: Optional<Reserva> = reservaDAO.findById(reservaID)// = Optional.empty();
        var exito = false
        if (reserva.isPresent) {
            reservaDAO.deleteById(reservaID)
            exito = true
        }
        return exito;
    }


    override fun existsByFechaAndHoraInicioAndPistaId(fecha: LocalDate, horaInicio: LocalTime, pistaID: UUID): Boolean {
        return reservaDAO.existsByFechaAndHoraInicioAndPistaId(fecha,horaInicio, pistaID)
    }

    override fun existsByFechaAndHoraFinAndPistaId(fecha: LocalDate, horaFin: LocalTime, pistaID: UUID): Boolean {
        return reservaDAO.existsByFechaAndHoraFinAndPistaId(fecha, horaFin, pistaID)
    }


    override fun findByUsuario(usuario: Usuario): Iterable<ReservaDTO> {
        return reservaDAO.findByUsuario(usuario).map { it.toDTO() }
    }

    override fun findByFechaAndPistaId(fecha: LocalDate, pistaID: UUID): Iterable<ReservaDTO> {
        return reservaDAO.findByFechaAndPistaId(fecha, pistaID).map { it.toDTO() }
    }


}