package es.sergigavi.PPSportAPI.MODEL

import jakarta.persistence.*
import java.time.LocalDate
import java.util.*

@Entity
data class Administrador(

    //Deberían ser private var pero no me funciona el lombok asi que se queda asi
    @Id
    @Column(name = "id", unique = true, length = 36 )
    val id:UUID,  //UUID

    @Column(name = "permisos", unique = false, nullable = true, length = 256)
    val permisos:String,

    //@Column(name = "Equipo_Id", unique = false, nullable = true)
    @ManyToOne(optional = false, cascade = [CascadeType.ALL], fetch = FetchType.EAGER)
    val polideportivoAsociado:Polideportivo,

    @OneToOne(optional = false, cascade = [CascadeType.ALL], fetch = FetchType.EAGER)
    val usuario: Usuario

)
