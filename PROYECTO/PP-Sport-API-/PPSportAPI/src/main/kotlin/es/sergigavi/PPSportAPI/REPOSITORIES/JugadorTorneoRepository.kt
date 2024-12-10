package es.sergigavi.PPSportAPI.REPOSITORIES

import es.sergigavi.PPSportAPI.MODEL.JugadorTorneo
import org.springframework.data.repository.CrudRepository
import java.util.UUID

interface JugadorTorneoRepository:CrudRepository<JugadorTorneo,UUID> {
}