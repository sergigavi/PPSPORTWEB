package es.sergigavi.PPSportAPI.SERVICES

import es.sergigavi.PPSportAPI.MODEL.EquipoTorneo
import es.sergigavi.PPSportAPI.MODEL.Torneo
import es.sergigavi.PPSportAPI.REPOSITORIES.EquipoTorneoRepository
import es.sergigavi.PPSportAPI.REPOSITORIES.TorneoRepository
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service
import java.util.*

@Service
class TorneoServiceImpl:ITorneoService {

    @Autowired
    lateinit var torneoDAO: TorneoRepository;

    @Autowired
    lateinit var equipoTorneo: EquipoTorneoRepository

    override fun add(torneo: Torneo): Pair<Boolean, Torneo?> {

        var respuesta:Pair<Boolean, Torneo?> = Pair(false,null)
        try {
            torneoDAO.save(torneo);

            respuesta = Pair(true,torneo)
        } catch (e: Exception) {
            println("Error en servicio 'Add', añadiendo un torneo nuevo." + e.printStackTrace())
        }

        return respuesta
    }

    override fun edit(torneo: Torneo): Boolean {
        var exito = false

        if (torneo.id?.let { torneoDAO.existsById(it) } == true){
            torneoDAO.save(torneo)
            exito = true
        }

        return exito
    }

    override fun findAll(): Iterable<Torneo> {
        return torneoDAO.findAll()
    }

    override fun findById(torneoID: UUID): Optional<Torneo> {
        return torneoDAO.findById(torneoID)
    }

    override fun existsById(torneoID: UUID): Boolean {
        return torneoDAO.existsById(torneoID)
    }

    override fun agregarEquipos(equipos: Iterable<EquipoTorneo>):Boolean {
        var exito = false
        try {
            equipoTorneo.saveAll(equipos)

            exito = true
        } catch (e: Exception) {
            println("Error en servicio 'Agregar Equipos a Torneo'" + e.printStackTrace())
        }

        return exito
    }


}