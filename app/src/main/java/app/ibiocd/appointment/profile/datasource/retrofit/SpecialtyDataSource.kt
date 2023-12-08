package app.ibiocd.appointment.profile.datasource.retrofit

import app.ibiocd.appointment.model.*
import retrofit2.Call
import retrofit2.Response
import retrofit2.http.*

interface SpecialtyDataSource {
    //SPECIALTY
    @GET("specialty")
    suspend fun  getSpecialtyList(): ApiSpecialty
    @FormUrlEncoded
    @PUT("specialty/{id}")
    fun putSpecialty(
        @Path("id") id:String,
        @Field("title") title:String,
        @Field("comment") comment:String,
        @Field("medicals") medical:String,
        @Field("offer") offer:String,
        @Field("price") price:String
    ): Call<SpecialtyRespons>

    @FormUrlEncoded
    @POST("specialty")
    fun postSpecialty(
        @Field("title") title:String,
        @Field("comment") comment:String,
        @Field("medicals") medical:String,
        @Field("offer") offer:String,
        @Field("price") price:String
    ): Call<SpecialtyRespons>
    @DELETE("specialty/{id}")
    suspend fun  deleteSpecialty(@Path("id") id:String): Response<DeleteResponse>


}