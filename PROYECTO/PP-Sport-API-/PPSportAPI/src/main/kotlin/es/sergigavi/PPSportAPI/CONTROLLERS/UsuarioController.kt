package es.sergigavi.PPSportAPI.CONTROLLERS

import es.sergigavi.PPSportAPI.MODEL.DTO.JugadorDTO
import es.sergigavi.PPSportAPI.MODEL.Jugador
import es.sergigavi.PPSportAPI.MODEL.MAPPER.toDTO
import es.sergigavi.PPSportAPI.MODEL.REQUEST.UsuarioRegister
import es.sergigavi.PPSportAPI.MODEL.Rol
import es.sergigavi.PPSportAPI.MODEL.Usuario
import es.sergigavi.PPSportAPI.SERVICES.IJugadorService
import es.sergigavi.PPSportAPI.SERVICES.IUsuarioService
import es.sergigavi.PPSportAPI.Utilities.Utilities
import jakarta.persistence.EntityManager
import jakarta.persistence.PersistenceContext
import jakarta.transaction.Transactional
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import java.time.LocalDate
import java.util.*

@CrossOrigin
@RestController
@RequestMapping("/ppsport/api/usuarios")
class UsuarioController {


    @Autowired
    lateinit var usuarioService: IUsuarioService
    @Autowired
    lateinit var jugadorService: IJugadorService

    @PersistenceContext
    private lateinit var entityManager: EntityManager


    @GetMapping("/init")
    fun test(): ResponseEntity<String> {
        //los dejo sin hashear para poder usar los user desde postman

        usuarioService.add(
            Usuario(
                id = null,
                dni = "00000001B",
                nombre = "Caca",
                apellido1 = "Huetes",
                apellido2 = "Fritos",
                fechaNacimiento = LocalDate.parse("2000-01-10"),
                email = "caca.huetes@frito.es",
                password = "1234",
                rol = Rol.ADMIN,
            )
        )

        return ResponseEntity("Correct init", HttpStatus.OK)
    }

    @GetMapping("/todos")
    fun getAll(): ResponseEntity<Iterable<Usuario>> {
        
        val allUsuarios: Iterable<Usuario> = this.usuarioService.findAll()
        return ResponseEntity( allUsuarios, HttpStatus.OK)

    }

    @GetMapping("/getByEmail/{email}")
    fun getUsuarioByEmail(@PathVariable email: String): ResponseEntity<Usuario> {
        try {
            val usuario = usuarioService.findByEmail(email)
            return if (usuario.isPresent) {
                ResponseEntity(usuario.get(), HttpStatus.OK)
            } else {
                ResponseEntity(HttpStatus.NOT_FOUND)
            }
        } catch (e: Exception) {
            return ResponseEntity(HttpStatus.INTERNAL_SERVER_ERROR)
        }
    }

    @GetMapping("/{id}")
    fun getUsuarioById(@PathVariable id: UUID): ResponseEntity<Usuario> {
        try {
            val usuario = usuarioService.findById(id)
            return if (usuario.isPresent) {
                ResponseEntity(usuario.get(), HttpStatus.OK)
            } else {
                ResponseEntity(HttpStatus.NOT_FOUND)
            }
        } catch (e: Exception) {
            return ResponseEntity(HttpStatus.INTERNAL_SERVER_ERROR)
        }
    }

    @PostMapping("/registrar")
    @Transactional
    fun registrar(@RequestBody usuarioRegister: UsuarioRegister): ResponseEntity<Usuario> {
            Utilities.LineaSeparadora();
            println("Se va a registrar el usuario: " + usuarioRegister.dni)

            if (usuarioService.existsByEmail(usuarioRegister.email) || usuarioService.existsByDni(usuarioRegister.dni)) {
                return ResponseEntity(HttpStatus.CONFLICT)
            }

            val usuario = Usuario(
                UUID.randomUUID(),
                usuarioRegister.dni,
                usuarioRegister.nombre,
                usuarioRegister.apellido1,
                usuarioRegister.apellido2,
                usuarioRegister.fechaNacimiento,
                usuarioRegister.email,
                usuarioRegister.password,
                Rol.USUARIO
            )
            println("Se ha insertado el usuario " + usuario.dni + " correctamente")
            Utilities.LineaSeparadora()
            val jugador = Jugador(id = UUID.randomUUID(), usuario = usuario)
            jugadorService.add(jugador)

            return ResponseEntity(usuario, HttpStatus.OK)
    }

    @PutMapping("/{id}")
    fun editarUsuario(
        @PathVariable id: UUID, @RequestBody usuarioRequest: Usuario
    ): ResponseEntity<Usuario> {
        try {
            if (usuarioService.existsById(id)) {
                val usuario = usuarioService.findById(id).get()
                usuario.apply {
                    email = usuarioRequest.email
                    password = usuarioRequest.password
                }

                usuarioService.edit(usuario)
                return ResponseEntity(usuario, HttpStatus.OK)
            } else {
                return ResponseEntity(HttpStatus.NOT_FOUND)
            }
        } catch (e: Exception) {
            return ResponseEntity(HttpStatus.INTERNAL_SERVER_ERROR)
        }
    }

    @GetMapping("/{id}/jugador")
    fun getJugadorByUsuarioId(@PathVariable id: UUID): ResponseEntity<JugadorDTO> {
        try {
            if (usuarioService.existsById(id)) {
                val jugador = jugadorService.findByUsuarioId(id)
                return if (jugador.isPresent) {
                    ResponseEntity(jugador.get().toDTO(), HttpStatus.OK)
                } else {
                    ResponseEntity(HttpStatus.NOT_FOUND)
                }
            }else{
                return ResponseEntity(HttpStatus.NOT_FOUND)
            }
        } catch (e: Exception) {
            return ResponseEntity(HttpStatus.INTERNAL_SERVER_ERROR)
        }
    }

    @PutMapping("/{id}/jugador")
    fun editarJugadorByUsuarioId(@PathVariable id: UUID, @RequestBody jugadorRequest: Jugador): ResponseEntity<Jugador> {
        try {
            return if (usuarioService.existsById(id)) {


                val jugadorOptional = jugadorService.findByUsuarioId(id)
                if (jugadorOptional.isPresent) {
                    val jugador = jugadorOptional.get()

                    jugador.apply {
                        deporteFavorito = jugadorRequest.deporteFavorito
                        polideportivoAsociado  = jugadorRequest.polideportivoAsociado
                    }
                    jugadorService.edit(jugador)

                    ResponseEntity(jugador, HttpStatus.OK)
                } else {
                    ResponseEntity(HttpStatus.NOT_FOUND)
                }
            }else{
                ResponseEntity(HttpStatus.NOT_FOUND)
            }
        } catch (e: Exception) {
            return ResponseEntity(HttpStatus.INTERNAL_SERVER_ERROR)
        }
    }

}