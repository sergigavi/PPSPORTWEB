package es.sergigavi.PPSportAPI.MODEL

import jakarta.persistence.*
import java.time.LocalDateTime
import java.util.UUID


@Entity
data class Partido(

    @Id
    val id:UUID?=null,

    @Column( unique = false, nullable = true)
    var fecha: LocalDateTime? = null,

    @OneToMany(mappedBy = "partido", cascade = [CascadeType.ALL], orphanRemoval = true)
    val jugadoresPartidos :MutableSet<JugadorPartido> = mutableSetOf(),

    @OneToMany(mappedBy = "partido", cascade = [CascadeType.ALL], orphanRemoval = true)
    var equiposPartidos:MutableSet<EquipoPartido> = mutableSetOf(),

    @ManyToOne(optional = true, cascade = [CascadeType.ALL], fetch = FetchType.LAZY)
    @JoinColumn(name = "torneo_id", unique = false, nullable = true)
    val torneo: Torneo,

    @Column( unique = false, nullable = true)
    var resultado:String?=null,

    val ronda:Int

    ){
    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (other == null || javaClass != other.javaClass) return false
        other as Partido
        return id == other.id // Comparar solo el id para evitar recursión
    }

    override fun hashCode(): Int {
        return id?.hashCode() ?: 0 // Solo el hashCode del id
    }
    override fun toString(): String {
        return "Partido(id=$id, fecha=$fecha,resultado=$resultado)"
    }
}
