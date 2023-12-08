package app.ibiocd.appointment.file.model

import android.util.Log
import app.ibiocd.appointment.file.datasource.FileDataSource
import app.ibiocd.appointment.model.*
import app.ibiocd.appointment.util.ApiResource
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flow
import retrofit2.Call
import retrofit2.Callback
import retrofit2.HttpException
import javax.inject.Inject

class FileRepositoryImpl @Inject constructor(
    private val dataSource: FileDataSource,
    private val fileDao: FileDao,
)
    : FileRepository {
    //FILES
    override suspend fun getPatientFiles(patient: String): Flow<ApiResource<String>> {
        return flow<ApiResource<String>> {
            delay(3000)
            val listFiles = ArrayList<Files>()

            val result = dataSource.getPatientListfile(patient).result;
            for (i in 0 until result.size) {

                listFiles.add(result[i])
                fileDao.insertfile(result[i])
            }
            emit(ApiResource.Success("Carga exitosa archivos: ${listFiles.size}"))

        }.catch {
            emit(ApiResource.Error(it.message.toString()))
        }


    }

    override suspend fun getMedicalFiles(medical:String): Flow<ApiResource<String>> {
        return flow<ApiResource<String>> {
            val listFiles= ArrayList<Files>()

            val medlistfile = dataSource.getMedicalListfile(medical).result
            for (i in 0 until medlistfile.size) {
                listFiles.add(medlistfile[i])
                fileDao.insertfile(medlistfile[i])
            }
            emit(ApiResource.Success("Carga exitosa archivos: ${listFiles.size}"))

        }.catch {
            emit(ApiResource.Error(it.message.toString()))

        }



    }
    override suspend fun putFile(files: Files) {
        try{
        delay(3000)
        //DESCARGO LOS DATOS
        val call = dataSource.putFile(files._id,files.laboratory,files.prescriptions,files.stadies,files.odontogram,files.form,files.patient,files.medical)
        call.enqueue(object : Callback<UpdateResponse> {
            override fun onFailure(call: Call<UpdateResponse>, t: Throwable) {
                Log.d("putFile -> ERROR",  t.message.toString())
            }
            override fun onResponse(call: Call<UpdateResponse>, response: retrofit2.Response<UpdateResponse>) {

                Log.d("putFile -> SUCCESSFULLY", response.body()?.acknowledged.toString())

            }
        })
    }catch (e: HttpException){
        Log.e("putFile",e.toString())
    }
    }
    override suspend fun postFile(files: Files): String {
        delay(3000)
        //DESCARGO LOS DATOS
        var  ID= ""
        val call = dataSource.postFiles(files.laboratory,files.prescriptions,files.stadies,files.odontogram,files.form,files.patient,files.medical)
        call.enqueue(object : Callback<AppointmentRespons> {
            override fun onFailure(call: Call<AppointmentRespons>, t: Throwable) {
            }
            override fun onResponse(call: Call<AppointmentRespons>, response: retrofit2.Response<AppointmentRespons>) {
                ID = response.body()?._id ?: ""
                Log.d("postFile",ID)
            }

        })

        delay(3000)
        Log.d("postFile",ID)

        return ID
    }
    override suspend fun deleteFile(toDelete: Files) = fileDao.deleteFile(toDelete)
    override suspend fun deleteAllFiles() {
        fileDao.deleteAllFiles()
        Log.d("deleteAllFiles","Successful")
    }

    override suspend fun findAllFiles(): Flow<ApiResource<String>> {
        return flow<ApiResource<String>> {
            val listFiles= ArrayList<Files>()

            val medlistfile = dataSource.getAllFiles()
            for (i in medlistfile.indices) {
                listFiles.add(medlistfile[i])
                fileDao.insertfile(medlistfile[i])
            }
            emit(ApiResource.Success("Carga exitosa archivos: ${listFiles.size}"))

        }.catch {
            emit(ApiResource.Error(it.message.toString()))

        }
    }

    override fun getAllFile() = fileDao.getAllFile()
}