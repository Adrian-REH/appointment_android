package com.nopalsoft.simple.rest.repository

import android.app.Activity
import android.util.Log
import androidx.lifecycle.LiveData
import app.ibiocd.appointment.appointment.datasource.LabDataSource
import app.ibiocd.appointment.home.datasource.SedeDataSource
import app.ibiocd.appointment.model.*
import app.ibiocd.lavanderia.VolleyFileUploadRequest
import com.android.volley.toolbox.Volley
import kotlinx.coroutines.delay
import org.json.JSONObject
import javax.inject.Inject

interface UserRepository {



    //IMAGEN
    suspend fun uploadImage (context: Activity,IMAGENAME:String,imageData:ByteArray): String



}

class UserRepositoryImp @Inject constructor(
    private val dataSource: SedeDataSource,
    private val labSource: LabDataSource,
    private val labsDao: LabsDao,
    private val sedeDao: SedeDao
)
    : UserRepository {









    //IMAGEN
    override suspend fun uploadImage (context: Activity,IMAGENAME:String,imageData:ByteArray): String {

        var ID= ""
        val url = "https://appointmentibiocd.azurewebsites.net/api/controller"
        val request = object : VolleyFileUploadRequest( Method.POST, url,
            com.android.volley.Response.Listener {
                ID=JSONObject(it.toString()).getString("filename").toString()

                Log.d("Successfully",ID)
            },
            com.android.volley.Response.ErrorListener {

                Log.d("ERROR",it.toString())
            }
        ) {

            override fun getByteData(): MutableMap<String, DataPart> {
                val params = HashMap<String, DataPart>()
                params["myFile"] = DataPart(IMAGENAME.toString(), imageData, "file")

                Log.d("IMAGEDATEs", "$IMAGENAME   $imageData")
                return params
            }



        }
        Volley.newRequestQueue(context).add(request)
        delay(3000)
        Log.d("SuccessfullyID",ID)

        return ID
    }



}
