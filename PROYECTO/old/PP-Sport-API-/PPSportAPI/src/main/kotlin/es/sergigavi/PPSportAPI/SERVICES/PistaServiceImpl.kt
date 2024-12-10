package es.sergigavi.PPSportAPI.SERVICES

import es.sergigavi.PPSportAPI.MODEL.DTO.PistaDTO
import es.sergigavi.PPSportAPI.MODEL.MAPPER.toDTO
import es.sergigavi.PPSportAPI.MODEL.Pista
import es.sergigavi.PPSportAPI.REPOSITORIES.PistaRepository
import es.sergigavi.PPSportAPI.REPOSITORIES.PolideportivoRepository
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service
import java.util.*

@Service
class PistaServiceImpl:IPistaService {
    @Autowired
    lateinit var pistaDAO: PistaRepository;
    @Autowired
    lateinit var polideportivoDAO: PolideportivoRepository;


    override fun add(pista: Pista): Boolean {

        var exito = false

        try {
            pistaDAO.save(pista);
            val polideportivo = pista.polideportivo
            polideportivo.pistas.add(pista)
            polideportivoDAO.save(polideportivo)

            exito = true
        }catch (e: Exception){
            println("Error en servicio 'Add', añadiendo un polideportivo nuevo." + e.printStackTrace())
        }

        return exito
    }

    override fun edit(pista: Pista): Boolean {
        var exito = false

        if (pista.id?.let { pistaDAO.existsById(it) } == true)
        {
            pistaDAO.save(pista)
            exito = true
        }

        return exito
    }

    override fun findAll(): Iterable<Pista> {
        return pistaDAO.findAll()
    }

    override fun findAllByPolideportivoId(polideportivoId: UUID): Iterable<PistaDTO> {
        return pistaDAO.findAllByPolideportivoId(polideportivoId).map { it.toDTO() }
    }

    override fun findById(pistaID: UUID): Optional<Pista> {
        return pistaDAO.findById(pistaID)
    }

    override fun existsById(pistaID: UUID): Boolean {
        return pistaDAO.existsById(pistaID)
    }

    override fun deleteById(pistaID: UUID): Boolean {
        TODO("Not yet implemented")
    }
}