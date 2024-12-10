package es.sergigavi.PPSportAPI.REPOSITORIES

import es.sergigavi.PPSportAPI.MODEL.Item
import es.sergigavi.PPSportAPI.MODEL.Polideportivo
import org.springframework.data.repository.CrudRepository
import java.util.Optional
import java.util.UUID

interface ItemRepository:CrudRepository<Item,UUID> {
    fun findAllByPolideportivoId(polideportivoId: UUID):Iterable<Item>
    fun findByNombreAndPolideportivoId(nombre:String,polideportivoId: UUID):Optional<Item>
}