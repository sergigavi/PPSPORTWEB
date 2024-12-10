package es.sergigavi.PPSportAPI.CONTROLLERS

import es.sergigavi.PPSportAPI.MODEL.AUTH.AuthenticationRequest
import es.sergigavi.PPSportAPI.MODEL.AUTH.AuthenticationResponse
import es.sergigavi.PPSportAPI.MODEL.AUTH.RefreshTokenRequest
import es.sergigavi.PPSportAPI.MODEL.AUTH.TokenResponse
import es.sergigavi.PPSportAPI.SERVICES.AUTH.IAuthenticationService
import es.sergigavi.PPSportAPI.Utilities.Utilities

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import org.springframework.web.server.ResponseStatusException

@RestController
@RequestMapping("/ppsport/api/auth")
class AuthController(

) {
    @Autowired
    lateinit var authenticationService: IAuthenticationService;

     @PostMapping
     fun autentificar(@RequestBody authRequest: AuthenticationRequest): AuthenticationResponse {
         Utilities.LineaSeparadora();
         println("Usuario " + authRequest.email + " Iniciando sesión")
         Utilities.LineaSeparadora();
         return authenticationService.autentificar(authRequest)
     }

    @PostMapping("/refresh")
    fun refrescarAccessToken(@RequestBody request: RefreshTokenRequest): TokenResponse =
        authenticationService.refrescarAccessToken(request.token)
            ?.mapToTokenResponse()
            ?:throw ResponseStatusException(HttpStatus.FORBIDDEN,"Invalid request")

    private fun String.mapToTokenResponse():TokenResponse =
        TokenResponse(token = this)

}