package app.ibiocd.appointment.profile.model.repository

import android.util.Log
import app.ibiocd.appointment.profile.datasource.retrofit.DateDataSource
import app.ibiocd.appointment.model.Date
import app.ibiocd.appointment.model.DateDao
import app.ibiocd.appointment.model.DateResponse
import app.ibiocd.appointment.model.UpdateResponse
import kotlinx.coroutines.delay
import retrofit2.Call
import retrofit2.Callback
import javax.inject.Inject

class DateRepositoryImpl @Inject constructor(
    private val dataSource: DateDataSource,
    private val dateDao: DateDao,
    ): DateRepository {
    //DATE
    override suspend fun deleteDate(toDelete: Date) {
        val call=dataSource.deleteDate(toDelete._id)

        dateDao.delete(toDelete)
    }
    override suspend fun getDate(): ArrayList<Date> {
        delay(3000)
        val listDates= ArrayList<Date>()
        for (i in 0 until dataSource.getDate().result.size) {
            val _id =dataSource.getDate().result[i]._id
            val medical =dataSource.getDate().result[i].medical
            val lunes =dataSource.getDate().result[i].lunes
            val martes =dataSource.getDate().result[i].martes
            val miercoles =dataSource.getDate().result[i].miercoles
            val jueves =dataSource.getDate().result[i].jueves
            val viernes =dataSource.getDate().result[i].viernes
            val sabado =dataSource.getDate().result[i].sabado
            val domingo =dataSource.getDate().result[i].domingo
            // ADD DATE
            val  dates= Date(_id,medical,lunes,martes,miercoles,jueves,viernes,sabado,domingo)
            listDates.add(dates)
            dateDao.insert(dates)
        }
        Log.d("getDate","${listDates.size}")

        return listDates
    }
    override fun getAllDate() = dateDao.getAllDate()
    override suspend fun putDate(date: Date) {
        delay(3000)
        //DESCARGO LOS DATOS


        val call = dataSource.putDate(date._id,date.medical,date.lunes,date.martes,date.miercoles,date.jueves,date.viernes,date.sabado,date.domingo)
        call.enqueue(object : Callback<UpdateResponse> {
            override fun onFailure(call: Call<UpdateResponse>, t: Throwable) {

                Log.d("putDate -> ERROR",  t.message.toString())

            }
            override fun onResponse(call: Call<UpdateResponse>, response: retrofit2.Response<UpdateResponse>) {

                Log.d("putDate -> SUCCESSFULLY", response.body()?.acknowledged.toString())

            }
        })
        dateDao.insert(date)

    }
    override suspend fun postDate(date: Date) {
        delay(3000)
        //DESCARGO LOS DATOS

        val call = dataSource.postDate(date.medical,date.lunes,date.martes,date.miercoles,date.jueves,date.viernes,date.sabado,date.domingo)
        call.enqueue(object : Callback<DateResponse> {
            override fun onFailure(call: Call<DateResponse>, t: Throwable) {
            }
            override fun onResponse(call: Call<DateResponse>, response: retrofit2.Response<DateResponse>) {
                //ID = response.body()?._id ?: ""
                Log.d("postMedical","Successful")

            }
        })

    }


}