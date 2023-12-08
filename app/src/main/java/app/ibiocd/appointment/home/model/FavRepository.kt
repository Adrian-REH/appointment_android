package com.nopalsoft.simple.rest.repository

import android.util.Log
import androidx.lifecycle.LiveData
import app.ibiocd.appointment.home.datasource.FavDataSource
import app.ibiocd.appointment.model.*
import app.ibiocd.appointment.profile.datasource.entity.Medical
import app.ibiocd.appointment.util.ApiResource
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flow
import retrofit2.Call
import retrofit2.Callback
import retrofit2.HttpException
import javax.inject.Inject

interface FavRepository {


    //FAVS
    fun getAllFavDao(): LiveData<List<Favs>>
    suspend fun getAllFav(): Flow<ApiResource<String>>

    suspend fun postFavs(favs:Favs)
    suspend fun deleteFavs(toDelete:Favs)
    suspend fun deleteAllFavs()




}

class FavRepositoryImp @Inject constructor(
    private val dataSource: FavDataSource,
    private val favsDao: FavsDao,
)
    : FavRepository {

    //FAVS
    override suspend fun deleteAllFavs() {
        favsDao.deleteAllFavs()
        Log.d("deleteAllFiles","Successful")
    }

    override fun getAllFavDao() = favsDao.getAllFavs()
    override suspend fun getAllFav(): Flow<ApiResource<String>> {

            return flow {
                emit(ApiResource.Loading())
                val listMedical= ArrayList<Favs>()
                val dataResponse:List<Favs> =dataSource.getAllFav().result

                for (i in dataResponse.indices) {
                    listMedical.add(dataResponse[i])
                    favsDao.insert(dataResponse[i])
                }
                emit(ApiResource.Success("Success ${listMedical.size}"))

            }.catch { emit(ApiResource.Error(it.message.toString())) }



    }

    override suspend fun postFavs(favs:Favs) {
        //DESCARGO LOS DATOS


        val call = dataSource.postFavs(favs.patient,favs.medical)
        call.enqueue(object : Callback<FavsRespons> {
            override fun onFailure(call: Call<FavsRespons>, t: Throwable) {
            }
            override fun onResponse(call: Call<FavsRespons>, response: retrofit2.Response<FavsRespons>) {

            }

        })

        favsDao.insert(favs)

    }
    override suspend fun deleteFavs(toDelete:Favs) {
        val call=dataSource.deleteFavs(toDelete._id)

        favsDao.delete(toDelete)
    }




}
