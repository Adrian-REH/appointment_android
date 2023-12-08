package app.ibiocd.appointment.datasource

import app.ibiocd.appointment.model.ApiDirection
import retrofit2.http.GET
import retrofit2.http.Query

interface RestDireSource {
    @GET("geocode/search/structured?")
    suspend fun  getDirection(
        @Query("api_key") api_key:String,
        @Query("address") address:String,
        @Query("neighbourhood") neighbourhood:String,
        @Query("country") country:String
    ): ApiDirection

}