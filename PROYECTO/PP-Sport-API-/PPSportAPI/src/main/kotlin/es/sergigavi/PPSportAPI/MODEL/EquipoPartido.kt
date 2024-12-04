package es.sergigavi.PPSportAPI.MODEL

import jakarta.persistence.*
import java.util.*

@Entity
data class EquipoPartido(
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    val id: UUID? = null,

    @ManyToOne
    @JoinColumn(name = "partido_id")
    val partido: Partido,

    @ManyToOne
    @JoinColumn(name = "equipo_id")
    val equipo: Equipo,

    @Column(name = "resultado")
    var resultado: String? = null,

    @Column(name = "desenlace_partido")
    var desenlacePartido: DesenlacePartido = DesenlacePartido.EMPATE

)
