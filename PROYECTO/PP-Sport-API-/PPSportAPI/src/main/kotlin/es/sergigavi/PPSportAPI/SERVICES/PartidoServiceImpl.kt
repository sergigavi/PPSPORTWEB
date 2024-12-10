package es.sergigavi.PPSportAPI.SERVICES

import es.sergigavi.PPSportAPI.MODEL.DTO.PartidoDTO
import es.sergigavi.PPSportAPI.MODEL.Equipo
import es.sergigavi.PPSportAPI.MODEL.EquipoPartido
import es.sergigavi.PPSportAPI.MODEL.MAPPER.toDTO
import es.sergigavi.PPSportAPI.MODEL.Partido
import es.sergigavi.PPSportAPI.REPOSITORIES.EquipoPartidoRepository
import es.sergigavi.PPSportAPI.REPOSITORIES.EquipoRepository
import es.sergigavi.PPSportAPI.REPOSITORIES.PartidoRepository
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service
import java.util.*

@Service
class PartidoServiceImpl : IPartidoService {
    @Autowired
    lateinit var partidoDAO: PartidoRepository

    @Autowired
    lateinit var equipoPartidoDAO: EquipoPartidoRepository

    override fun add(partido: Partido): Pair<Boolean, Partido?> {

        var respuesta: Pair<Boolean, Partido?> = Pair(false, null)
        try {
            partidoDAO.save(partido);

            respuesta = Pair(true, partido)
        } catch (e: Exception) {
            println("Error en servicio 'Add', añadiendo un partido nuevo." + e.printStackTrace())
        }

        return respuesta
    }

    override fun addAll(partidos: Iterable<Partido>): Pair<Boolean, Iterable<Partido>> {

        var respuesta: Pair<Boolean, Iterable<Partido>> = Pair(false, mutableSetOf())

        try {
            partidoDAO.saveAll(partidos);

            respuesta = Pair(true, partidos)
        } catch (e: Exception) {
            println("Error en servicio 'Add', añadiendo unos partidos nuevos." + e.printStackTrace())
        }

        return respuesta
    }

    override fun findAllEquipoPartidoByPartidoId(partidoID: UUID): Iterable<EquipoPartido> {
        return equipoPartidoDAO.findAllByPartidoId(partidoID)
    }

    override fun findAllEquipoPartidoByEquipoId(equipoID: UUID): Iterable<EquipoPartido> {
       return equipoPartidoDAO.findAllByEquipoId(equipoID)
    }

    override fun editEquipoPartido(equipoPartido: EquipoPartido): Boolean {
        var exito = false

        if (equipoPartido.id?.let { partidoDAO.existsById(it) } == true) {
            equipoPartidoDAO.save(equipoPartido)
            exito = true
        }

        return exito
    }

    override fun edit(partido: Partido): Boolean {
        var exito = false

        if (partido.id?.let { partidoDAO.existsById(it) } == true) {
            partidoDAO.save(partido)
            exito = true
        }

        return exito
    }

    override fun findAll(): Iterable<Partido> {
        return partidoDAO.findAll()
    }

    override fun findAllByTorneoId(torneoId: UUID): Iterable<Partido> {
        return partidoDAO.findAllByTorneoId(torneoId)
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

    override fun guardarEquiposPartido(equipos: Iterable<EquipoPartido>): Boolean {
        var exito = false
        try {
            equipoPartidoDAO.saveAll(equipos)
            exito = true
        } catch (e: Exception) {
            println("Error en servicio 'crearEquiposPartido', guardando nuevas entidades." + e.printStackTrace())

        }
        return exito
    }

}