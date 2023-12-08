package app.ibiocd.appointment.util

import android.util.Log
import org.json.JSONObject
import java.util.*
import javax.crypto.SecretKey

class UtilJwtParser(private val jwt: String) {

    private fun decodeJwt(jwt: String): JSONObject? {
        try {
            val parts = jwt.split(".")

            val charset = charset("UTF-8")
                val header = String(Base64.getUrlDecoder().decode(parts[0].toByteArray(charset)), charset)
                val payload = String(Base64.getUrlDecoder().decode(parts[1].toByteArray(charset)), charset)


            return  JSONObject(payload)
        } catch (e: Exception) {
            // Maneja la excepción en caso de que el JWT no sea válido
            e.printStackTrace()
            return null
        }
    }
    fun getUsername(): String? {

        return decodeJwt(jwt)?.getString("sub").toString()
    }

    fun getRole(): String {
        return decodeJwt(jwt)?.getString("roles").toString().removePrefix("ROLE_")
    }

    fun isValid(): Boolean {
        // Lógica para verificar la validez del token
        // return jwt.isValid()
        return false
    }
}