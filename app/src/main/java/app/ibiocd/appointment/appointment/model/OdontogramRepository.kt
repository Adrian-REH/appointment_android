package com.nopalsoft.simple.rest.repository

import android.util.Log
import androidx.lifecycle.LiveData
import app.ibiocd.appointment.appointment.datasource.OdontogramDataSource
import app.ibiocd.appointment.model.*
import app.ibiocd.appointment.util.ApiResource
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flow
import retrofit2.Call
import retrofit2.Callback
import retrofit2.await
import javax.inject.Inject

interface OdontogramRepository {


    //PATIENT
    suspend fun putOdontogram(id:String,data:ApiTooth,patient:String,medical:String): String
    suspend fun postOdontogram(data:ApiTooth,patient:String,medical:String): Flow<ApiResource<OdontogramResponse>>
    suspend fun deleteOdontogram(toDelete: Odontogram)
    suspend fun deleteAllOdontogram()
    suspend fun getOdontogram(odontogramID:String)
    fun getAllOdontogram(): LiveData<List<Odontogram>>
    fun getAllTooth(): LiveData<List<Tooth>>


}

class OdontogramRepositoryImp @Inject constructor(
    private val dataSource: OdontogramDataSource,
    private val odontogramDao: OdontogramDao

) : OdontogramRepository {

    override fun getAllOdontogram() = odontogramDao.getAllOdontogram()
    override fun getAllTooth() = odontogramDao.getAllTooth()
    override suspend fun putOdontogram(id:String,data:ApiTooth,patient:String,medical:String): String {
        delay(3000)
        //DESCARGO LOS DATOS
        var  ID= ""

        val call = dataSource.putOdontogram(id,OdontogramDataSource.Data(data.data,patient,medical))
        call.enqueue(object : Callback<UpdateResponse> {
            override fun onFailure(call: Call<UpdateResponse>, t: Throwable) {

                Log.d("puPatient -> ERROR", t.message.toString())

            }
            override fun onResponse(call: Call<UpdateResponse>, response: retrofit2.Response<UpdateResponse>) {

                Log.d("puPatient -> SUCCESSFULLY", response.body()?.acknowledged.toString())

            }
        })

        return ID
    }
    override suspend fun postOdontogram(data:ApiTooth,patient:String,medical:String): Flow<ApiResource<OdontogramResponse>> {
        return flow<ApiResource<OdontogramResponse>> {

            emit(ApiResource.Loading())
            val call = dataSource.postOdontogram(OdontogramDataSource.Data(data.data,patient,medical)).await()
            emit(ApiResource.Success(call))

        }.catch {
            emit(ApiResource.Error(it.message.toString()))

        }

    }
    override suspend fun getOdontogram(odontogramID:String){
        delay(3000)
        val listOdontogram= ArrayList<Odontogram>()
        val listTooth= ArrayList<Tooth>()
        val Odontogram = dataSource.getOdontogram(odontogramID).result
        for (i in 0 until Odontogram.size) {
            val Tooth=Odontogram[i].data
            for (j in 0 until Tooth.size) {
                //odontogramDao.insert(Tooth[i])
                //listTooth.add(Tooth[i])
            }
            odontogramDao.insert(Odontogram(Odontogram[i]._id,"", Odontogram[i].patient, Odontogram[i].medical ))
            listOdontogram.add(Odontogram(Odontogram[i]._id,"", Odontogram[i].patient, Odontogram[i].medical ))
        }
        Log.d("getOdontogram","${listOdontogram.size} & ${listTooth.size}")

    }
    override suspend fun deleteOdontogram(toDelete: Odontogram) = odontogramDao.delete(toDelete)
    override suspend fun deleteAllOdontogram() {
        odontogramDao.deleteAllOdontogram()
        odontogramDao.deleteAllTooth()
        Log.d("deleteAllOdontogram","Successful")
    }

}
