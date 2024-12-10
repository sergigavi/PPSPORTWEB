package es.sergigavi.PPSportAPI.REPOSITORIES

import es.sergigavi.PPSportAPI.MODEL.EquipoPartido
import org.springframework.data.repository.CrudRepository
import java.util.UUID

interface EquipoPartidoRepository:CrudRepository<EquipoPartido,UUID> {
    fun findAllByPartidoId(partidoId:UUID):Iterable<EquipoPartido>
    fun findAllByEquipoId(equipoId:UUID):Iterable<EquipoPartido>
}