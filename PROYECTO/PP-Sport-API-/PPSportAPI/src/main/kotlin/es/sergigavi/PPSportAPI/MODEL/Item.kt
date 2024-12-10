package es.sergigavi.PPSportAPI.MODEL

import jakarta.persistence.*
import lombok.*
import lombok.Builder.Default
import java.time.LocalDate
import java.util.UUID


@Entity
class Item(

    @Id
    @Column(name = "ID", unique = true)
    var id:UUID?=null,

    @Column(name = "nombre", unique = false, nullable = false, length = 100)
    var nombre:String,

    @Column(name = "unidades", unique = false, nullable = false)
    var unidades: Int,

    @ManyToOne(optional = false, cascade = [CascadeType.ALL], fetch = FetchType.EAGER)
    val polideportivo: Polideportivo,

)
