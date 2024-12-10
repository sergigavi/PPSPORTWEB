package es.sergigavi.PPSportAPI.CONTROLLERS

import es.sergigavi.PPSportAPI.MODEL.*
import es.sergigavi.PPSportAPI.MODEL.DTO.ClasificacionDTO
import es.sergigavi.PPSportAPI.MODEL.DTO.PartidoDTO
import es.sergigavi.PPSportAPI.MODEL.DTO.TorneoDTO
import es.sergigavi.PPSportAPI.MODEL.MAPPER.toDTO
import es.sergigavi.PPSportAPI.MODEL.REQUEST.TorneoRequest
import es.sergigavi.PPSportAPI.MODEL.REQUEST.IdRequest
import es.sergigavi.PPSportAPI.SERVICES.IEquipoService
import es.sergigavi.PPSportAPI.SERVICES.IJugadorService
import es.sergigavi.PPSportAPI.SERVICES.IPartidoService
import es.sergigavi.PPSportAPI.SERVICES.ITorneoService
import es.sergigavi.PPSportAPI.Utilities.Utilities
import jakarta.transaction.Transactional
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

    @Autowired
    lateinit var partidoService: IPartidoService

    @Autowired
    lateinit var equipoService: IEquipoService

    @Autowired
    lateinit var jugadorService: IJugadorService

    @GetMapping("/todos")
    fun getAll(): ResponseEntity<Iterable<TorneoDTO>> = ResponseEntity(this.torneoService.findAll(), HttpStatus.OK)

    @GetMapping("/{id}")
    fun getTorneo(@PathVariable id: UUID): ResponseEntity<TorneoDTO> {
        try {
            val torneo = torneoService.findById(id)
            return if (torneo.isPresent) {
                ResponseEntity(torneo.get().toDTO(), HttpStatus.OK)
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

            val torneo = torneoRequest.let {
                Torneo(
                    nombre = it.nombre,
                    deporte = it.deporte,
                    fechaInicio = it.fechaInicio,
                    fechaFin = it.fechaFin,
                    tipoTorneo = it.tipoTorneo,
                    estado = Estado.CREADO
                )
            }
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
    fun cancelarTorneo(@PathVariable id: UUID) = if (torneoService.existsById(id)) {
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
    fun agregarEquiposATorneo(@PathVariable id: UUID, @RequestBody equipos: List<Equipo>): ResponseEntity<String> {
        if (torneoService.existsById(id)) {
            val torneo = torneoService.findById(id).get()

            val equiposTorneo: MutableSet<EquipoTorneo> = mutableSetOf()
            for (equipo in equipos) {
                equiposTorneo.add(EquipoTorneo(torneo = torneo, equipo = equipo))
            }
            torneoService.agregarEquipos(equiposTorneo)


            return ResponseEntity("Agregados correctamente", HttpStatus.OK)
        } else {
            return ResponseEntity("Torneo no encontrado", HttpStatus.NOT_FOUND)
        }
    }


    @PostMapping("/{id}/unirse-equipo-a-torneo")
    fun unirseATorneoEquipo(@PathVariable id: UUID, @RequestBody request: IdRequest): ResponseEntity<String> {

        val torneo = torneoService.findById(id)
        if (torneo.isEmpty) {
            return ResponseEntity("Torneo no encontrado", HttpStatus.NOT_FOUND)

        }
        val equipo = equipoService.findById(request.id)
        if (equipo.isPresent) {

            if (equipo.get().deporte != torneo.get().deporte) {
                return ResponseEntity(HttpStatus.BAD_REQUEST)
            }

            if (torneo.get().equiposTorneo.map { it.equipo.id }.contains(equipo.get().id)) {
                return ResponseEntity("Equipo ya registrado", HttpStatus.CONFLICT)
            }
            val equiposTorneo: MutableSet<EquipoTorneo> = mutableSetOf()
            val equipoTorneo = EquipoTorneo(torneo = torneo.get(), equipo = equipo.get())

            equiposTorneo.add(equipoTorneo)

            torneo.get().equiposTorneo.add(equipoTorneo)
            equipo.get().equipoTorneos.add(equipoTorneo)


            torneoService.agregarEquipos(equiposTorneo)

            return ResponseEntity("agregado correctamente", HttpStatus.CREATED)
        } else {
            return ResponseEntity("Equipo no encontrado", HttpStatus.NOT_FOUND)
        }
    }

    @PostMapping("/{id}/unirse-jugador-a-torneo")
    fun unirseATorneoJugador(@PathVariable id: UUID, @RequestBody request: IdRequest): ResponseEntity<String> {
        val torneo = torneoService.findById(id)


        val jugador = jugadorService.findById(request.id)

        if (jugador.isPresent) {

            if (torneo.get().jugadoresTorneo.map { it.jugador.id }.contains(jugador.get().id)) {
                return ResponseEntity("Jugador ya registrado", HttpStatus.CONFLICT)
            }


            val jugadoresTorneo: MutableSet<JugadorTorneo> = mutableSetOf()
            val jugadorTorneo = JugadorTorneo(torneo = torneo.get(), jugador = jugador.get())
            jugadoresTorneo.add(jugadorTorneo)

            torneo.get().jugadoresTorneo.add(jugadorTorneo)
            jugador.get().torneos.add(jugadorTorneo)


            torneoService.agregarJugadores(jugadoresTorneo)

            return ResponseEntity("agregado correctamente", HttpStatus.CREATED)
        } else {
            return ResponseEntity("Jugador no encontrado", HttpStatus.NOT_FOUND)
        }
    }


    @GetMapping("{id}/generar-partidos-torneo")
    @Transactional
    fun generarPartidosDeTorneo(@PathVariable id: UUID): ResponseEntity<String> {

        val torneo = torneoService.findById(id)
        if (torneo.isPresent) {

            if (torneo.get().tipoTorneo != TipoTorneo.ELIMINATORIO) {
                return ResponseEntity(HttpStatus.BAD_REQUEST)
            }

            if (torneo.get().partidos.isNotEmpty()) {
                return ResponseEntity(HttpStatus.BAD_REQUEST)
            }

            val partidos: MutableSet<Partido> = mutableSetOf()
            val equipos = torneo.get().equiposTorneo.map { it.equipo }.shuffled()

            for (i in equipos.indices step 2) {
                partidos.add(
                    Partido(
                        id = UUID.randomUUID(),
                        fecha = torneo.get().fechaInicio,
                        torneo = torneo.get(),
                        ronda = equipos.lastIndex + 1
                    )
                )
            }

            val resultadoPartidos = partidoService.addAll(partidos)
            if (!resultadoPartidos.first) {
                return ResponseEntity(HttpStatus.INTERNAL_SERVER_ERROR)
            }

            val partidosCreados = partidoService.findAllByTorneoId(torneo.get().id!!)
            val equiposPartido: MutableSet<EquipoPartido> = mutableSetOf()

            partidosCreados.forEachIndexed { index, partido ->
                equiposPartido.add(EquipoPartido(equipo = equipos[index * 2], partido = partido))
                equiposPartido.add(EquipoPartido(equipo = equipos[index * 2 + 1], partido = partido))
            }


            val exito = partidoService.guardarEquiposPartido(equiposPartido)
            return ResponseEntity("Partidos generados correctamente", HttpStatus.OK)
        } else {
            return ResponseEntity(HttpStatus.NOT_FOUND)
        }
    }

    @GetMapping("{id}/generar-partidos-torneo-siguiente-ronda")
    @Transactional
    fun generarPartidosDeTorneoSiguienteRonda(@PathVariable id: UUID): ResponseEntity<String> {

        val torneo = torneoService.findById(id)
        if (torneo.isPresent) {

            if (torneo.get().tipoTorneo != TipoTorneo.ELIMINATORIO) {
                return ResponseEntity(HttpStatus.BAD_REQUEST)
            }

            val partidos: MutableSet<Partido> = mutableSetOf()
            val ultimaRonda = torneo.get().partidos.minOf { it.ronda }
            val equipos = torneo.get().partidos.filter { it.ronda == ultimaRonda }.flatMap { it.equiposPartidos }
                .filter { it.desenlacePartido == DesenlacePartido.VICTORIA }.map { it.equipo }.shuffled()

            val rondaActual = equipos.lastIndex +1

            if (rondaActual == 1) {
                return ResponseEntity(HttpStatus.BAD_REQUEST)
            }

            for (i in equipos.indices step 2) {
                partidos.add(
                    Partido(
                        id = UUID.randomUUID(),
                        fecha = torneo.get().fechaInicio,
                        torneo = torneo.get(),
                        ronda = rondaActual
                    )
                )
            }

            val resultadoPartidos = partidoService.addAll(partidos)
            if (!resultadoPartidos.first) {
                return ResponseEntity(HttpStatus.INTERNAL_SERVER_ERROR)
            }

            var partidosCreados = partidoService.findAllByTorneoId(torneo.get().id!!).filter { it.ronda == rondaActual }
            val equiposPartido: MutableSet<EquipoPartido> = mutableSetOf()

            partidosCreados.forEachIndexed { index, partido ->
                equiposPartido.add(EquipoPartido(equipo = equipos[index * 2], partido = partido))
                equiposPartido.add(EquipoPartido(equipo = equipos[index * 2 + 1], partido = partido))
            }

            partidoService.guardarEquiposPartido(equiposPartido)
            return ResponseEntity("Partidos generados correctamente", HttpStatus.OK)
        } else {
            return ResponseEntity(HttpStatus.NOT_FOUND)
        }

    }

    @GetMapping("{id}/generar-partidos-liga")
    @Transactional
    fun generarPartidosDeLiga(@PathVariable id: UUID): ResponseEntity<String> {

        val torneo = torneoService.findById(id)
        if (torneo.isPresent) {

            if (torneo.get().tipoTorneo != TipoTorneo.LIGA) {
                return ResponseEntity(HttpStatus.BAD_REQUEST)
            }
            if (torneo.get().partidos.isNotEmpty()) {
                return ResponseEntity(HttpStatus.BAD_REQUEST)
            }

            val partidos: MutableSet<Partido> = mutableSetOf()
            var equipos = torneo.get().equiposTorneo.map { it.equipo }.shuffled()

            val equiposRestantes = equipos.toMutableSet()
            val equiposPartido: MutableSet<EquipoPartido> = mutableSetOf()

            for (equipo in equipos) {
                equiposRestantes.remove(equipo)
                for (equipoRestante in equiposRestantes) {
                    val partido = Partido(
                        id = UUID.randomUUID(),
                        fecha = torneo.get().fechaInicio,
                        torneo = torneo.get(),
                        ronda = equiposRestantes.indexOf(equipoRestante)+1
                    )
                    partidos.add(partido)
                    equiposPartido.add(EquipoPartido(equipo = equipo, partido = partido))
                    equiposPartido.add(EquipoPartido(equipo = equipoRestante, partido = partido))
                }
            }

            val resultadoPartidos = partidoService.addAll(partidos)
            if (!resultadoPartidos.first) {
                return ResponseEntity(HttpStatus.INTERNAL_SERVER_ERROR)
            }

            var partidosCreados = partidoService.findAllByTorneoId(torneo.get().id!!)

            equiposPartido.forEach {
                it.partido = partidosCreados.first { p->p.id == it.partido.id  }
            }


            partidoService.guardarEquiposPartido(equiposPartido)
            return ResponseEntity("Partidos generados correctamente", HttpStatus.OK)
        } else {
            return ResponseEntity(HttpStatus.NOT_FOUND)
        }

    }


    @GetMapping("/{id}/get-clasificacion")
    fun getClasificacionDeLiga(@PathVariable id: UUID): ResponseEntity<Iterable<ClasificacionDTO>> {

        val torneo = torneoService.findById(id)

        if (torneo.isPresent) {
            val clasificaciones = torneoService.getClasificaciones(torneo.get())
            return ResponseEntity(clasificaciones, HttpStatus.OK)
        } else {
            return ResponseEntity(HttpStatus.NOT_FOUND)
        }
    }


}