package com.nopalsoft.simple.rest.repository

import androidx.lifecycle.LiveData
import app.ibiocd.appointment.home.datasource.SedeDataSource
import app.ibiocd.appointment.model.*
import kotlinx.coroutines.delay
import javax.inject.Inject

interface SedeRepository {
    //SEDES
    fun getAllSedes(): LiveData<List<Sedes>>
    suspend fun getSede()
}

class SedeRepositoryImp @Inject constructor(
    private val dataSource: SedeDataSource,
    private val sedeDao: SedeDao
)
    : SedeRepository {


    //SEDES
    override fun getAllSedes() = sedeDao.getAllSedes()
    override suspend fun getSede() {
        delay(3000)
        val listSedes= ArrayList<Sedes>()
        val dataResponse=dataSource.getSede().result
        for (i in 0 until dataResponse.size) {
            listSedes.add(dataResponse[i])
            sedeDao.insert(dataResponse[i])
        }

    }


}
