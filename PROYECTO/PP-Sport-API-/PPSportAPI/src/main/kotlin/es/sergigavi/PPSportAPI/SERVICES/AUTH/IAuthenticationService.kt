package es.sergigavi.PPSportAPI.SERVICES.AUTH

import es.sergigavi.PPSportAPI.MODEL.AUTH.AuthenticationRequest
import es.sergigavi.PPSportAPI.MODEL.AUTH.AuthenticationResponse
import es.sergigavi.PPSportAPI.MODEL.AUTH.RefreshTokenRequest
import es.sergigavi.PPSportAPI.MODEL.AUTH.TokenResponse

interface IAuthenticationService {
    fun autentificar(authRequest: AuthenticationRequest): AuthenticationResponse
    fun refrescarAccessToken(token:String):String?
}