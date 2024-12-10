package es.sergigavi.PPSportAPI.REPOSITORIES

import es.sergigavi.PPSportAPI.MODEL.JugadorPartido
import org.springframework.data.repository.CrudRepository
import java.util.UUID

interface JugadorPartidoRepository:CrudRepository<JugadorPartido,UUID> {
}