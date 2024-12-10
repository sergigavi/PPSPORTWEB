package es.sergigavi.PPSportAPI.SERVICES

import es.sergigavi.PPSportAPI.MODEL.Item
import es.sergigavi.PPSportAPI.MODEL.Polideportivo
import es.sergigavi.PPSportAPI.MODEL.Usuario
import java.util.Optional
import java.util.UUID

interface IItemService {

    fun add(item: Item): Boolean;
    fun edit(item: Item): Boolean;
    fun findById(id:UUID):Optional<Item>
    fun findAll(): Iterable<Item>;
    fun delete(itemId:UUID):Optional<Item>
    fun findAllByPolideportivoId(polideportivoId: UUID):Iterable<Item>
    fun findByNombreAndPolideportivoId(nombre:String,polideportivoId: UUID):Optional<Item>
}