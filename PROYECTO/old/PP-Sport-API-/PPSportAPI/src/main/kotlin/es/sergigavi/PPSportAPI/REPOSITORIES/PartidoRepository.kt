package es.sergigavi.PPSportAPI.REPOSITORIES

import es.sergigavi.PPSportAPI.MODEL.Partido
import es.sergigavi.PPSportAPI.MODEL.Pista
import org.springframework.data.repository.CrudRepository
import java.util.UUID

interface PartidoRepository:CrudRepository<Partido,UUID> {
    fun findAllByTorneoId(torneoID: UUID):Iterable<Partido>

}