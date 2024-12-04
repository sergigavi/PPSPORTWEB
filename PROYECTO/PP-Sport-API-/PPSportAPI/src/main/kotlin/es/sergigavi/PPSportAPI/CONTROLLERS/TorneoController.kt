package es.sergigavi.PPSportAPI.CONTROLLERS

import es.sergigavi.PPSportAPI.MODEL.REQUEST.TorneoRequest
import es.sergigavi.PPSportAPI.MODEL.Equipo
import es.sergigavi.PPSportAPI.MODEL.EquipoTorneo
import es.sergigavi.PPSportAPI.MODEL.Estado
import es.sergigavi.PPSportAPI.MODEL.Torneo
import es.sergigavi.PPSportAPI.SERVICES.ITorneoService
import es.sergigavi.PPSportAPI.Utilities.Utilities
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import java.util.*

@CrossOrigin
@RestController
@RequestMapping("/ppsport/api/torneos")
class TorneoController {
    @Autowired
    lateinit var torneoService: ITorneoService

    @GetMapping("/todos")
    fun getAll(): ResponseEntity<Iterable<Torneo>> = ResponseEntity(this.torneoService.findAll(), HttpStatus.OK)

    @GetMapping("/{id}")
    fun getTorneo(@PathVariable id: UUID): ResponseEntity<Torneo> {
        try {
            val torneo = torneoService.findById(id)
            return if (torneo.isPresent) {
                ResponseEntity(torneo.get(), HttpStatus.OK)
            } else {
                ResponseEntity(HttpStatus.NOT_FOUND)
            }
        } catch (e: Exception) {
            return ResponseEntity(HttpStatus.INTERNAL_SERVER_ERROR)
        }
    }

    @PostMapping("/crear")
    fun crearTorneo(@RequestBody torneoRequest: TorneoRequest): ResponseEntity<Torneo> {
        try {

            val torneo = torneoRequest.let { Torneo(
                nombre = it.nombre,
                deporte = it.deporte,
                fechaInicio = it.fechaInicio,
                fechaFin = it.fechaFin,
                tipoTorneo = it.tipoTorneo,
                estado = Estado.CREADO
            ) }
            Utilities.LineaSeparadora()
            println("Se va a registrar el torneo: " + torneo.nombre)

            val respuesta = torneoService.add(torneo)
            if (respuesta.first) {
                println("Se ha insertado el torneo " + torneo.nombre + " correctamente")
                Utilities.LineaSeparadora()
                return ResponseEntity(torneo, HttpStatus.OK)
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
    fun editarTorneo(
        @PathVariable id: UUID, @RequestBody torneoRequest: Torneo
    ): ResponseEntity<Torneo> {
        try {
            if (torneoService.existsById(id)) {
                val torneo = torneoService.findById(id).get()
                torneo.apply {
                    nombre = torneoRequest.nombre
                    estado = torneoRequest.estado
                }

                torneoService.edit(torneo)
                return ResponseEntity(torneo, HttpStatus.OK)
            } else {
                return ResponseEntity(HttpStatus.NOT_FOUND)
            }
        } catch (e: Exception) {
            return ResponseEntity(HttpStatus.INTERNAL_SERVER_ERROR)
        }
    }

    @DeleteMapping("{id}")
    fun cancelarTorneo(@PathVariable id: UUID) =
        if (torneoService.existsById(id)) {
            val torneo = torneoService.findById(id).get()
            torneo.apply {
                estado = Estado.CANCELADO
            }
            torneoService.edit(torneo)
            ResponseEntity.ok("Equipo con id $id eliminado correctamente")
        } else {
            ResponseEntity(HttpStatus.NOT_FOUND)
        }

    @PostMapping("{id}/agregar-equipos")
    fun agregarEquiposATorneo(@PathVariable id: UUID,@RequestBody equipos:List<Equipo>):ResponseEntity<String>{
        if (torneoService.existsById(id)) {
            val torneo = torneoService.findById(id).get()

            val equiposTorneo:MutableSet<EquipoTorneo> = mutableSetOf()
            for (equipo in equipos) {
                equiposTorneo.add(EquipoTorneo(torneo=torneo, equipo = equipo))
            }
            torneoService.agregarEquipos(equiposTorneo)


            return ResponseEntity("Creado correctamente",HttpStatus.CREATED)
        }else{
            return ResponseEntity("Torneo no encontrado",HttpStatus.NOT_FOUND)
        }
    }






}