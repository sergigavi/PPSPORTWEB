package es.sergigavi.PPSportAPI.MODEL

import jakarta.persistence.Column

data class ItemRequest(
    val nombre:String,
    val unidades: Int,
)
