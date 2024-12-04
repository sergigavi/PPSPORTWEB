package es.sergigavi.PPSportAPI.MODEL

enum class Rol{
    USUARIO,ADMIN
}

enum class Deporte{
    TENIS, PADEL, FUTBOL_7, FUTBOL_SALA, FUTBOL_11, BALONCESTO
}

enum class TipoTorneo{
    LIGA,ELIMINATORIO
}

enum class Estado{
    CREADO,EN_CURSO,FINALIZADO,CANCELADO
}

enum class DesenlacePartido{
    EMPATE,DERROTA,VICTORIA
}