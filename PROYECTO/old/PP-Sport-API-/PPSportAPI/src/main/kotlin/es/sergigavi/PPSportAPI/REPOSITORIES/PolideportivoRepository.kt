package es.sergigavi.PPSportAPI.REPOSITORIES

import es.sergigavi.PPSportAPI.MODEL.Polideportivo
import org.springframework.data.repository.CrudRepository
import java.util.*

interface PolideportivoRepository : CrudRepository<Polideportivo, UUID> {
}