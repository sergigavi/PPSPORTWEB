package es.sergigavi.PPSportAPI.SERVICES

import es.sergigavi.PPSportAPI.MODEL.DTO.ClasificacionDTO
import es.sergigavi.PPSportAPI.MODEL.DTO.TorneoDTO
import es.sergigavi.PPSportAPI.MODEL.DesenlacePartido
import es.sergigavi.PPSportAPI.MODEL.EquipoTorneo
import es.sergigavi.PPSportAPI.MODEL.JugadorTorneo
import es.sergigavi.PPSportAPI.MODEL.MAPPER.toDTO
import es.sergigavi.PPSportAPI.MODEL.Torneo
import es.sergigavi.PPSportAPI.REPOSITORIES.EquipoTorneoRepository
import es.sergigavi.PPSportAPI.REPOSITORIES.JugadorTorneoRepository
import es.sergigavi.PPSportAPI.REPOSITORIES.TorneoRepository
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service
import java.util.*

@Service
class TorneoServiceImpl : ITorneoService {

    @Autowired
    lateinit var torneoDAO: TorneoRepository;

    @Autowired
    lateinit var equipoTorneo: EquipoTorneoRepository

    @Autowired
    lateinit var jugadorTorneo: JugadorTorneoRepository

    override fun add(torneo: Torneo): Pair<Boolean, Torneo?> {

        var respuesta: Pair<Boolean, Torneo?> = Pair(false, null)
        try {
            torneoDAO.save(torneo);

            respuesta = Pair(true, torneo)
        } catch (e: Exception) {
            println("Error en servicio 'Add', añadiendo un torneo nuevo." + e.printStackTrace())
        }

        return respuesta
    }

    override fun edit(torneo: Torneo): Boolean {
        var exito = false

        if (torneo.id?.let { torneoDAO.existsById(it) } == true) {
            torneoDAO.save(torneo)
            exito = true
        }

        return exito
    }

    override fun findAll(): Iterable<TorneoDTO> {
        return torneoDAO.findAll().map { it.toDTO() }

    }
    fun findAllSinDTO(): Iterable<Torneo> {
        return torneoDAO.findAll()

    }

    override fun findById(torneoID: UUID): Optional<Torneo> {
        return torneoDAO.findById(torneoID)
    }

    override fun existsById(torneoID: UUID): Boolean {
        return torneoDAO.existsById(torneoID)
    }

    override fun agregarEquipos(equipos: Iterable<EquipoTorneo>): Boolean {
        var exito = false
        try {
            equipoTorneo.saveAll(equipos)

            exito = true
        } catch (e: Exception) {
            println("Error en servicio 'Agregar Equipos a Torneo'" + e.printStackTrace())
        }

        return exito
    }

    override fun agregarJugadores(jugadores: Iterable<JugadorTorneo>): Boolean {
        var exito = false
        try {
            jugadorTorneo.saveAll(jugadores)

            exito = true
        } catch (e: Exception) {
            println("Error en servicio 'Agregar Jugadores a Torneo'" + e.printStackTrace())
        }

        return exito
    }


    override fun getClasificaciones(torneo: Torneo): Iterable<ClasificacionDTO> {
        val clasificaciones: MutableSet<ClasificacionDTO> = mutableSetOf()

        for (equipo in torneo.equiposTorneo) {
            var puntos = 0
            for (partido in equipo.equipo.equipoPartidos) {
                if (partido.desenlacePartido == DesenlacePartido.VICTORIA) {
                    puntos += 3
                } else if (partido.desenlacePartido == DesenlacePartido.EMPATE) {
                    puntos += 1
                }
            }

            clasificaciones.add(
                ClasificacionDTO(
                    equipoId = equipo.equipo.id!!,
                    torneoId = torneo.id!!,
                    nombreTorneo = torneo.nombre,
                    nombreEquipo = equipo.equipo.nombre,
                    puntos = puntos
                )
            )
        }
        return clasificaciones

    }

}