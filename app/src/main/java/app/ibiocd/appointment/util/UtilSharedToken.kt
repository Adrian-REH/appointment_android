package app.ibiocd.appointment.util

import android.content.Context
import android.content.SharedPreferences

class UtilSharedToken(context: Context) {

    private val sharedPreferences: SharedPreferences =
        context.getSharedPreferences("jwt_prefs", Context.MODE_PRIVATE)

    fun saveJwt(jwt: String) {
        val editor = sharedPreferences.edit()
        editor.putString("jwt", jwt)
        editor.apply()
    }

    fun getJwt(): String? {
        return sharedPreferences.getString("jwt", "")
    }

    fun clearJwt() {
        val editor = sharedPreferences.edit()
        editor.remove("jwt")
        editor.apply()
    }
}