package es.sergigavi.PPSportAPI.CONTROLLERS

import es.sergigavi.PPSportAPI.MODEL.REQUEST.UsuarioRegister
import es.sergigavi.PPSportAPI.MODEL.Rol
import es.sergigavi.PPSportAPI.MODEL.Usuario
import es.sergigavi.PPSportAPI.SERVICES.IUsuarioService
import es.sergigavi.PPSportAPI.Utilities.Utilities
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
    lateinit var usuarioService: IUsuarioService;

    @GetMapping("/init")
    fun test():ResponseEntity<String>{
    //los dejo sin hashear para poder usar los user desde postman
        usuarioService.add(
            Usuario(
                id=null,
                dni = "00000000T",
                nombre = "Sergio",
                apellido1 = "Garcia",
                apellido2 = "Villalba",
                fechaNacimiento = LocalDate.parse("2002-10-29"),
                email = "sergito.gv@gmail.com",
                password = "1234",
                rol = Rol.USUARIO

            )
        )

        usuarioService.add(
            Usuario(
                id=null,
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

        var allUsuarios: Iterable<Usuario> = this.usuarioService.findAll();
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
    fun registrar(@RequestBody usuario: UsuarioRegister): ResponseEntity<Usuario> {

        try {

            Utilities.LineaSeparadora();
            println("Se va a registrar el usuario: " + usuario.dni)

            if (usuarioService.existsByEmail(usuario.email) || usuarioService.existsByDni(usuario.dni))
            {
                return ResponseEntity(HttpStatus.CONFLICT)
            }

            val UsuarioNew = Usuario(UUID.randomUUID(), usuario.dni, usuario.nombre, usuario.apellido1, usuario.apellido2, usuario.fechaNacimiento, usuario.email, usuario.password, Rol.USUARIO )

            if (usuarioService.add(UsuarioNew))
            {
                println("Se ha insertado el usuario " + usuario.dni + " correctamente")
                Utilities.LineaSeparadora()
                return ResponseEntity(UsuarioNew, HttpStatus.OK)
            }else{
                return ResponseEntity(HttpStatus.INTERNAL_SERVER_ERROR)
            }

        }catch (e: Exception){
            println(e.printStackTrace())
            Utilities.LineaSeparadora();
            return ResponseEntity(HttpStatus.NOT_FOUND)
        }

    }
    //TODO añadir editar para usuario y get para info de jugador

}