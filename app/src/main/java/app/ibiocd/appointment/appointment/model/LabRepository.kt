package com.nopalsoft.simple.rest.repository

import android.util.Log
import androidx.lifecycle.LiveData
import app.ibiocd.appointment.appointment.datasource.LabDataSource
import app.ibiocd.appointment.model.*
import kotlinx.coroutines.delay
import javax.inject.Inject

interface LabRepository {
    //LABS
    suspend fun getLabs()
    fun getAllLabs(): LiveData<List<Labs>>
}

class LabRepositoryImp @Inject constructor(
    private val labSource: LabDataSource,
    private val labsDao: LabsDao,
)
    : LabRepository {

    //LABS
    override fun getAllLabs() = labsDao.getAllLabs()
    override suspend fun getLabs() {
        delay(3000)
        val listlab= ArrayList<Labs>()
        val dataResponse=labSource.getLab()

        for (i in 0 until dataResponse.result.size) {

            listlab.add(dataResponse.result[i])
            labsDao.insert(dataResponse.result[i])

        }

        Log.d("getLabs","${listlab.size}")

    }


}
