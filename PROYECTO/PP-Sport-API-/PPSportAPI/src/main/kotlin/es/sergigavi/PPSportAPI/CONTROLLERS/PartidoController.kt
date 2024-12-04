package es.sergigavi.PPSportAPI.CONTROLLERS

import es.sergigavi.PPSportAPI.MODEL.Partido
import es.sergigavi.PPSportAPI.SERVICES.IPartidoService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import java.util.*


@CrossOrigin
@RestController
@RequestMapping("/ppsport/api/partido")
class PartidoController {

    @Autowired
    lateinit var partidoService: IPartidoService

    @GetMapping("/todos")
    fun getAll(): ResponseEntity<Iterable<Partido>> = ResponseEntity(this.partidoService.findAll(), HttpStatus.OK)

    @GetMapping("/{id}")
    fun getPartido(@PathVariable id: UUID): ResponseEntity<Partido> {
        try {
            val partido = partidoService.findById(id)
            return if (partido.isPresent) {
                ResponseEntity(partido.get(), HttpStatus.OK)
            } else {
                ResponseEntity(HttpStatus.NOT_FOUND)
            }
        } catch (e: Exception) {
            return ResponseEntity(HttpStatus.INTERNAL_SERVER_ERROR)
        }
    }


    @PutMapping("/{id}")
    fun editarPartido(
        @PathVariable id: UUID, @RequestBody partidoRequest: Partido
    ): ResponseEntity<Partido> {
        try {
            if (partidoService.existsById(id)) {
                val partido = partidoService.findById(id).get()
                partido.apply {
                     resultado = partido.resultado
                }

                partidoService.edit(partido)
                return ResponseEntity(partido, HttpStatus.OK)
            } else {
                return ResponseEntity(HttpStatus.NOT_FOUND)
            }
        } catch (e: Exception) {
            return ResponseEntity(HttpStatus.INTERNAL_SERVER_ERROR)
        }
    }
}