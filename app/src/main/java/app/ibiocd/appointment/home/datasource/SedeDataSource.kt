package app.ibiocd.appointment.home.datasource

import app.ibiocd.appointment.model.ApiLaboratory
import app.ibiocd.appointment.model.ApiSede
import retrofit2.http.GET

interface SedeDataSource {

    //SEDE
    @GET("sede")
    suspend fun  getSede(): ApiSede

}