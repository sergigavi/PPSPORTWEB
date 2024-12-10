package es.sergigavi.PPSportAPI.SERVICES

import es.sergigavi.PPSportAPI.MODEL.Item
import es.sergigavi.PPSportAPI.REPOSITORIES.ItemRepository
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service
import java.util.*

@Service
class ItemServiceImpl:IItemService {

    @Autowired
    lateinit var itemDAO: ItemRepository

    override fun add(item: Item): Boolean {
        var exito = false

        try {
            this.itemDAO.save(item);
            exito = true
        }catch (e: Exception){
            println("Error en servicio 'Add', añadiendo un item nuevo." + e.printStackTrace())
        }

        return exito
    }

    override fun edit(item: Item): Boolean {
        var exito = false

        if (item.id?.let { itemDAO.existsById(it) } == true)
        {
            itemDAO.save(item)
            exito = true
        }

        return exito
    }

    override fun findById(id: UUID): Optional<Item> {
        return itemDAO.findById(id)
    }

    override fun findAll(): Iterable<Item> {
        return itemDAO.findAll()
    }

    override fun delete(itemId: UUID): Optional<Item>  {
        val item : Optional<Item> = itemDAO.findById(itemId)

        if (item.isPresent)
        {
            itemDAO.deleteById(itemId)
        }
        return item;
    }

    override fun findAllByPolideportivoId(polideportivoId:UUID): Iterable<Item> {
        return itemDAO.findAllByPolideportivoId(polideportivoId)
    }

    override fun findByNombreAndPolideportivoId(nombre: String, polideportivoId: UUID): Optional<Item> {
        return itemDAO.findByNombreAndPolideportivoId(nombre, polideportivoId)
    }
}