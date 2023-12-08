package app.ibiocd.appointment.profile.model.repository

import androidx.lifecycle.LiveData
import app.ibiocd.appointment.model.Date

interface DateRepository {
    //DATE
    suspend fun getDate(): ArrayList<Date>
    fun getAllDate(): LiveData<List<Date>>
    suspend fun deleteDate(toDelete: Date)
    suspend fun putDate(date: Date)
    suspend fun postDate(date: Date)

}