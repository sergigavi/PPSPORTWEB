package es.sergigavi.PPSportAPI.CONTROLLERS

import com.fasterxml.jackson.databind.JsonNode
import com.fasterxml.jackson.module.kotlin.jacksonObjectMapper
import es.sergigavi.PPSportAPI.MODEL.*
import es.sergigavi.PPSportAPI.MODEL.DTO.PistaDTO
import es.sergigavi.PPSportAPI.MODEL.DTO.ReservaDTO
import es.sergigavi.PPSportAPI.MODEL.REQUEST.PistaRequest
import es.sergigavi.PPSportAPI.MODEL.REQUEST.PolideportivoRequest
import es.sergigavi.PPSportAPI.SERVICES.IPistaService
import es.sergigavi.PPSportAPI.SERVICES.IPolideportivoService
import es.sergigavi.PPSportAPI.SERVICES.IReservaService
import es.sergigavi.PPSportAPI.SERVICES.apiexterna.DatosMadridService
import es.sergigavi.PPSportAPI.Utilities.Utilities
import jakarta.transaction.Transactional
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import java.time.LocalDate
import java.time.LocalTime
import java.util.UUID

@CrossOrigin
@RestController
@RequestMapping("/ppsport/api/polideportivos")
class PolideportivoController {

    @Autowired
    lateinit var polideportivoService: IPolideportivoService;
    @Autowired
    lateinit var datosMadridService: DatosMadridService;
    @Autowired
    lateinit var pistaService: IPistaService;
    @Autowired
    lateinit var reservaService: IReservaService;

    @GetMapping("/todos")
    fun getAll(): ResponseEntity<Iterable<Polideportivo>> {
        return ResponseEntity(this.polideportivoService.findAll(), HttpStatus.OK)
    }

    @GetMapping("/todoslimit")
    fun getAllLimit(): ResponseEntity<Iterable<Polideportivo>> {
        return ResponseEntity(this.polideportivoService.findAll().take(30), HttpStatus.OK)
    }

    @GetMapping("/pistas")
    fun getPistas(): ResponseEntity<Iterable<Pista>> = ResponseEntity(this.pistaService.findAll(), HttpStatus.OK)

    @GetMapping("/{id}")
    fun getPolideportivo(@PathVariable id: UUID): ResponseEntity<Polideportivo> {
        try {
            val polideportivo = polideportivoService.findById(id)
            return if (polideportivo.isPresent) {
                ResponseEntity(polideportivo.get(), HttpStatus.OK)
            } else {
                ResponseEntity(HttpStatus.NOT_FOUND)
            }
        } catch (e: Exception) {
            return ResponseEntity(HttpStatus.INTERNAL_SERVER_ERROR)
        }
    }

    @GetMapping("/{id}/pistas")
    fun getPistasDePolideportivo(@PathVariable id: UUID): ResponseEntity<Iterable<PistaDTO>> {
        return try {
            if (polideportivoService.existsById(id)) {
                ResponseEntity(pistaService.findAllByPolideportivoId(id), HttpStatus.OK)
            } else {
                ResponseEntity(HttpStatus.NOT_FOUND)
            }
        } catch (e: Exception) {
            ResponseEntity(HttpStatus.INTERNAL_SERVER_ERROR)
        }
    }
    @GetMapping("/pistas/{idPista}")
    fun getPista(@PathVariable id: UUID): ResponseEntity<Pista> {
        try {
            val pista = pistaService.findById(id)
            return if (pista.isPresent) {
                ResponseEntity(pista.get(), HttpStatus.OK)
            } else {
                ResponseEntity(HttpStatus.NOT_FOUND)
            }
        } catch (e: Exception) {
            return ResponseEntity(HttpStatus.INTERNAL_SERVER_ERROR)
        }
    }

    @GetMapping("/pistas/{pistaID}/reservas")
    fun getReservaDePistaByFecha(@PathVariable pistaID: UUID,@RequestParam fecha: LocalDate): ResponseEntity<Iterable<ReservaDTO>> {
        try {
            val reservas = reservaService.findByFechaAndPistaId(fecha,pistaID)
            return ResponseEntity(reservas, HttpStatus.OK)

        } catch (e: Exception) {
            println(e.printStackTrace())
            Utilities.LineaSeparadora();
            return ResponseEntity(HttpStatus.INTERNAL_SERVER_ERROR)
        }
    }

    @PostMapping("/registrar")
    fun registrarPolideportivo(@RequestBody polideportivoRegister: PolideportivoRequest): ResponseEntity<Polideportivo> {
        try {
            Utilities.LineaSeparadora();
            println("Se va a registrar el polideportivo: " + polideportivoRegister.nombre)
            val polideportivo = polideportivoRegister.let {
                Polideportivo(
                    nombre = it.nombre,
                    direccion = it.direccion,
                    localidad = it.localidad,
                    cp = it.cp,
                    horaApertura = it.horaApertura,
                    horaCierre = it.horaCierre,
//                    inventario = mutableSetOf()
                )
            }
            if (polideportivoService.add(polideportivo)) {
                println("Se ha insertado el polideportivo " + polideportivo.nombre + " correctamente")
                Utilities.LineaSeparadora()
                return ResponseEntity(polideportivo, HttpStatus.OK)
            } else {
                return ResponseEntity(HttpStatus.INTERNAL_SERVER_ERROR)
            }

        } catch (e: Exception) {
            println(e.printStackTrace())
            Utilities.LineaSeparadora();
            return ResponseEntity(HttpStatus.NOT_FOUND)
        }
    }

    @PutMapping("/{id}")
    fun editarPolideportivo(
        @PathVariable id: UUID, @RequestBody polideportivoRequest: PolideportivoRequest
    ): ResponseEntity<Polideportivo> {
        try {
            if (polideportivoService.existsById(id)) {
                val polideportivo = polideportivoService.findById(id).get()
                polideportivo.apply {
                    nombre = polideportivoRequest.nombre
                    direccion = polideportivoRequest.direccion
                    cp = polideportivoRequest.cp
                    localidad = polideportivoRequest.localidad
                }

                polideportivoService.edit(polideportivo)
                return ResponseEntity(polideportivo, HttpStatus.OK)
            } else {
                return ResponseEntity(HttpStatus.NOT_FOUND)
            }
        } catch (e: Exception) {
            return ResponseEntity(HttpStatus.INTERNAL_SERVER_ERROR)
        }
    }

    @DeleteMapping("{id}")
    fun eliminarPolideportivo(@PathVariable id: UUID) =
        if (polideportivoService.deleteById(id)) {
            ResponseEntity.ok("Polideportivo con id $id eliminado correctamente")
        } else {
            ResponseEntity(HttpStatus.NOT_FOUND)
        }

    @PostMapping("/{id}/registrar-pista")
    fun registrarPista(@PathVariable id: UUID,@RequestBody pistaRequest: PistaRequest): ResponseEntity<Pista> {
        try {

            val polideportivo = polideportivoService.findById(id)

            if (polideportivo.isEmpty){
                return ResponseEntity(HttpStatus.BAD_REQUEST)
            }

            val pista = Pista(id=null,nombre=pistaRequest.nombre, tipoPista = pistaRequest.tipoPista, polideportivo = polideportivo.get())

            Utilities.LineaSeparadora();
            println("Se va a registrar en el polideportivo " + pista.polideportivo.nombre + "la pista " + pista.nombre)

            if (pistaService.add(pista)) {
                println("Se ha insertado la pista " + pista.nombre + " correctamente")
                Utilities.LineaSeparadora()
                return ResponseEntity(pista, HttpStatus.OK)
            } else {
                return ResponseEntity(HttpStatus.BAD_REQUEST)
            }
        } catch (e: Exception) {
            println(e.printStackTrace())
            Utilities.LineaSeparadora();
            return ResponseEntity(HttpStatus.INTERNAL_SERVER_ERROR)
        }
    }


    @Deprecated(message = "usar 'cargar-polideportivos-api'")
    @Transactional
    fun cargarPolideportivos(): ResponseEntity<String> {
        val inputStream = this::class.java.classLoader.getResourceAsStream("images/CiudadDeportivaPrincipeFelipe.jpg")
        val byteArray = inputStream?.readBytes() ?: throw IllegalArgumentException("Archivo no encontrado")
        println("Tamaño de la imagen: ${byteArray.size} bytes")
        try {
            for (i in 1..20){
                val polideportivo = Polideportivo(
                    cp = (28500+i).toString(),
                    localidad = "Localidad $i",
                    direccion = "Calle $i",
                    nombre = "Polideportivo $i",
                    horaApertura = LocalTime.of(8, 45),
                    horaCierre = LocalTime.of(22,0),
    //              inventario = mutableSetOf(),
                    foto = byteArray
                )
                if(polideportivoService.add(polideportivo)){
                    for (enum in Deporte.entries) {
                        val pista = Pista(nombre = "Pista de " + enum.name.lowercase().replace("_", " "), tipoPista = enum, polideportivo = polideportivo)
                        pistaService.add(pista) // Añadimos la pista al Set de pistas del Polideportivo
                    }
                }
        }
            return ResponseEntity("Correct init de polideportivos", HttpStatus.OK)
        }catch (e:Exception){
            return ResponseEntity("Fallo en init de polideportivos", HttpStatus.INTERNAL_SERVER_ERROR)

        }
    }

    @GetMapping("/cargar-polideportivos-api")
    @Transactional
    fun cargarPolideportivosAPI():ResponseEntity<String>{


        val polideportivos = this.procesarPolideportivosAPI()
        val respuesta = polideportivoService.saveAll(polideportivos)

        for(polideportivo in respuesta){
            for (enum in Deporte.entries) {
                val pista = Pista(nombre = "Pista de " + enum.name.lowercase().replace("_", " "), tipoPista = enum, polideportivo = polideportivo)
                pistaService.add(pista) // Añadimos la pista al Set de pistas del Polideportivo
            }
        }

        return ResponseEntity("correcto init polis+pitas",HttpStatus.OK)
    }


    private fun procesarPolideportivosAPI(): List<Polideportivo> {
        val json = datosMadridService.obtenerDatosPolideportivos()

        val objectMapper = jacksonObjectMapper()
        val rootNode: JsonNode = objectMapper.readTree(json)
        val graphNode = rootNode["@graph"]

        val instalaciones = mutableListOf<Polideportivo>()

        val inputStream = this::class.java.classLoader.getResourceAsStream("images/CiudadDeportivaPrincipeFelipe.jpg")
        val imagenPoli = inputStream?.readBytes() ?: throw IllegalArgumentException("Archivo no encontrado")
        println("Tamaño de la imagen: ${imagenPoli.size} bytes")

        graphNode?.forEach { nodo ->
            val nombre = nodo["title"].asText()
            val localidad = nodo["address"]["locality"].asText()
            val cp = nodo["address"]["postal-code"].asText()
            val direccion = nodo["address"]["street-address"].asText()
            val latitud = nodo["location"]["latitude"].asDouble()
            val longitud = nodo["location"]["longitude"].asDouble()
            val horario = nodo["organization"]["schedule"].asText()

            instalaciones.add(
                Polideportivo(
                    nombre = nombre,
                    localidad = localidad,
                    cp = cp,
                    direccion = direccion,
                    latitud = latitud,
                    longitud = longitud,
                    horario = horario.take(255),
                    foto = imagenPoli
                )
            )
        }

        return instalaciones
    }





}