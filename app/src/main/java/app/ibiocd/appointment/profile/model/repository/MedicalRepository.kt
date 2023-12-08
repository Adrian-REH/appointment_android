package app.ibiocd.appointment.profile.model.repository

import android.util.Log
import androidx.lifecycle.LiveData
import app.ibiocd.appointment.profile.datasource.retrofit.MedicalDataSource
import app.ibiocd.appointment.datasource.RestDireSource
import app.ibiocd.appointment.model.*
import app.ibiocd.appointment.profile.datasource.dao.MedicalDao
import app.ibiocd.appointment.profile.datasource.entity.Medical
import app.ibiocd.appointment.profile.model.MedicalRequest
import app.ibiocd.appointment.util.ApiResource
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flow
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.io.IOException
import javax.inject.Inject

interface MedicalRepository {


    //MEDICAL
    suspend fun postMedical(medialRequest: MedicalRequest): Flow<ApiResource<String>>
    suspend fun getMedical(): Flow<ApiResource<String>>
    fun getAllMedical(): LiveData<List<Medical>>
    suspend fun getByUsername(username:String): Flow<ApiResource<Medical>>
    suspend fun deleteAllMedical()
    suspend fun putMedical(medical: Medical): Flow<ApiResource<String>>


    suspend fun getDirectionLatLng(address:String,neighbourhood:String,country:String): List<Double>
     fun getMedicalById(id: String): LiveData<Medical>
}

class MedicalRepositoryImp @Inject constructor(
    private val dataSource: MedicalDataSource,
    private val medicalDao: MedicalDao

) : MedicalRepository {
    override suspend fun getDirectionLatLng(address:String,neighbourhood:String,country:String): List<Double> {
        val ID= Retrofit.Builder()
            .baseUrl("https://api.openrouteservice.org/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(RestDireSource::class.java)
            .getDirection(
                "",
                address,
                neighbourhood,
                country
            )
        //DESCARGO LOS DATOS

        Log.d("Successful",ID.toString())
        return ID.bbox
    }
    //MEDICAL

    override fun getMedicalById(id: String) = medicalDao.getByIdMedical(id)


    override fun getAllMedical() = medicalDao.getAllMedical()
    override suspend fun deleteAllMedical() {
        medicalDao.deleteAllMedical()
        Log.d("deleteAllMedical","Successful")
    }
    override suspend fun putMedical(medical: Medical): Flow<ApiResource<String>> {
        return flow<ApiResource<String>> {
            emit(ApiResource.Loading())
            val call = dataSource.putMedical(medical._id,medical.name_last,medical.dni,medical.phone,medical.profession,medical.email,medical.direction,medical.tuition,medical.img,medical.token_not,medical.hour_on)
            call.enqueue(object : Callback<UpdateResponse> {
                override fun onFailure(call: Call<UpdateResponse>, t: Throwable) {

                    Log.d("putMedical -> ERROR",  t.message.toString())

                }
                override fun onResponse(call: Call<UpdateResponse>, response: retrofit2.Response<UpdateResponse>) {
                    suspend {
                        emit(ApiResource.Success(response.body()?.acknowledged.toString()))
                    }

                    Log.d("putMedical -> SUCCESSFULLY", response.body()?.acknowledged.toString())

                }
            })
            medicalDao.insert(medical)


        }.catch {
            emit(ApiResource.Error(it.message.toString()))
        }

    }
    override suspend fun postMedical(medialRequest: MedicalRequest): Flow<ApiResource<String>> {
        delay(3000)
        return flow {

            emit(ApiResource.Loading())
            try{
                val response = dataSource.postMedical(medialRequest)

                emit(ApiResource.Success(response.body()?._id.toString()))



            }catch (e: IOException){
                emit(ApiResource.Error(e.message.toString()))

            }



        }.catch {
            emit(ApiResource.Error(it.message.toString()))
        }
    }
    override suspend fun getByUsername(username:String): Flow<ApiResource<Medical>> {
        return flow {

            emit(ApiResource.Loading())
            val result = dataSource.getMedicalUsername(username).result;
            emit(ApiResource.Success(result))


        }.catch { emit(ApiResource.Error(it.message.toString())) }
    }

    override suspend fun getMedical(): Flow<ApiResource<String>> {
        return flow {
            emit(ApiResource.Loading())
            delay(3000)
            val listMedical= ArrayList<Medical>()
            val dataResponse=dataSource.getMedical().result
            for (i in dataResponse.indices) {
                listMedical.add(dataResponse[i])
                medicalDao.insert(dataResponse[i])
            }
            emit(ApiResource.Success("Success ${listMedical.size}"))

        }.catch { emit(ApiResource.Error(it.message.toString())) }


    }


}
