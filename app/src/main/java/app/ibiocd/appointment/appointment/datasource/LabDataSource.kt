package app.ibiocd.appointment.appointment.datasource

import app.ibiocd.appointment.model.ApiLaboratory
import retrofit2.http.GET

interface LabDataSource {

    //LABORATORY
    @GET("laboratory")
    suspend fun  getLab(): ApiLaboratory

}