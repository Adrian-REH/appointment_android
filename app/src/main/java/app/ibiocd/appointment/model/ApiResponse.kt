package app.ibiocd.appointment.model

import app.ibiocd.appointment.profile.datasource.entity.Medical
import com.google.gson.annotations.SerializedName

data class ApiAptListResponse(
    val result: List<Appointment> = emptyList(),
)
data class ApiAptFileListResponse(
    val result: List<Files> = emptyList(),
)
data class ApiPatientListResponse(
    val result: List<Patient> = emptyList(),
)
data class ApiPatientResponse(
    val result: Patient,
)
data class ApiMedicalListResponse(
    val result: List<Medical> = emptyList(),
)
data class ApiMedicalResponse(
    val result: Medical,
)

data class ApiFavs(
    val result: List<Favs> = emptyList(),
)

data class ApiDirection(
    val bbox: List<Double> = emptyList(),
)


data class ApiLaboratory(
    val result: List<Labs> = emptyList(),
)
data class ApiSede(
    val result: List<Sedes> = emptyList(),
)
data class ApiDate(
    val result: List<Date> = emptyList(),
)
data class ApiOdontogram(
    val result: List<OdontogramResponse> = emptyList(),
)
data class ApiTooth(
    val data: List<Tooths> = emptyList(),
)
data class ApiSpecialty(
    val result: List<Specialty> = emptyList(),
)


data class PushNotification(
    val data: NotificationData,
    val to: String
)

data class NotificationData(
    val title: String,
    val message: String
)
data class OdontogramResponse(
    @SerializedName("_id") var _id:String,
    @SerializedName("data") val data: List<Tooth> = emptyList(),
    @SerializedName("medicals") var medical:String,
    @SerializedName("patients") var patient:String
    )
data class UpdateResponse(
    @SerializedName("acknowledged") var acknowledged:String,
    @SerializedName("modifiedCount") var modifiedCount:String,
    @SerializedName("upsertedId") var upsertedId:String,
    @SerializedName("upsertedCount") var upsertedCount:String,
    @SerializedName("matchedCount") var matchedCount:String
)
data class DeleteResponse(
    @SerializedName("acknowledged") var acknowledged:String,
    @SerializedName("deletedCount") var modifiedCount:String
)

data class AppointmentRespons(
    @SerializedName("_id") var _id: String,
    @SerializedName("fecha") var fecha:String,
    @SerializedName("hora") var hora:String,
    @SerializedName("coment") var coment:String,
    @SerializedName("specialty") var specialty:String,
    @SerializedName("patients") var patient:String,
    @SerializedName("medicals") var medical:String,
    @SerializedName("profession") var profession:String,
    @SerializedName("files") var files:String,
    @SerializedName("price") var price:String
)
data class FavsRespons(
    @SerializedName("_id") var _id:String,
    @SerializedName("medicals") var medical:String,
    @SerializedName("patients") var patient:String
)


data class MedicalResponse(

    @SerializedName("id") var _id:String,
    @SerializedName("nameLast") var name_last:String,
    @SerializedName("dni") var dni:String,
    @SerializedName("phone") var phone:String,
    @SerializedName("profession") var profession:String,
    @SerializedName("email") var email:String,
    @SerializedName("direction") var direction:String,
    @SerializedName("tuition") var tuition:String,
    @SerializedName("img") var img:String,
    @SerializedName("tokenNot") var token_not:String,
    @SerializedName("hourOn") var hour_on:String
)

data class PatientResponse(
    @SerializedName("id") var id:String,
    @SerializedName("username") var username:String,
    @SerializedName("nameLast") var nameLast:String,
    @SerializedName("name") var name:String,
    @SerializedName("password") var password:String,
    @SerializedName("dni") var dni:String,
    @SerializedName("phone") var phone:String,
    @SerializedName("email") var email:String,
    @SerializedName("direction") var direction:String,
    @SerializedName("img") var img:String,
    @SerializedName("tokenNot") var tokenNot:String
)
data class SpecialtyRespons(
    @SerializedName("_id") var _id:String,
    @SerializedName("title") var title:String,
    @SerializedName("comment") var comment:String,
    @SerializedName("medicals") var medical:String,
    @SerializedName("offer") var offer:String,
    @SerializedName("price") var price:String
)

data class DateResponse(
    @SerializedName("_id") var _id:String,
    @SerializedName("medicals") var medical:String,
    @SerializedName("lunes") var lunes:String,
    @SerializedName("martes") var martes:String,
    @SerializedName("miercoles") var miercoles:String,
    @SerializedName("jueves") var jueves:String,
    @SerializedName("viernes") var viernes:String,
    @SerializedName("sabado") var sabado:String,
    @SerializedName("domingo") var domingo:String
)