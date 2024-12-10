package es.sergigavi.PPSportAPI.SERVICES

import es.sergigavi.PPSportAPI.MODEL.Polideportivo
import es.sergigavi.PPSportAPI.REPOSITORIES.PolideportivoRepository
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service
import java.util.*

@Service
class PolideportivoServiceImpl: IPolideportivoService {
    @Autowired
    lateinit var polideportivoDAO: PolideportivoRepository;

    override fun add(polideportivo: Polideportivo): Boolean {

        var exito = false

        try {
            this.polideportivoDAO.save(polideportivo);
            exito = true
        }catch (e: Exception){
            println("Error en servicio 'Add', añadiendo un polideportivo nuevo." + e.printStackTrace())
        }

        return exito

    }

    override fun edit(polideportivo: Polideportivo): Boolean {
        var exito = false

        if (polideportivo.id?.let { polideportivoDAO.existsById(it) } == true){
            polideportivoDAO.save(polideportivo)
            exito = true
        }

        return exito
    }

    override fun findAll(): Iterable<Polideportivo> {
        return polideportivoDAO.findAll()
    }

    override fun findById(polideportivoID: UUID): Optional<Polideportivo> {
        return polideportivoDAO.findById(polideportivoID)
    }

    override fun deleteById(polideportivoID: UUID): Boolean {
        val polideportivo : Optional<Polideportivo> = polideportivoDAO.findById(polideportivoID)// = Optional.empty();
        var exito = false

        if (polideportivo.isPresent) {
            polideportivoDAO.deleteById(polideportivoID)
            exito = true
        }
        return exito;
    }

    override fun existsById(polideportivoID: UUID): Boolean {
        return polideportivoDAO.existsById(polideportivoID)
    }

    override fun saveAll(polideportivos: List<Polideportivo>): Iterable<Polideportivo> {

        return try {
            polideportivoDAO.saveAll(polideportivos)
        }catch (e:Exception){
            listOf<Polideportivo>().asIterable()
        }
    }
}