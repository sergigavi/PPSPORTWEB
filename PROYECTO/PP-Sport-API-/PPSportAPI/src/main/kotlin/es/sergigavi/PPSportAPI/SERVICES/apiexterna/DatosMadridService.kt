package es.sergigavi.PPSportAPI.SERVICES.apiexterna

import org.springframework.stereotype.Service
import org.springframework.web.client.RestTemplate

@Service
class DatosMadridService(private val restTemplate: RestTemplate) {

    fun obtenerDatosPolideportivos(): String? {
        val url = "https://datos.madrid.es/egob/catalogo/200215-0-instalaciones-deportivas.json"
        val response = restTemplate.getForEntity(url, String::class.java)
        if (response.statusCode.is2xxSuccessful) {
            return response.body
        }
        return null
    }
}