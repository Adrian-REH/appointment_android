package app.ibiocd.appointment.profile.model.repository

import android.util.Log
import androidx.lifecycle.LiveData
import app.ibiocd.appointment.model.*
import app.ibiocd.appointment.profile.datasource.retrofit.PatientDataSource
import app.ibiocd.appointment.profile.model.PatientRequest
import app.ibiocd.appointment.util.ApiResource
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flow
import retrofit2.Call
import retrofit2.Callback
import javax.inject.Inject

interface PatientRepository {


    //PATIENT
    suspend fun putPatient(patient: Patient): Flow<ApiResource<String>>
    suspend fun getByUsername(username:String): Flow<ApiResource<Patient>>
    suspend fun postPatient(patientRequest: PatientRequest): Flow<ApiResource<String>>
    suspend fun getAllPatient(): Flow<ApiResource<ArrayList<Patient>>>
    fun getAllPatientDao(): LiveData<List<Patient>>
    suspend fun deletePatient(toDelete: Patient)
    suspend fun deleteAllPatient()



}


class PatientRepositoryImp @Inject constructor(
    private val dataSource: PatientDataSource,
    private val patientDao: PatientDao

) : PatientRepository {

    //PATIENT
    override fun getAllPatientDao() = patientDao.getAllPatient()
    override suspend fun putPatient(patient:Patient): Flow<ApiResource<String>> {
        return flow<ApiResource<String>> {
            emit(ApiResource.Loading())
            val call = dataSource.putPatient(patient._id,patient.name_last,patient.dni,patient.phone,patient.email,patient.direction,patient.img,patient.token_not)
            call.enqueue(object : Callback<UpdateResponse> {
                override fun onFailure(call: Call<UpdateResponse>, t: Throwable) {
                    suspend {
                        emit(ApiResource.Error(t.message.toString()))
                    }

                }
                override fun onResponse(call: Call<UpdateResponse>, response: retrofit2.Response<UpdateResponse>) {
                    Log.d("puPatient -> SUCCESSFULLY", response.body()?.acknowledged.toString())
                }
            })
            patientDao.insert(patient)
            emit(ApiResource.Success(""))


        }.catch { emit(ApiResource.Error(it.message.toString())) }
    }
    override suspend fun postPatient(patientRequest: PatientRequest): Flow<ApiResource<String>> {
        return flow {
            emit(ApiResource.Loading())
            val response = dataSource.postPatient(patientRequest)

            emit(ApiResource.Success(response.body()?.id.toString()))

        }.catch {
            emit(ApiResource.Error(it.message.toString()))
        }
    }
    override suspend fun getByUsername(username:String): Flow<ApiResource<Patient>> {
            return flow {
                emit(ApiResource.Loading())

                val result =  dataSource.getPatientUsername(username).result
                emit(ApiResource.Success(result))
            }.catch {
                emit(ApiResource.Error(it.message.toString()))

            }
    }
    override suspend fun getAllPatient(): Flow<ApiResource<ArrayList<Patient>>> {
        return flow {
            emit(ApiResource.Loading())

            val listPatient= ArrayList<Patient>()
            val result =dataSource.getAllPatient().result
            for (i in result.indices) {
                listPatient.add(result[i])
                patientDao.insert(result[i])
            }

            Log.d("getPatients","${listPatient.size}")
            emit(ApiResource.Success(listPatient))

        }.catch {
            emit(ApiResource.Error(it.message.toString()))

        }


    }
    override suspend fun deletePatient(toDelete: Patient) = patientDao.delete(toDelete)
    override suspend fun deleteAllPatient() {
        patientDao.deleteAllPatient()
        Log.d("deleteAllPatient","Successful")
    }



}
