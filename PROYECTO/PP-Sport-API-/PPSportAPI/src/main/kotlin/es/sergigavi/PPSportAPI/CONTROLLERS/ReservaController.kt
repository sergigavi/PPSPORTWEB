package es.sergigavi.PPSportAPI.CONTROLLERS

import es.sergigavi.PPSportAPI.MODEL.DTO.ReservaDTO
import es.sergigavi.PPSportAPI.MODEL.REQUEST.ReservaRequest
import es.sergigavi.PPSportAPI.MODEL.Reserva
import es.sergigavi.PPSportAPI.SERVICES.IReservaService
import es.sergigavi.PPSportAPI.SERVICES.IUsuarioService
import es.sergigavi.PPSportAPI.Utilities.Utilities
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import java.util.UUID

@CrossOrigin
@RestController
@RequestMapping("/ppsport/api/reservas")
class ReservaController {
    @Autowired
    lateinit var reservaService: IReservaService

    @Autowired
    lateinit var usuarioService: IUsuarioService

    @GetMapping("/todas")
    fun getAll(): ResponseEntity<Iterable<Reserva>> = ResponseEntity(this.reservaService.findAll(), HttpStatus.OK)


    @GetMapping("/{id}")
    fun getReserva(@PathVariable reservaID: UUID): ResponseEntity<ReservaDTO> {
        try {
            val reserva = reservaService.findById(reservaID)
            return if (reserva.isPresent) {
                ResponseEntity(reserva.get(), HttpStatus.OK)
            } else {
                ResponseEntity(HttpStatus.NOT_FOUND)
            }
        } catch (e: Exception) {
            println(e.printStackTrace())
            Utilities.LineaSeparadora()
            return ResponseEntity(HttpStatus.INTERNAL_SERVER_ERROR)
        }
    }


    @GetMapping("/todas/{usuarioID}")
    fun getReservasByUsuario(@PathVariable usuarioID: UUID): ResponseEntity<Iterable<ReservaDTO>> {
        try {
            val usuario = usuarioService.findById(usuarioID)
            if (usuario.isPresent) {
                val reservas = reservaService.findAllByUsuario(usuario.get())
                return ResponseEntity(reservas, HttpStatus.OK)
            } else {
                return ResponseEntity(HttpStatus.BAD_REQUEST)
            }
        } catch (e: Exception) {
            println(e.printStackTrace())
            Utilities.LineaSeparadora()
            return ResponseEntity(HttpStatus.INTERNAL_SERVER_ERROR)
        }
    }

    @PostMapping("/nueva")
    fun crearReserva(@RequestBody reserva: ReservaRequest): ResponseEntity<Reserva> {

        try {
            if (reserva.let{reservaService.existsByFechaAndHoraInicioAndPistaId(it.fecha,it.horaInicio,it.pistaID ) ||
                        reservaService.existsByFechaAndHoraFinAndPistaId(it.fecha,it.horaFin,it.pistaID )}) {
                return ResponseEntity(HttpStatus.CONFLICT)
            }
            val respuestaServicio = reservaService.add(reserva)
            if (respuestaServicio.first) {
                Utilities.LineaSeparadora()
                println("Se ha creado la reserva par el usuario con ID: " + reserva.usuarioID + "correctamente")
                Utilities.LineaSeparadora()
                return ResponseEntity(respuestaServicio.second,HttpStatus.OK)
            } else {
                return ResponseEntity(HttpStatus.BAD_REQUEST)
            }

        } catch (e: Exception) {
            println(e.printStackTrace())
            Utilities.LineaSeparadora()
            return ResponseEntity(HttpStatus.INTERNAL_SERVER_ERROR)
        }

    }

    @DeleteMapping("{id}")
    fun anularReserva(@PathVariable id: UUID) =
        if (reservaService.deleteById(id)) {
        ResponseEntity.ok("Reserva con id $id anulada correctamente")
    } else {
        ResponseEntity(HttpStatus.NOT_FOUND)
    }

}