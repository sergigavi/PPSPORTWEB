package es.sergigavi.PPSportAPI.MODEL


import jakarta.persistence.*
import java.util.*

@Entity
data class Jugador(

    @Id
    val id: UUID?=null,
    //Deberían ser private var pero no me funciona el lombok asi que se queda asi

    @Column(name = "deporte_favorito", unique = false, nullable = true, length = 256)
    var deporteFavorito:Deporte?=null,
 
    @ManyToMany(cascade = [CascadeType.ALL])
    @JoinTable(
        name = "jugador_equipo",
        joinColumns = [JoinColumn(name = "jugador_id")],
        inverseJoinColumns = [JoinColumn(name = "equipo_id")]
    )
    val equipos:MutableSet<Equipo> = mutableSetOf(),

    @ManyToOne(optional = true, cascade = [CascadeType.ALL], fetch = FetchType.EAGER)
    @JoinColumn(name = "polideportivo_asociado_id", unique = false, nullable = true)
    var polideportivoAsociado:Polideportivo?=null,

    @JoinColumn(name = "usuario_id")
    @OneToOne(optional = false, cascade = [CascadeType.ALL], fetch = FetchType.LAZY)
    val usuario: Usuario,

    @OneToMany(mappedBy = "jugador", cascade = [CascadeType.ALL], orphanRemoval = true,fetch = FetchType.EAGER)
    val torneos:MutableSet<JugadorTorneo> = mutableSetOf(),

    @OneToMany(mappedBy = "jugador", cascade = [CascadeType.ALL], orphanRemoval = true)
    val jugadorPartido :MutableSet<JugadorPartido> = mutableSetOf()

    ){
    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (other == null || javaClass != other.javaClass) return false
        other as Jugador
        return id == other.id // Comparar solo el id para evitar recursión
    }

    override fun hashCode(): Int {
        return id?.hashCode() ?: 0 // Solo el hashCode del id
    }
    override fun toString(): String {
        return "Jugador(id=$id, nombre=$usuario.nombre,deporteFavorito=$deporteFavorito)"
    }
}

