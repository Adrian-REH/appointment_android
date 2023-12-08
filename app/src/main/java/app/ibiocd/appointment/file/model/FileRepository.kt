package app.ibiocd.appointment.file.model

import androidx.lifecycle.LiveData
import app.ibiocd.appointment.model.Files
import app.ibiocd.appointment.util.ApiResource
import kotlinx.coroutines.flow.Flow

interface FileRepository {
    //FILES
    fun getAllFile(): LiveData<List<Files>>
    suspend fun deleteFile(toDelete: Files)
    suspend fun getPatientFiles(patient: String) : Flow<ApiResource<String>>
    suspend fun getMedicalFiles(medical:String): Flow<ApiResource<String>>
    suspend fun postFile(files: Files): String
    suspend fun putFile(files: Files)

    suspend fun deleteAllFiles()
    suspend fun findAllFiles(): Flow<ApiResource<String>>


}