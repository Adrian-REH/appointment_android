package app.ibiocd.appointment.auth.view

import android.content.ContentValues.TAG
import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import app.ibiocd.appointment.notification.viewmodel.NotificationViewModel
import app.ibiocd.appointment.auth.navegation.SessionDestinationsActivity
import app.ibiocd.appointment.ui.theme.AppointmentTheme
import com.google.android.gms.tasks.OnCompleteListener
import com.google.firebase.messaging.FirebaseMessaging
import app.ibiocd.appointment.auth.view.screens.Beginning
import app.ibiocd.appointment.auth.view.screens.CheckIn
import app.ibiocd.appointment.auth.view.screens.LogIn
import app.ibiocd.appointment.auth.view.screens.ResetPassword
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class SessionActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            val navController = rememberNavController()
            var definit= SessionDestinationsActivity.Beginning.route
            var token by remember {
                mutableStateOf("")
            }

            FirebaseMessaging.getInstance().token.addOnCompleteListener(OnCompleteListener { task ->
                if (!task.isSuccessful) {
                    Log.w(TAG, "Fetching FCM registration token failed", task.exception)
                    return@OnCompleteListener
                }

                // Get new FCM registration token
                val token = task.result

                // Log and toast
                Log.d("TOKEN", token.toString())
            })

            if (token!=""){

            }
            AppointmentTheme {




                // A surface container using the 'background' color from the theme
                SessionNavHost(navController,definit,token)



            }
        }
    }
}

@Composable
fun SessionNavHost(navController: NavHostController, definit: String,token:String,viewModel: NotificationViewModel = hiltViewModel()
){
    val scope = rememberCoroutineScope()
    val state =viewModel.notificationState.collectAsState(initial = null)

 //viewModel.newNotification(PushNotification(NotificationData("HOLAAAAA","Notificado dentro de la app"),"eLQXgZHqTT-Y4H-nCwu7hR:APA91bFfcQaVXxYJLiJZkyFNl-AwDZriq20JU0sWfTYlHJiq8_gKnzCAcUqWyFtpQ-5ylGZyMfDKcIa4-1MQY1ShX7-9ic3LoICFdu8vYExouoB1TluJxuhBVCUdZaJIUVs6nXAyisZy"))

    Surface(
        modifier = Modifier.fillMaxSize(),
        color = MaterialTheme.colors.background
    ) {
        NavHost(navController = navController, startDestination = definit) {
            composable(SessionDestinationsActivity.Beginning.route) {
                Column(modifier = Modifier
                    .fillMaxSize()
                    .wrapContentHeight(Alignment.CenterVertically)) {
                    Beginning(
                        NavCheckIn = {
                            navController.navigate( SessionDestinationsActivity.CheckIn.createRoute()   )
                        },
                        NavLogIn={
                            navController.navigate( SessionDestinationsActivity.LogIn.createRoute()  )
                        }
                    )
                }

            }
            composable(
                SessionDestinationsActivity.CheckIn.route,
                arguments = listOf(navArgument("newText"){ defaultValue = "Check In" })
            ) {

                CheckIn(
                    NavBeginning={
                        navController.navigate( SessionDestinationsActivity.Beginning.createRoute()  )
                    },
                    NavLogIn={
                        navController.navigate( SessionDestinationsActivity.LogIn.createRoute()  )
                    })
            }
            composable(
                SessionDestinationsActivity.LogIn.route,
                arguments = listOf(navArgument("newText"){ defaultValue = "Log In" })
            ) {

                LogIn(
                    NavBeginning={
                        navController.navigate( SessionDestinationsActivity.Beginning.createRoute()  )
                    },
                    NavReset={
                        navController.navigate( SessionDestinationsActivity.ResetPassword.createRoute()  )
                    })

            }
            composable(
                SessionDestinationsActivity.ResetPassword.route,
                arguments = listOf(navArgument("newText"){ defaultValue = "Log In" })
            ) {

                ResetPassword (NavLogIn = {
                    navController.navigate(SessionDestinationsActivity.LogIn.createRoute())
                }, NavBeginning = {                        navController.navigate( SessionDestinationsActivity.Beginning.createRoute()  )
                })

            }
        }
    }

}
