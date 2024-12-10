package es.sergigavi.PPSportAPI.SERVICES

import es.sergigavi.PPSportAPI.MODEL.Equipo
import es.sergigavi.PPSportAPI.MODEL.Partido
import es.sergigavi.PPSportAPI.REPOSITORIES.EquipoRepository
import es.sergigavi.PPSportAPI.REPOSITORIES.PartidoRepository
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service
import java.util.*

@Service
class PartidoServiceImpl:IPartidoService {
    @Autowired
    lateinit var partidoDAO: PartidoRepository;

    override fun add(partido: Partido): Pair<Boolean, Partido?> {

        var respuesta:Pair<Boolean, Partido?> = Pair(false,null)
        try {
            partidoDAO.save(partido);

            respuesta = Pair(true,partido)
        } catch (e: Exception) {
            println("Error en servicio 'Add', añadiendo un equipo nuevo." + e.printStackTrace())
        }

        return respuesta
    }

    override fun edit(partido: Partido): Boolean {
        var exito = false

        if (partido.id?.let { partidoDAO.existsById(it) } == true){
            partidoDAO.save(partido)
            exito = true
        }

        return exito
    }

    override fun findAll(): Iterable<Partido> {
        return partidoDAO.findAll()
    }

    override fun findAllByTorneoId(torneoId: UUID): Iterable<Partido> {
        TODO("Not yet implemented")
    }

    override fun findById(partidoID: UUID): Optional<Partido> {
        return partidoDAO.findById(partidoID)
    }

    override fun existsById(partidoID: UUID): Boolean {
        return partidoDAO.existsById(partidoID)
    }

    override fun deleteById(partidoID: UUID): Boolean {
        val reserva: Optional<Partido> = partidoDAO.findById(partidoID)// = Optional.empty();
        var exito = false
        if (reserva.isPresent) {
            partidoDAO.deleteById(partidoID)
            exito = true
        }
        return exito;
    }

}