package es.sergigavi.PPSportAPI.MODEL

import com.fasterxml.jackson.annotation.JsonIgnore
import com.fasterxml.jackson.annotation.JsonManagedReference
import jakarta.persistence.*
import java.io.Serializable
import java.time.LocalTime
import java.util.UUID

@Entity
data class Polideportivo(

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    var id:UUID?=null,

    @Column(name = "nombre", unique = false, nullable = false, length = 120)
    var nombre:String,

    @Column(name = "direccion", unique = false, nullable = false, length = 100)
    var direccion:String,

    @Column(name = "ciudad", unique = false, nullable = false, length = 40)
    var localidad:String,

    @Column(name = "cp", unique = false, nullable = false, length = 8)
    var cp:String,

    @Column(name = "hora_apertura", nullable = true)
    var horaApertura:LocalTime?=null,

    @Column(name = "hora_cierre", nullable = true)
    var horaCierre:LocalTime?=null,

    @Column(name = "latitud", nullable = true)
    var latitud:Double?=null,

    @Column(name = "longitud", nullable = true)
    var longitud:Double?=null,

    @Column(name = "horario", nullable = true)
    var horario:String?=null,

    @Lob
    @Column(name = "foto", nullable = true, columnDefinition = "LONGBLOB")
    val foto: ByteArray?=null,

    @OneToMany(mappedBy = "polideportivo",cascade = [CascadeType.ALL], fetch = FetchType.LAZY)
    @JsonIgnore
    var pistas: MutableSet<Pista> = mutableSetOf(),

    @OneToMany(mappedBy = "polideportivo",cascade = [CascadeType.ALL], fetch = FetchType.LAZY)
    @JsonIgnore
    var inventario: MutableSet<Item> = mutableSetOf(),

//    @OneToMany(mappedBy = "polideportivo",cascade = [CascadeType.ALL], fetch = FetchType.LAZY)
//    @JsonManagedReference
//    var inventario: MutableSet<Item> = mutableSetOf(),

    @OneToMany(mappedBy = "polideportivoAsociado",cascade = [CascadeType.ALL], fetch = FetchType.LAZY)
    @JsonManagedReference
    @JsonIgnore
    var jugadoresAsociados: MutableSet<Jugador> = mutableSetOf(),



    ):Serializable{
    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (other == null || javaClass != other.javaClass) return false
        other as Polideportivo
        return id == other.id // Comparar solo el id para evitar recursión
    }

    override fun hashCode(): Int {
        return id?.hashCode() ?: 0 // Solo el hashCode del id
    }
    }
