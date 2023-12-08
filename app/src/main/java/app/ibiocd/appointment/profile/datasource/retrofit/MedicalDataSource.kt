package app.ibiocd.appointment.profile.datasource.retrofit

import app.ibiocd.appointment.model.ApiMedicalListResponse
import app.ibiocd.appointment.model.ApiMedicalResponse
import app.ibiocd.appointment.model.MedicalResponse
import app.ibiocd.appointment.model.UpdateResponse
import app.ibiocd.appointment.profile.model.MedicalRequest
import retrofit2.Call
import retrofit2.Response
import retrofit2.http.*

interface MedicalDataSource {
    //MEDICAL
    @GET("medicals/username/{username}")
    suspend fun  getMedicalUsername(@Path("username") username:String): ApiMedicalResponse
    @GET("medicals")
    suspend fun  getMedical(): ApiMedicalListResponse
    @FormUrlEncoded
    @PUT("medicals/{id}")
    fun putMedical(
        @Path("id") id:String,
        @Field("name_last") name_last:String,
        @Field("dni") dni:String,
        @Field("phone") phone:String,
        @Field("profession") profession:String,
        @Field("email") email:String,
        @Field("direction") direction:String,
        @Field("tuition") tuition:String,
        @Field("img") img:String,
        @Field("token_not") token_not:String,
        @Field("hour_on") hour_on:String
    ): Call<UpdateResponse>

    @POST("medicals")
    suspend fun postMedical(
        @Body medicalRequest: MedicalRequest
    ): Response<MedicalResponse>

}