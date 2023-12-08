package app.ibiocd.appointment.profile.datasource.retrofit

import app.ibiocd.appointment.model.ApiDate
import app.ibiocd.appointment.model.DateResponse
import app.ibiocd.appointment.model.DeleteResponse
import app.ibiocd.appointment.model.UpdateResponse
import retrofit2.Call
import retrofit2.Response
import retrofit2.http.*

interface DateDataSource {

    //DATE
    @GET("date")
    suspend fun  getDate(): ApiDate

    @DELETE("date/{id}")
    suspend fun  deleteDate(@Path("id") id:String): Response<DeleteResponse>

    @FormUrlEncoded
    @PUT("date/{id}")
    fun putDate(
        @Path("id") id:String,
        @Field("medicals") name_last:String,
        @Field("lunes") dni:String,
        @Field("martes") phone:String,
        @Field("miercoles") profession:String,
        @Field("jueves") email:String,
        @Field("viernes") direction:String,
        @Field("sabado") tuition:String,
        @Field("domingo") img:String
    ): Call<UpdateResponse>

    @FormUrlEncoded
    @POST("date")
    fun postDate(
        @Field("medicals") medical:String,
        @Field("lunes") lunes:String,
        @Field("martes") martes:String,
        @Field("miercoles") miercoles:String,
        @Field("jueves") jueves:String,
        @Field("viernes") viernes:String,
        @Field("sabado") sabado:String,
        @Field("domingo") domingo:String
    ): Call<DateResponse>


}