package es.sergigavi.PPSportAPI.MODEL


import jakarta.persistence.*
import java.util.*

@Entity
data class Jugador(

    @Id
    val id: UUID?=null,
    //Deberían ser private var pero no me funciona el lombok asi que se queda asi

    @Column(name = "deporte_favorito", unique = false, nullable = true, length = 256)
    val deporteFavorito:Deporte?=null,

    @ManyToMany
    @JoinTable(
        name = "jugador_equipo",
        joinColumns = [JoinColumn(name = "jugador_id")],
        inverseJoinColumns = [JoinColumn(name = "equipo_id")]
    )
    val equipos:MutableSet<Equipo> = mutableSetOf(),

    @ManyToOne(optional = true, cascade = [CascadeType.ALL], fetch = FetchType.EAGER)
    @JoinColumn(name = "polideportivo_asociado_id", unique = false, nullable = true)
    val polideportivoAsociado:Polideportivo?=null,

    @JoinColumn(name = "usuario_id", referencedColumnName = "id")
    @OneToOne(optional = false, cascade = [CascadeType.ALL], fetch = FetchType.EAGER)
    val usuario: Usuario,

    @OneToMany(mappedBy = "jugador", cascade = [CascadeType.ALL], orphanRemoval = true)
    val torneos:MutableSet<JugadorTorneo> = mutableSetOf(),

    @OneToMany(mappedBy = "jugador", cascade = [CascadeType.ALL], orphanRemoval = true)
    val jugadorPartido :MutableSet<JugadorPartido> = mutableSetOf()

    )

