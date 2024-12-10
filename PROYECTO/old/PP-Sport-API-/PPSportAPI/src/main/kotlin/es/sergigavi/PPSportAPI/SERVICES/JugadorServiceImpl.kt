package es.sergigavi.PPSportAPI.SERVICES

import es.sergigavi.PPSportAPI.MODEL.Jugador
import es.sergigavi.PPSportAPI.REPOSITORIES.JugadorRepository
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service
import java.util.*

@Service
class JugadorServiceImpl : IJugadorService {

    @Autowired
    lateinit var JugadorDAO: JugadorRepository;


}