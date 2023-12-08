package app.ibiocd.appointment.appointment.datasource

import app.ibiocd.appointment.model.*
import retrofit2.Call
import retrofit2.http.*


interface AppointmentDataSource {

    //APPOINTMENT
    @GET("appointment")
    suspend fun  getAllAppointments(): List<Appointment>
    @GET("appointment/patients/{patients}")
    suspend fun  getPatientturno(@Path("patients") patient:String): List<Appointment>

    @GET("appointment/medicals/{medicals}")
    suspend fun  getMedicalturno(@Path("medicals")  medical:String): List<Appointment>

    @FormUrlEncoded
    @POST("appointment")
    fun postAppointment(
        @Field("fecha") fecha:String,
        @Field("hora") hora:String,
        @Field("coment") coment:String,
        @Field("specialty") specialty:String,
        @Field("patient") patient:String,
        @Field("medical") medical:String,
        @Field("profession") profession:String,
        @Field("files") files:String,
        @Field("price") price:String
    ): Call<AppointmentRespons>

}
