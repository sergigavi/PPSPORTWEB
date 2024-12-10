package es.sergigavi.PPSportAPI.SERVICES

import es.sergigavi.PPSportAPI.MODEL.Equipo
import es.sergigavi.PPSportAPI.REPOSITORIES.EquipoRepository
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service
import java.util.*

@Service
class EquipoServiceImpl:IEquipoService {

    @Autowired
    lateinit var equipoDAO: EquipoRepository;

    override fun add(equipo: Equipo): Pair<Boolean,Equipo?> {

        var respuesta:Pair<Boolean,Equipo?> = Pair(false,null)
            try {
                equipoDAO.save(equipo);

                respuesta = Pair(true,equipo)
            } catch (e: Exception) {
                println("Error en servicio 'Add', añadiendo un equipo nuevo." + e.printStackTrace())
            }

        return respuesta
    }

    override fun edit(equipo: Equipo): Boolean {
        var exito = false

        if (equipo.id?.let { equipoDAO.existsById(it) } == true){
            equipoDAO.save(equipo)
            exito = true
        }

        return exito
    }

    override fun findAll(): Iterable<Equipo> {
        return equipoDAO.findAll()
    }

    override fun findById(equipoID: UUID): Optional<Equipo> {
        return equipoDAO.findById(equipoID)
    }

    override fun existsById(equipoID: UUID): Boolean {
        return equipoDAO.existsById(equipoID)
    }


    override fun deleteById(equipoID: UUID): Boolean {
        val reserva: Optional<Equipo> = equipoDAO.findById(equipoID)// = Optional.empty();
        var exito = false
        if (reserva.isPresent) {
            equipoDAO.deleteById(equipoID)
            exito = true
        }
        return exito;
    }






}