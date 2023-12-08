package app.ibiocd.appointment.appointment.datasource

import app.ibiocd.appointment.model.*
import retrofit2.Call
import retrofit2.Response
import retrofit2.http.*

interface OdontogramDataSource {
    //ODONTOGRAMS
    @GET("odontogram/{id}")
    suspend fun  getOdontogram(@Path("id") id:String): ApiOdontogram

    @POST("odontogram")
    fun  postOdontogram(@Body body:Data): Call<OdontogramResponse>

    class Data(
        var data:List<Tooths>,
        val patient:String,
        val medical:String)

    @PUT("odontogram/{id}")
    fun  putOdontogram(@Path("id") id:String, @Body body:Data
    ): Call<UpdateResponse>
    @DELETE("odontogram/{id}")
    suspend fun  deleteOdontogram(@Path("id") id:String): Response<DeleteResponse>


}