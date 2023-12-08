package com.nopalsoft.simple.rest.repository

import android.util.Log
import androidx.lifecycle.LiveData
import app.ibiocd.appointment.appointment.datasource.AppointmentDataSource
import app.ibiocd.appointment.model.*
import app.ibiocd.appointment.util.ApiResource
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flow
import retrofit2.Call
import retrofit2.Callback
import retrofit2.HttpException
import javax.inject.Inject

interface AppointmetRepository {

    //APPOINTMENT
    suspend fun deleteAppointment(toDelete: Appointment)
    fun getAllAppointmentDao(): LiveData<List<Appointment>>
    suspend fun getAllAppointment(): Flow<ApiResource<String>>
    suspend fun deleteAllAppointment()
    suspend fun postAppointment(appointment: Appointment):Flow<ApiResource<AppointmentRespons>>



}

class AppointmetRepositoryImp @Inject constructor(
    private val dataSource: AppointmentDataSource,
    private val AppointmentDao: AppointmentDao
) : AppointmetRepository {
    //APPOINTMENT
    override suspend fun postAppointment(appointment: Appointment):Flow<ApiResource<AppointmentRespons>> {
        return flow<ApiResource<AppointmentRespons>> {
            emit(ApiResource.Loading())
            val call = dataSource.postAppointment(appointment.fecha,appointment.hora,appointment.coment,appointment.specialty,appointment.patient,appointment.medical,appointment.profession,appointment.files,appointment.price)
            call.enqueue(object : Callback<AppointmentRespons> {
                override fun onFailure(call: Call<AppointmentRespons>, t: Throwable) {
                    suspend {
                        emit(ApiResource.Error(message = t.message.toString()))
                    }
                }

                override fun onResponse(call: Call<AppointmentRespons>, response: retrofit2.Response<AppointmentRespons>) {
                    //
                    suspend {
                        emit(ApiResource.Success(response.body()!!))

                    }

                }
            })


        }.catch {
            emit(ApiResource.Error(it.message.toString()))
        }

    }
    override suspend fun deleteAllAppointment() {
        AppointmentDao.deleteAllAppointment()
    }

    override fun getAllAppointmentDao() = AppointmentDao.getAllAppointment()
    override suspend fun getAllAppointment(): Flow<ApiResource<String>> {
        return flow<ApiResource<String>> {
            val listAppointment = ArrayList<Appointment>()

            val dataResponse = dataSource.getAllAppointments()
            listAppointment.addAll(dataResponse)
            for (element in dataResponse) {
                AppointmentDao.insert(element)
            }

            emit(ApiResource.Success(""))

        }.catch {
            emit(ApiResource.Error(it.message.toString()))
        }
    }

    override suspend fun deleteAppointment(toDelete: Appointment) = AppointmentDao.deleteAppointment(toDelete)


}
