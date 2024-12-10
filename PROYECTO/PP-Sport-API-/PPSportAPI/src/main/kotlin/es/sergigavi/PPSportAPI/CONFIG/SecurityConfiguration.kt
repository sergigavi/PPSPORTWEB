package es.sergigavi.PPSportAPI.CONFIG

import org.springframework.context.annotation.Bean
import org.springframework.http.HttpMethod
import org.springframework.security.authentication.AuthenticationProvider
import org.springframework.security.config.annotation.web.builders.HttpSecurity
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity
import org.springframework.security.config.http.SessionCreationPolicy
import org.springframework.security.web.DefaultSecurityFilterChain
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter
import org.springframework.stereotype.Component

@Component
@EnableWebSecurity
class SecurityConfiguration (
    private val authenticationProvider: AuthenticationProvider
) {

    @Bean
    fun securityFilterChain(
        http:HttpSecurity,
        jwtAuthenticationFilter: JwtAuthenticationFilter
    ):DefaultSecurityFilterChain =
        http.csrf{it.disable()}
            .authorizeHttpRequests{
//                it.requestMatchers("**")
//                    .permitAll()
                //Llamadas que no requieren autenticación
                it.requestMatchers("/ppsport/api/auth","/ppsport/api/auth/refresh","/ppsport/api/usuarios/init", "/ppsport/api/polideportivos/cargar-polideportivos-api")
                    .permitAll()
                    .requestMatchers(HttpMethod.POST,"/ppsport/api/usuarios/registrar")
                    .permitAll()
                    //Llamadas que requieren el rol de ADMIN
                    .requestMatchers("/ppsport/api/torneos/crear","/ppsport/api/equipos/registrar")
                    .hasRole("ADMIN")
                    .requestMatchers(HttpMethod.PUT,"/ppsport/api/torneos**", "/ppsport/api/equipos**",
                        "/ppsport/api/partidos**")
                    .hasRole("ADMIN")
                    .requestMatchers(HttpMethod.DELETE,"/ppsport/api/torneos**", "/ppsport/api/equipos**",
                        "/ppsport/api/partidos**")
                    .hasRole("ADMIN")
                    //Resto de llamadas que no requieren rol en especifico pero si autenticación
                    .anyRequest()
                    .fullyAuthenticated()
            }.sessionManagement {
                it.sessionCreationPolicy(SessionCreationPolicy.STATELESS)
            }.authenticationProvider(authenticationProvider)
            .addFilterBefore(jwtAuthenticationFilter,UsernamePasswordAuthenticationFilter::class.java)
            .build()
}