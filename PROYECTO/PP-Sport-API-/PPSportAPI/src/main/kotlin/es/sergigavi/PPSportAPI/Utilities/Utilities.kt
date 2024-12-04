package es.sergigavi.PPSportAPI.Utilities

import java.security.MessageDigest

public class Utilities {

    companion object{

        public fun HashearPassword(password: String) : String
        {
            val bytes = password.toByteArray()
            val md = MessageDigest.getInstance("SHA-512")
            val digest = md.digest(bytes)
            return digest.fold("") { str, it -> str + "%02x".format(it) }
        }

        public fun LineaSeparadora()
        {
            println("\n**-------------------------------**\n")
        }

    }


}