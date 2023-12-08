package com.nopalsoft.simple.rest.repository

import app.ibiocd.appointment.notification.datasource.RetrofitInstance
import app.ibiocd.appointment.model.*
import app.ibiocd.appointment.util.Resource
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flow
import okhttp3.ResponseBody
import retrofit2.Response
import javax.inject.Inject

interface NotificationRepository {
    fun sendNotification(notification: PushNotification) : Flow<Resource<Response<ResponseBody>>>


}

class NotificationRepositoryImp @Inject constructor(
) : NotificationRepository {

    override fun sendNotification(notification: PushNotification): Flow<Resource<Response<ResponseBody>>> {
        return flow {
            emit(Resource.Loading())
            val response = RetrofitInstance.api.postNotification(notification)
            emit(Resource.Success(response))
        }.catch {
            emit(Resource.Error(it.message.toString()))
        }


    }




}
