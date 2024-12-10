package es.sergigavi.PPSportAPI.MODEL.REQUEST

import lombok.*

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
data class UsuarioLogin(

    var email: String,

    var password: String
)
{
}