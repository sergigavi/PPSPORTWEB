package es.sergigavi.PPSportAPI.SERVICES.AUTH

import es.sergigavi.PPSportAPI.CONFIG.JwtProperties
import es.sergigavi.PPSportAPI.MODEL.AUTH.AuthenticationRequest
import es.sergigavi.PPSportAPI.MODEL.AUTH.AuthenticationResponse
import es.sergigavi.PPSportAPI.REPOSITORIES.RefreshTokenRepository
import org.springframework.security.authentication.AuthenticationManager
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.security.core.userdetails.UserDetails
import org.springframework.stereotype.Service
import java.util.*

@Service
class AuthenticationServiceImpl(
    private val authManager: AuthenticationManager,
    private val userDetailsService: CustomUserDetailsService,
    private val tokenService: TokenService,
    private val jwtProperties: JwtProperties,
    private val refreshTokenRepository: RefreshTokenRepository
) : IAuthenticationService {
    override fun autentificar(authRequest: AuthenticationRequest): AuthenticationResponse {
        authManager.authenticate(
            UsernamePasswordAuthenticationToken(
                authRequest.email,
                authRequest.password
            )
        )
        val user = userDetailsService.loadUserByUsername(authRequest.email)
        val accessToken = genereateAccessToken(user)
        val refreshToken = genereateRefreshToken(user)

        refreshTokenRepository.save(refreshToken,user)
        return AuthenticationResponse(
            accessToken = accessToken,
            refreshToken = refreshToken
        )
    }

    override fun refrescarAccessToken(token: String): String? {
        val extractedEmail = tokenService.extractEmail(token)

        return extractedEmail?.let { email ->
            val currentUserDetails = userDetailsService.loadUserByUsername(email)
            val refreshTokenUserDetails = refreshTokenRepository.findUserDetailsByToken(token)

            if(!tokenService.isExpired(token) && currentUserDetails.username == refreshTokenUserDetails?.username)
                genereateAccessToken(currentUserDetails)
            else
                null
        }
    }

    private fun genereateAccessToken(user: UserDetails) = tokenService.generate(
        userDetails = user,
        expirationDate = Date(System.currentTimeMillis() + jwtProperties.accessTokenExpiration)
    )

    private fun genereateRefreshToken(user: UserDetails) = tokenService.generate(
        userDetails = user,
        expirationDate = Date(System.currentTimeMillis() + jwtProperties.refreshTokenExpiration)
    )
}