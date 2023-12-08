package app.ibiocd.appointment.profile.view

import android.annotation.SuppressLint
import android.app.Activity
import android.content.ContentValues
import android.database.Cursor
import android.net.Uri
import android.os.Bundle
import android.provider.OpenableColumns
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.result.PickVisualMediaRequest
import androidx.activity.result.contract.ActivityResultContracts
import androidx.activity.viewModels
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.runtime.*
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import app.ibiocd.appointment.UserViewModel
import app.ibiocd.appointment.profile.navegation.ProfileDestinations
import app.ibiocd.appointment.auth.navegation.SessionDestinationsActivity
import app.ibiocd.appointment.presentation.screens.profile.EditMedical
import app.ibiocd.appointment.ui.theme.AppointmentTheme
import com.nopalsoft.simple.rest.presentation.*
import com.nopalsoft.simple.rest.presentation.screens.profile.reset.*
import dagger.hilt.android.AndroidEntryPoint
import java.io.IOException


@AndroidEntryPoint
class ProfileActivity : ComponentActivity() {
    val pickMedia= registerForActivityResult(ActivityResultContracts.PickVisualMedia()){

        if (it!=null){
            viewModel.insertImag(it.toString())
            Log.i("IMAGE",it.toString())
            createImageData(it)
            dumpImageMetaData(it)
            Log.i("aris","seleccionar")
        }else{
            Log.i("aris","No seleccionado")
        }
    }
    private var imageData: ByteArray? = null
    private var IMAGENAME = ""

    val viewModel: UserViewModel by viewModels()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {

            val imageUri by viewModel.isImageUri.observeAsState("")
            val isImage by viewModel.isImage.observeAsState("")
            val isLoading by viewModel.isLoading.observeAsState(false)




            val navController = rememberNavController()
            var definit= SessionDestinationsActivity.Beginning.route

            var profile=6
            var ID=""
            var medicalb=false
            var editb=false

            if(intent.extras !=null){
                ID=intent.getStringExtra("id").toString()
                Log.d("Profile -> ID",ID)
                medicalb= intent.getStringExtra("medicals").toString().toBoolean()
                editb= intent.getStringExtra("edit").toString().toBoolean()
                if (medicalb){
                    if (editb){
                        profile=0
                    }else{
                        profile=1

                    }
                }else if (intent.getStringExtra("patients").toString().toBoolean()){
                    if (editb){
                        profile=2

                    }else{
                        profile=3

                    }
                }

            }else{
                //ERROR AL ABRIR LA PAGINA PRINCIPAL

            }
            when (profile){
                0 -> definit=ProfileDestinations.EditMedical.route
                1 -> definit=ProfileDestinations.ResumenMedical.route
                2 -> definit=ProfileDestinations.EditPatient.route
                3 -> definit= ProfileDestinations.ResumenPatient.route

            }

            AppointmentTheme {
                val activity = (LocalContext.current as? Activity)

                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colors.background
                ) {
                    NavHost(navController = navController, startDestination = definit) {

                        composable(ProfileDestinations.EditMedical.route,
                            arguments = listOf(navArgument("newText"){ defaultValue = "Check In" }))
                        {
                            EditMedical(ID,
                                resetdni = {
                                navController.navigate( ProfileDestinations.ResetDNI.createRoute()  )

                            }, resetemail = {
                                navController.navigate( ProfileDestinations.ResetEmail.createRoute()  )

                            }, resettuition = {
                                navController.navigate( ProfileDestinations.ResetTuition.createRoute()  )

                            }, resetphone = {
                                navController.navigate( ProfileDestinations.ResetPhone.createRoute()  )

                            }, resetlocation={
                                navController.navigate( ProfileDestinations.ResetLocation.createRoute()  )

                            }, resetname = {
                                navController.navigate( ProfileDestinations.ResetName.createRoute()  )

                            }, resetprofession = {
                                navController.navigate( ProfileDestinations.ResetProfession.createRoute()  )

                            }, Schedules = {
                                navController.navigate( ProfileDestinations.ListSchedules.createRoute()  )

                            }, Specialty = {
                                navController.navigate( ProfileDestinations.ListSpecialty.createRoute()  )

                            }, resetimage = {
                                    navController.navigate( ProfileDestinations.ResetImage.createRoute())

                                })



                        }

                        composable(
                            ProfileDestinations.ResumenMedical.route,
                            arguments = listOf(navArgument("newText"){ defaultValue = "Check In" }))
                        {

                            ResumenMedical(ID, Schedules = {
                                navController.navigate( ProfileDestinations.ListSchedules.createRoute()  )

                            }, Specialty = {
                                navController.navigate( ProfileDestinations.ListSpecialty.createRoute()  )

                            })

                        }
                        composable(
                            ProfileDestinations.EditPatient.route,
                            arguments = listOf(navArgument("newText"){ defaultValue = "Patient" })
                        ) {


                            EditPatient(ID,resetname = {
                                navController.navigate( ProfileDestinations.ResetName.createRoute()  )

                            },resetdni = {
                                navController.navigate( ProfileDestinations.ResetDNI.createRoute()  )

                            }, resetemail = {
                                navController.navigate( ProfileDestinations.ResetEmail.createRoute()  )

                            }, resetphone = {
                                navController.navigate( ProfileDestinations.ResetPhone.createRoute()  )

                            }, resetlocation={
                                navController.navigate( ProfileDestinations.ResetLocation.createRoute()  )

                            }, resetimage = {
                                navController.navigate( ProfileDestinations.ResetImage.createRoute())

                            })
                        }

                        composable(
                            ProfileDestinations.ResetDNI.route,
                            arguments = listOf(navArgument("newText"){ defaultValue = "Dni" })
                        ) {

                            ResetDNI(ID,medicalb, BackMedical={
                                navController.navigate( ProfileDestinations.EditMedical.createRoute()  )

                            }, BackPatient={
                                navController.navigate( ProfileDestinations.EditPatient.createRoute()  )

                            })

                        }
                        composable(
                            ProfileDestinations.ResumenPatient.route,
                            arguments = listOf(navArgument("newText"){ defaultValue = "Dni" })
                        ) {

                            ResumenPatient(ID)

                        }
                        composable(
                            ProfileDestinations.ResetLocation.route,
                            arguments = listOf(navArgument("newText"){ defaultValue = "Location" })
                        ) {
                            ResetLocation(ID,medicalb, BackMedical={
                                navController.navigate( ProfileDestinations.EditMedical.createRoute()  )

                            }, BackPatient={
                                navController.navigate( ProfileDestinations.EditPatient.createRoute()  )

                            })


                        }
                        composable(
                            ProfileDestinations.ResetName.route,
                            arguments = listOf(navArgument("newText"){ defaultValue = "Name" })
                        ) {

                            ResetName(ID,medicalb, BackMedical={
                                navController.navigate( ProfileDestinations.EditMedical.createRoute()  )

                            }, BackPatient={
                                navController.navigate( ProfileDestinations.EditPatient.createRoute()  )

                            })

                        }
                        composable(
                            ProfileDestinations.ResetPhone.route,
                            arguments = listOf(navArgument("newText"){ defaultValue = "Phone" })
                        ) {
                            ResetPhone(ID,medicalb, BackMedical={
                                navController.navigate( ProfileDestinations.EditMedical.createRoute()  )

                            }, BackPatient={
                                navController.navigate( ProfileDestinations.EditPatient.createRoute()  )

                            })


                        }
                        composable(
                            ProfileDestinations.ResetTuition.route,
                            arguments = listOf(navArgument("newText"){ defaultValue = "Tuition" })
                        ) {

                            ResetTuition(ID, BackMedical={
                                navController.navigate( ProfileDestinations.EditMedical.createRoute()  )

                            })

                        }
                        composable(
                            ProfileDestinations.ResetImage.route,
                            arguments = listOf(navArgument("newText"){ defaultValue = "Image" })
                        ) {

                            ResetImage(ID,medicalb,isLoading,imageUri,isImage,onOpen={
                                pickMedia.launch(PickVisualMediaRequest(ActivityResultContracts.PickVisualMedia.ImageOnly))
                            },onAddImage={
                                viewModel.uploadImage(activity,IMAGENAME,imageData!!)

                            }, BackMedical={
                                navController.navigate( ProfileDestinations.EditMedical.createRoute()  )

                            }, BackPatient={
                                navController.navigate( ProfileDestinations.EditPatient.createRoute()  )

                            })


                        }
                        composable(
                            ProfileDestinations.ResetEmail.route,
                            arguments = listOf(navArgument("newText"){ defaultValue = "Email" })
                        ) {

                            ResetEmail(ID,medicalb, BackMedical={
                                navController.navigate( ProfileDestinations.EditMedical.createRoute()  )

                            }, BackPatient={
                                navController.navigate( ProfileDestinations.EditPatient.createRoute()  )

                            })

                        }
                        composable(
                            ProfileDestinations.ResetProfession.route,
                            arguments = listOf(navArgument("newText"){ defaultValue = "Profession" })
                        ) {

                            ResetProfession(ID, BackMedical={
                                navController.navigate( ProfileDestinations.EditMedical.createRoute()  )

                            })

                        }
                        composable(
                            ProfileDestinations.ListSchedules.route,
                            arguments = listOf(navArgument("newText"){ defaultValue = "Schedules" })
                        ) {

                            ListSchedules(editb,ID)

                        }
                        composable(
                            ProfileDestinations.ListSpecialty.route,
                            arguments = listOf(navArgument("newText"){ defaultValue = "Specialty" })
                        ) {

                            ListSpecialty(editb,ID)

                        }
                    }


                }
            }
        }
    }


    @Throws(IOException::class)
    private fun createImageData(uri: Uri) {
        val inputStream = contentResolver.openInputStream(uri)
        inputStream?.buffered()?.use {
            imageData = it.readBytes()

        }

    }
    @SuppressLint("Range")
    fun dumpImageMetaData(uri: Uri) {
        val cursor: Cursor? = contentResolver.query(
            uri, null, null, null, null, null)
        cursor?.use {
            if (it.moveToFirst()) {
                val displayName: String =
                    it.getString(it.getColumnIndex(OpenableColumns.DISPLAY_NAME))
                Log.i(ContentValues.TAG, "Display Name: $displayName")
                IMAGENAME=displayName

            }
        }
    }



}

