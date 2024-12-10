package es.sergigavi.PPSportAPI.MODEL

import jakarta.persistence.*
import java.time.LocalDateTime
import java.util.UUID


@Entity
data class Torneo(

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    val id:UUID?=null,

    @Column(unique = false, nullable = false )
    val deporte: Deporte,

    @Column(unique = false, nullable = false )
    var nombre: String,

    @Column(name = "tipo_torneo", unique = false, nullable = false )
    val tipoTorneo: TipoTorneo,

    @Column(unique = false, nullable = false )
    var estado:Estado,

    @Column(name = "fecha_inicio", unique = false, nullable = false)
    val fechaInicio: LocalDateTime,

    @Column(name = "fecha_fin", unique = false, nullable = false)
    val fechaFin: LocalDateTime,

    @OneToMany(mappedBy = "torneo", cascade = [CascadeType.ALL], fetch = FetchType.EAGER, orphanRemoval = true)
    val partidos: MutableSet<Partido> = mutableSetOf(),

    @OneToMany(mappedBy = "torneo", cascade = [CascadeType.ALL], orphanRemoval = true,fetch = FetchType.EAGER)
    val jugadoresTorneo: MutableSet<JugadorTorneo> = mutableSetOf(),

    @OneToMany(mappedBy = "torneo", cascade = [CascadeType.ALL], orphanRemoval = true,fetch = FetchType.EAGER)
    val equiposTorneo: MutableSet<EquipoTorneo> = mutableSetOf(),

    ){
    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (other == null || javaClass != other.javaClass) return false
        other as Torneo
        return id == other.id // Comparar solo el id para evitar recursión
    }

    override fun hashCode(): Int {
        return id?.hashCode() ?: 0 // Solo el hashCode del id
    }
    override fun toString(): String {
        return "Torneo(id=$id, nombre=$nombre,deporte=$deporte,tipoTorneo=$tipoTorneo,estado=$estado)"
    }
}
