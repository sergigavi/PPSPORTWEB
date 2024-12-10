package es.sergigavi.PPSportAPI.MODEL

import jakarta.persistence.*
import java.util.UUID


@Entity
data class Pista(

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    var id:UUID?=null,

    @Column(name = "nombre", unique = false, nullable = false, length = 100)
    var nombre:String,

    @Column(name = "tipo_pista", unique = false, nullable = false)
    var tipoPista: Deporte,

    @ManyToOne(optional = false, cascade = [CascadeType.ALL], fetch = FetchType.EAGER)
    val polideportivo: Polideportivo,

){
    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (other == null || javaClass != other.javaClass) return false
        other as Pista
        return id == other.id // Comparar solo el id para evitar recursión
    }

    override fun hashCode(): Int {
        return id?.hashCode() ?: 0 // Solo el hashCode del id
    }
    override fun toString(): String {
        return "Pista(id=$id, nombre=$nombre,tipoPista=$tipoPista)"
    }
}
