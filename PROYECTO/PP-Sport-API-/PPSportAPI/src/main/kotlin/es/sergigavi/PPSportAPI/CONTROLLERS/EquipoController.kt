package es.sergigavi.PPSportAPI.CONTROLLERS

import es.sergigavi.PPSportAPI.MODEL.REQUEST.EquipoRequest
import es.sergigavi.PPSportAPI.MODEL.Equipo
import es.sergigavi.PPSportAPI.SERVICES.IEquipoService
import es.sergigavi.PPSportAPI.Utilities.Utilities
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import java.util.*

@CrossOrigin
@RestController
@RequestMapping("/ppsport/api/equipos")
class EquipoController {
    @Autowired
    lateinit var equipoService:IEquipoService

    @GetMapping("/todos")
    fun getAll(): ResponseEntity<Iterable<Equipo>> = ResponseEntity(this.equipoService.findAll(), HttpStatus.OK)

    @GetMapping("/{id}")
    fun getEquipo(@PathVariable id: UUID): ResponseEntity<Equipo> {
        try {
            val equipo = equipoService.findById(id)
            return if (equipo.isPresent) {
                ResponseEntity(equipo.get(), HttpStatus.OK)
            } else {
                ResponseEntity(HttpStatus.NOT_FOUND)
            }
        } catch (e: Exception) {
            return ResponseEntity(HttpStatus.INTERNAL_SERVER_ERROR)
        }
    }



    @PostMapping("/registrar")
    fun registrarEquipo(@RequestBody equipoRequest: EquipoRequest): ResponseEntity<Equipo> {
        try {

            val equipo = equipoRequest.let { Equipo(
                nombre = it.nombre,
                deporte = it.deporte
            ) }
            Utilities.LineaSeparadora()
            println("Se va a registrar el equipo: " + equipo.nombre)

            val respuesta = equipoService.add(equipo)
            if (respuesta.first) {
                println("Se ha insertado el equipo " + equipo.nombre + " correctamente")
                Utilities.LineaSeparadora()
                return ResponseEntity(equipo, HttpStatus.OK)
            } else {
                return ResponseEntity(HttpStatus.INTERNAL_SERVER_ERROR)
            }

        } catch (e: Exception) {
            println(e.printStackTrace())
            Utilities.LineaSeparadora()
            return ResponseEntity(HttpStatus.NOT_FOUND)
        }
    }
    
    @PutMapping("/{id}")
    fun editarEquipo(
        @PathVariable id: UUID, @RequestBody equipoRequest: Equipo
    ): ResponseEntity<Equipo> {
        try {
            if (equipoService.existsById(id)) {
                val equipo = equipoService.findById(id).get()
                equipo.apply {
                    nombre = equipoRequest.nombre
                }

                equipoService.edit(equipo)
                return ResponseEntity(equipo, HttpStatus.OK)
            } else {
                return ResponseEntity(HttpStatus.NOT_FOUND)
            }
        } catch (e: Exception) {
            return ResponseEntity(HttpStatus.INTERNAL_SERVER_ERROR)
        }
    }

    @DeleteMapping("{id}")
    fun eliminarEquipo(@PathVariable id: UUID) =
        if (equipoService.deleteById(id)) {
            ResponseEntity.ok("Equipo con id $id eliminado correctamente")
        } else {
            ResponseEntity(HttpStatus.NOT_FOUND)
        }

}