package es.sergigavi.PPSportAPI.REPOSITORIES

import es.sergigavi.PPSportAPI.MODEL.Torneo
import org.springframework.data.repository.CrudRepository
import java.util.*

interface TorneoRepository  : CrudRepository<Torneo, UUID> {


}