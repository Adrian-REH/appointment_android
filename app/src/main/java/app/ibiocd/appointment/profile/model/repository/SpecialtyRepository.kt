package app.ibiocd.appointment.profile.model.repository

import android.util.Log
import androidx.lifecycle.LiveData
import app.ibiocd.appointment.profile.datasource.retrofit.SpecialtyDataSource
import app.ibiocd.appointment.model.*
import kotlinx.coroutines.delay
import retrofit2.Call
import retrofit2.Callback
import javax.inject.Inject

interface SpecialtyRepository {
    //SPECIALTY
    fun getAllSpecialty(): LiveData<List<Specialty>>
    suspend fun getSpecialty(): ArrayList<Specialty>
    suspend fun postSpecialty(specialty:Specialty)
    suspend fun putSpecialty(specialty: Specialty)
    suspend fun deleteSpecialty(specialty: Specialty)
}

class SpecialtyRepositoryImp @Inject constructor(
    private val dataSource: SpecialtyDataSource,
    private val specialtyDao: SpecialtyDao,


    ) : SpecialtyRepository {


    //SPECIALTY
    override suspend fun deleteSpecialty(specialty: Specialty) {
        val call=dataSource.deleteSpecialty(specialty._id)

        specialtyDao.delete(specialty)
    }
    override suspend fun getSpecialty(): ArrayList<Specialty> {
        delay(3000)
        val listSpecialty= ArrayList<Specialty>()
        for (i in 0 until dataSource.getSpecialtyList().result.size) {
            val _id =dataSource.getSpecialtyList().result[i]._id
            val title =dataSource.getSpecialtyList().result[i].title
            val comment =dataSource.getSpecialtyList().result[i].comment
            val medical =dataSource.getSpecialtyList().result[i].medical
            val offer =dataSource.getSpecialtyList().result[i].offer
            val price =dataSource.getSpecialtyList().result[i].price
            // ADD DATE
            val  register= Specialty(_id,title,comment,medical,offer,price)
            listSpecialty.add(register)
            specialtyDao.insert(register)
        }
        Log.d("getSpecialty","${listSpecialty.size}")

        return listSpecialty
    }
    override fun getAllSpecialty() = specialtyDao.getAllSpecialty()
    override suspend fun postSpecialty(specialty: Specialty) {
        delay(3000)
        val call = dataSource.postSpecialty(specialty.title,specialty.comment,specialty.medical,specialty.offer,specialty.price)
        call.enqueue(object : Callback<SpecialtyRespons> {
            override fun onFailure(call: Call<SpecialtyRespons>, t: Throwable) {
            }
            override fun onResponse(call: Call<SpecialtyRespons>, response: retrofit2.Response<SpecialtyRespons>) {
                //ID = response.body()?._id ?: ""
                Log.d("postSpecialty","Successful")

            }
        })


    }
    override suspend fun putSpecialty(specialty: Specialty) {
        val call = dataSource.putSpecialty(specialty._id,specialty.title,specialty.comment,specialty.medical,specialty.offer,specialty.price)
        call.enqueue(object : Callback<SpecialtyRespons> {
            override fun onFailure(call: Call<SpecialtyRespons>, t: Throwable) {
            }
            override fun onResponse(call: Call<SpecialtyRespons>, response: retrofit2.Response<SpecialtyRespons>) {
                Log.d("putSpecialty","Successful")

            }
        })
        delay(3000)

        specialtyDao.insert(specialty)

    }


}
