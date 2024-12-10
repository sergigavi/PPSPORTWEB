package es.sergigavi.PPSportAPI.MODEL.DTO

import es.sergigavi.PPSportAPI.MODEL.Rol
import java.time.LocalDate
import java.util.*

data class UsuarioDTO(

    val id: UUID,
    val dni:String,
    val nombre:String,
    val apellido1:String,
    val apellido2:String?,
    val fechaNacimiento: LocalDate,
    val email:String,
    val password:String,
    val rol: Rol
)