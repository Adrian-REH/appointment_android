package app.ibiocd.appointment.profile.datasource.retrofit

import app.ibiocd.appointment.model.*
import app.ibiocd.appointment.profile.model.PatientRequest
import retrofit2.Call
import retrofit2.Response
import retrofit2.http.*

interface PatientDataSource {
    //PATIENT
    @GET("patients")
    suspend fun  getAllPatient(): ApiPatientListResponse
    @GET("patients/{username}")
    suspend fun  getPatientUsername(@Path("username") username:String): ApiPatientResponse
    @FormUrlEncoded
    @PUT("patients/{id}")
    fun putPatient(
        @Path("id") id:String,
        @Field("name_last") name_last:String,
        @Field("dni") dni:String,
        @Field("phone") phone:String,
        @Field("email") email:String,
        @Field("direction") direction:String,
        @Field("img") img:String,
        @Field("token_not") token_not:String
    ): Call<UpdateResponse>

    @POST("patients")
    suspend fun postPatient(
        @Body patientRequest: PatientRequest
    ): Response<PatientResponse>

}