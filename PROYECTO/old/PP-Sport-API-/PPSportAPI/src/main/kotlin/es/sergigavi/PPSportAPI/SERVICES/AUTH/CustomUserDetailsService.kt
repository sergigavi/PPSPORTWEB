package es.sergigavi.PPSportAPI.SERVICES.AUTH

import es.sergigavi.PPSportAPI.REPOSITORIES.UsuarioRepository

import org.springframework.security.core.userdetails.User
import org.springframework.security.core.userdetails.UserDetails
import org.springframework.security.core.userdetails.UserDetailsService
import org.springframework.security.core.userdetails.UsernameNotFoundException
import org.springframework.stereotype.Service

typealias ApplicationUser = es.sergigavi.PPSportAPI.MODEL.Usuario


@Service
class CustomUserDetailsService(private val usuarioRepository: UsuarioRepository): UserDetailsService {
    override fun loadUserByUsername(username: String): UserDetails =
        usuarioRepository.findByEmail(username).get().mapToUserDetails()
            ?:throw UsernameNotFoundException("No se encontró")


    private fun ApplicationUser.mapToUserDetails(): UserDetails =
        User.builder()
            .username(this.email)
            .password(this.password)
            .roles(this.rol.name)
            .build()

}