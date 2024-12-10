package es.sergigavi.PPSportAPI.MODEL.REQUEST

import lombok.*
import java.time.LocalDate

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
data class UsuarioRegister(

    var dni:String,

    var nombre:String,

    var apellido1:String,

    var apellido2:String?,

    var fechaNacimiento: LocalDate,

    var email:String,

    var password:String,
)
{
}