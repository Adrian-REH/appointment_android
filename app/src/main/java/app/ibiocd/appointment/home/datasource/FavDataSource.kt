package app.ibiocd.appointment.home.datasource

import app.ibiocd.appointment.model.ApiFavs
import app.ibiocd.appointment.model.DeleteResponse
import app.ibiocd.appointment.model.FavsRespons
import retrofit2.Call
import retrofit2.Response
import retrofit2.http.*

interface FavDataSource {
    //FAVORITO
    @GET("favs")
    suspend fun  getAllFav(): ApiFavs
    @FormUrlEncoded
    @POST("favs")
    fun postFavs(
        @Field("patients") name_last:String,
        @Field("medicals") dni:String
    ): Call<FavsRespons>
    @DELETE("favs/{id}")
    suspend fun  deleteFavs(@Path("id") id:String): Response<DeleteResponse>

}