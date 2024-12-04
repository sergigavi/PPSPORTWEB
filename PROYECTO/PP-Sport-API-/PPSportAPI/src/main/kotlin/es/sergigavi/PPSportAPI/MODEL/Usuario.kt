package es.sergigavi.PPSportAPI.MODEL

import jakarta.persistence.*
import java.time.LocalDate
import java.util.UUID

@Entity
data class Usuario (

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    val id:UUID?=null,
    @Column(name = "dni", unique = true, nullable = false, length = 10)
    val dni:String,

    @Column(name = "nombre", unique = false, nullable = false, length = 60)
    val nombre:String,

    @Column(name = "apellido1", unique = false, nullable = false, length = 40)
    val apellido1:String,

    @Column(name = "apellido2", unique = false, nullable = true, length = 40)
    val apellido2:String?=null,

    @Column(name = "fechaNacimiento", unique = false, nullable = false)
    val fechaNacimiento: LocalDate,

    @Column(name = "email", unique = false, nullable = false, length = 40)
    val email:String,

    @Column(name = "password", unique = false, nullable = false, length = 256)
    val password:String,

    @Column(name = "rol", unique = false, nullable = false)
    val rol:Rol
)

