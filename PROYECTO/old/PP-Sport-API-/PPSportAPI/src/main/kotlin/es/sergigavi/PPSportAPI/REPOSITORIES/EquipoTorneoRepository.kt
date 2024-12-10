package es.sergigavi.PPSportAPI.REPOSITORIES

import es.sergigavi.PPSportAPI.MODEL.EquipoTorneo
import org.springframework.data.repository.CrudRepository
import java.util.*

interface EquipoTorneoRepository: CrudRepository<EquipoTorneo, UUID> {


}