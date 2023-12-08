package app.ibiocd.appointment.file.datasource

import app.ibiocd.appointment.model.*
import retrofit2.Call
import retrofit2.http.*

interface FileDataSource {
    //FILE
    @GET("files")
    suspend fun  getAllFiles(): List<Files>
    @GET("files/patients/{patients}")
    suspend fun  getPatientListfile(@Path("patients") patient:String): ApiAptFileListResponse
    @GET("files/medicals/{medicals}")
    suspend fun  getMedicalListfile(@Path("medicals") medical:String): ApiAptFileListResponse

    @GET("files/patient/{idPatient}/medical/{idMedical}")
    suspend fun  getFilePatientAndMedical(@Path("idPatient") patient:String, @Path("idMedical") medical:String): Files

    @FormUrlEncoded
    @POST("files")
    fun postFiles(
        @Field("laboratory") laboratory:String,
        @Field("prescriptions") prescriptions:String,
        @Field("stadies") stadies:String,
        @Field("odontogram") odontogram:String,
        @Field("form") form:String,
        @Field("patients") patient:String,
        @Field("medicals") medical:String
    ): Call<AppointmentRespons>
    @FormUrlEncoded
    @PUT("files/{id}")
    fun putFile(
        @Path("id") id:String,
        @Field("laboratory") laboratory:String,
        @Field("prescriptions") prescriptions:String,
        @Field("stadies") stadies:String,
        @Field("odontogram") odontogram:String,
        @Field("form") form:String,
        @Field("patients") patient:String,
        @Field("medicals") medical:String
    ): Call<UpdateResponse>

}