package es.sergigavi.PPSportAPI.REPOSITORIES

import es.sergigavi.PPSportAPI.MODEL.Equipo
import org.springframework.data.repository.CrudRepository
import java.util.*

interface EquipoRepository  : CrudRepository<Equipo, UUID> {

}