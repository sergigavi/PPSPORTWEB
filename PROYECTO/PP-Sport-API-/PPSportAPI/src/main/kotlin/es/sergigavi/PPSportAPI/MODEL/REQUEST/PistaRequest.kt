package es.sergigavi.PPSportAPI.MODEL.REQUEST

import es.sergigavi.PPSportAPI.MODEL.Deporte

data class PistaRequest(
    var nombre:String,
    var tipoPista: Deporte,
)
