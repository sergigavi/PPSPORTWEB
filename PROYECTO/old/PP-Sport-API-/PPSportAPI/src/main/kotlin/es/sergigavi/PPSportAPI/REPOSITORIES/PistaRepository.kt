package es.sergigavi.PPSportAPI.REPOSITORIES

import es.sergigavi.PPSportAPI.MODEL.Pista
import es.sergigavi.PPSportAPI.MODEL.Polideportivo
import org.springframework.data.repository.CrudRepository
import java.util.*

interface PistaRepository: CrudRepository<Pista, UUID> {
    fun findAllByPolideportivoId(polideportivoID: UUID):Iterable<Pista>
}