package app.ibiocd.appointment.profile.model

import com.google.gson.annotations.SerializedName

data class PatientRequest(
    @SerializedName("nameLast") var nameLast: String="",
    @SerializedName("username") var username: String="",
    @SerializedName("password") var password: String="",
    @SerializedName("dni") var dni: String="",
    @SerializedName("name") var name: String="",
    @SerializedName("email") var email: String="",
    @SerializedName("phone") var phone: String="",
    @SerializedName("direction") var direction: String="::",
    @SerializedName("img") var img: String="",
    @SerializedName("tokenNot") var tokenNot: String="",

)
