package es.sergigavi.PPSportAPI.MODEL

import jakarta.persistence.*
import lombok.*
import java.time.LocalDate
import java.util.UUID


@Entity
data class Equipo(

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    var id: UUID?=null,

    @Column(unique = false, nullable = false, length = 100)
    var nombre: String,

    @Column(unique = false, nullable = false)
    val deporte: Deporte,

    @ManyToMany(mappedBy = "equipos")
    val jugadores: MutableSet<Jugador> = mutableSetOf(),

    @OneToMany(mappedBy = "equipo", cascade = [CascadeType.ALL], orphanRemoval = true)
    val equipoTorneos: MutableSet<EquipoTorneo> = mutableSetOf(),

    @OneToMany(mappedBy = "equipo", cascade = [CascadeType.ALL], orphanRemoval = true)
    val equipoPartidos: MutableSet<EquipoPartido> = mutableSetOf()
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
        return "Equipo(id=$id, nombre=$nombre,deporte=$deporte)"
    }
}
