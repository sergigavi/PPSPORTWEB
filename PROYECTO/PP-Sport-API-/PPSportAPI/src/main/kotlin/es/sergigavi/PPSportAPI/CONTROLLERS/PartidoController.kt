package es.sergigavi.PPSportAPI.CONTROLLERS

import es.sergigavi.PPSportAPI.MODEL.DTO.PartidoDTO
import es.sergigavi.PPSportAPI.MODEL.DesenlacePartido
import es.sergigavi.PPSportAPI.MODEL.MAPPER.toDTO
import es.sergigavi.PPSportAPI.MODEL.Partido
import es.sergigavi.PPSportAPI.MODEL.REQUEST.PartidoRequest
import es.sergigavi.PPSportAPI.SERVICES.IPartidoService
import es.sergigavi.PPSportAPI.SERVICES.ITorneoService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import java.util.*


@CrossOrigin
@RestController
@RequestMapping("/ppsport/api/partidos")
class PartidoController {

    @Autowired
    lateinit var partidoService: IPartidoService

    @Autowired
    lateinit var torneoService: ITorneoService

    @GetMapping("/todos")
    fun getAll(): ResponseEntity<Iterable<PartidoDTO>> = ResponseEntity(this.partidoService.findAll().map { it.toDTO() }, HttpStatus.OK)

    @GetMapping("/{id}")
    fun getPartido(@PathVariable id: UUID): ResponseEntity<PartidoDTO> {
        try {
            val partido = partidoService.findById(id)
            return if (partido.isPresent) {
                ResponseEntity(partido.get().toDTO(), HttpStatus.OK)
            } else {
                ResponseEntity(HttpStatus.NOT_FOUND)
            }
        } catch (e: Exception) {
            return ResponseEntity(HttpStatus.INTERNAL_SERVER_ERROR)
        }
    }

    @GetMapping("/torneo/{id}")
    fun getPartidosByTorneo(@PathVariable id: UUID): ResponseEntity<Iterable<PartidoDTO>> {
        try {
            val torneo = torneoService.findById(id)
            return if (torneo.isPresent) {
                ResponseEntity(partidoService.findAllByTorneoId(id).map{it.toDTO()}, HttpStatus.OK)
            } else {
                ResponseEntity(HttpStatus.NOT_FOUND)
            }
        } catch (e: Exception) {
            return ResponseEntity(HttpStatus.INTERNAL_SERVER_ERROR)
        }
    }


    @PutMapping("/{id}")
    fun editarPartido(
        @PathVariable id: UUID, @RequestBody partidoRequest: PartidoRequest
    ): ResponseEntity<PartidoDTO> {
        try {
            val partidoOptional = partidoService.findById(id)
            if (partidoOptional.isPresent) {
                val partido = partidoOptional.get()

                val equiposPartido = partidoService.findAllEquipoPartidoByPartidoId(partido.id!!)

                equiposPartido.forEach { eqPartido ->
                    eqPartido.resultado = partidoRequest.resultado
                    if(eqPartido.equipo.id == partidoRequest.equipoGanador){
                        eqPartido.desenlacePartido = DesenlacePartido.VICTORIA
                    }else{
                        eqPartido.desenlacePartido = DesenlacePartido.DERROTA
                    }
                    partidoService.editEquipoPartido(eqPartido)
                }
                partido.apply {
                    resultado = partidoRequest.resultado
                    partidoRequest.fecha.let {fecha = it}
                    equiposPartidos.forEach {
                        it.desenlacePartido = equiposPartido.first { e->e.id==it.id }.desenlacePartido
                        it.resultado = partidoRequest.resultado
                    }
                }
                partidoService.edit(partido)

                return ResponseEntity(partido.toDTO(), HttpStatus.OK)
            } else {
                return ResponseEntity(HttpStatus.NOT_FOUND)
            }
        } catch (e: Exception) {
            return ResponseEntity(HttpStatus.INTERNAL_SERVER_ERROR)
        }
    }
}