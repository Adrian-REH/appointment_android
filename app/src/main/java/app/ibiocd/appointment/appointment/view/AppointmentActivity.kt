package app.ibiocd.appointment.appointment.view

import android.app.Activity
import android.app.DownloadManager
import android.net.Uri
import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import app.ibiocd.appointment.appointment.navegation.AppointmentDestinations
import app.ibiocd.appointment.ui.theme.AppointmentTheme
import com.nopalsoft.simple.rest.presentation.*
import androidx.compose.ui.platform.LocalContext
import androidx.navigation.NavHostController
import app.ibiocd.appointment.presentation.screens.Appointment.Odontogram
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class AppointmentActivity : ComponentActivity() {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)


        setContent {

            var definit= AppointmentDestinations.HomeSelect.route

            var resume=6
            val navController = rememberNavController()
            var Medical:Boolean=false
            var ID:String=""
            var appointmentID:String=""
            if(intent.extras !=null){
                Medical = intent.getStringExtra("medicals").toString().toBoolean()
                ID = intent.getStringExtra("id").toString()
                appointmentID = intent.getStringExtra("appointment").toString()

                resume = intent.getStringExtra("resume").toString().toInt()

                Log.d("RESUME",resume.toString())
            }

            when (resume){
                0 -> definit= AppointmentDestinations.AppointmentPatient.route
                1 -> definit= AppointmentDestinations.AppointmentMedical.route
                2 -> definit= AppointmentDestinations.AppointmentLab.route

            }
            AppointmentTheme {

                AppointmentNavHost(Medical,ID,appointmentID,definit,navController,onDownload={
                    val requst= DownloadManager.Request(Uri.parse(it))
                        .setTitle("nombre")
                        .setDescription("Descargando....")
                        .setNotificationVisibility(DownloadManager.Request.VISIBILITY_VISIBLE_NOTIFY_COMPLETED)
                        .setAllowedOverMetered(true)
                    val da = getSystemService(ComponentActivity.DOWNLOAD_SERVICE) as DownloadManager
                    da.enqueue(requst)
                })

            }
        }
    }
}

@Composable
fun AppointmentNavHost(Medical:Boolean,
                       ID:String,
                       appointmentID:String,
                       definit:String,
                       navHostController:NavHostController,
                       onDownload:(String)->Unit
                     ) {
    val activity = (LocalContext.current as? Activity)

    // A surface container using the 'background' color from the theme
    Surface(
        modifier = Modifier.fillMaxSize(),
        color = MaterialTheme.colors.background
    ) {
        NavHost(navController = navHostController, startDestination = definit) {
            composable(AppointmentDestinations.HomeSelect.route) {
                Column(modifier = Modifier
                    .fillMaxSize()
                    .wrapContentHeight(Alignment.CenterVertically)) {
                    HomeSelect(
                        NavSimpleIn = {
                            navHostController.navigate( AppointmentDestinations.AppointmentCreate.createRoute()   )
                        },
                        NavLabIn={
                            navHostController.navigate( AppointmentDestinations.AppointmentLabCreate.createRoute()  )
                        }
                    )
                }





            }
            composable(
                AppointmentDestinations.AppointmentCreate.route,
                arguments = listOf(navArgument("newText"){ defaultValue = "Check In" })
            ) {

                AppointmentCreate(Medical,ID,
                    Save={

                        activity?.finish()
                    },
                    NavBack={
                        navHostController.navigate(  AppointmentDestinations.HomeSelect.createRoute() )
                    })


            }
            composable(
                AppointmentDestinations.AppointmentLabCreate.route,
                arguments = listOf(navArgument("newText"){ defaultValue = "Log In" })
            ) {


                AppointmentLabCreate(Medical,ID,
                    Save={
                        activity?.finish()
                    },
                    NavBack={
                        navHostController.navigate(  AppointmentDestinations.HomeSelect.createRoute() )
                    })


            }
            composable(
                AppointmentDestinations.AppointmentPatient.route,
                arguments = listOf(navArgument("newText"){ defaultValue = "Log In" })
            ) {

                AppointmentPatient(appointmentID, onDownload = {


                })

            }
            composable(
                AppointmentDestinations.AppointmentMedical.route,
                arguments = listOf(navArgument("newText"){ defaultValue = "Log In" })
            ) {


                AppointmentMedical(appointmentID, onDownload = {
                    onDownload.invoke(it)
                },
                onNav = {
                    navHostController.navigate(  AppointmentDestinations.Odontogram.createRoute() )


                })



            }
            composable(
                AppointmentDestinations.AppointmentLab.route,
                arguments = listOf(navArgument("newText"){ defaultValue = "Log In" })
            ) {

                AppointmentLabPatient(appointmentID, onDownload = {
                    onDownload.invoke(it)
                })




            }
            composable(
                AppointmentDestinations.Odontogram.route,
                arguments = listOf(navArgument("newText"){ defaultValue = "Log In" })
            ) {

                Odontogram(appointmentID)


            }
        }
    }
}

