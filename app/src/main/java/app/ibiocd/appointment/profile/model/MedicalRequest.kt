package app.ibiocd.appointment.profile.model

import com.google.gson.annotations.SerializedName

data class MedicalRequest(
    @SerializedName("nameLast") var nameLast: String="",
    @SerializedName("name") var name: String="",
    @SerializedName("dni") var dni: String="",
    @SerializedName("email") var email: String="",
    @SerializedName("direction") var direction: String="::",
    @SerializedName("username") var username: String="",
    @SerializedName("password") var password: String="",
    @SerializedName("tuition") var tuition: String="",
    @SerializedName("phone") var phone: String="",
    @SerializedName("profession") var profession: String="",
    @SerializedName("img") var img: String="",
    @SerializedName("tokenNot") var tokenNot: String="",
    @SerializedName("hourOn") val hourOn: String=""

)
