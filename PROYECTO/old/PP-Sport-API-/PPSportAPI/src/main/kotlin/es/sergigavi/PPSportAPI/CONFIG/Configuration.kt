package es.sergigavi.PPSportAPI.CONFIG


import es.sergigavi.PPSportAPI.REPOSITORIES.UsuarioRepository
import es.sergigavi.PPSportAPI.SERVICES.AUTH.CustomUserDetailsService
import org.springframework.boot.context.properties.EnableConfigurationProperties
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.security.authentication.AuthenticationManager
import org.springframework.security.authentication.AuthenticationProvider
import org.springframework.security.authentication.dao.DaoAuthenticationProvider
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration
import org.springframework.security.core.userdetails.UserDetailsService
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.web.client.RestTemplate

@Configuration
@EnableConfigurationProperties(JwtProperties::class)
class Configuration {
    @Bean
    fun userDetailsService(usuarioRepository: UsuarioRepository): UserDetailsService =
        CustomUserDetailsService(usuarioRepository)

    @Bean
    fun encoder(): PasswordEncoder = BCryptPasswordEncoder()

    @Bean
    fun authenticationProvider(usuarioRepository: UsuarioRepository): AuthenticationProvider =
        DaoAuthenticationProvider()
            .also {
                it.setUserDetailsService(userDetailsService(usuarioRepository))
                it.setPasswordEncoder(encoder())
            }

    @Bean
    fun authenticationManager(config: AuthenticationConfiguration): AuthenticationManager =
        config.authenticationManager

    @Bean
    fun restTemplate(): RestTemplate {
        return RestTemplate()
    }
}