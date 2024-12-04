package es.sergigavi.PPSportAPI.REPOSITORIES

import es.sergigavi.PPSportAPI.MODEL.Jugador
import org.springframework.data.repository.CrudRepository
import java.util.*

interface JugadorRepository : CrudRepository<Jugador, UUID> {


}