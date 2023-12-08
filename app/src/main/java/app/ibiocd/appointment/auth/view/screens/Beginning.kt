package app.ibiocd.appointment.auth.view.screens

import android.app.Activity
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import app.ibiocd.appointment.file.viewmodel.FileViewModel
import app.ibiocd.appointment.UserViewModel
import app.ibiocd.appointment.appointment.viewmodel.AppointmentViewModel
import app.ibiocd.appointment.appointment.viewmodel.OdontogramViewModel
import app.ibiocd.appointment.home.viewmodel.FavViewModel
import app.ibiocd.appointment.presentation.normalized.BlockSpace
import app.ibiocd.appointment.presentation.normalized.BlockSpaces
import app.ibiocd.appointment.presentation.normalized.Spacers
import app.ibiocd.appointment.profile.viewmodel.MedicalViewModel
import app.ibiocd.appointment.profile.viewmodel.PatientViewModel
import kotlinx.coroutines.launch
import javax.inject.Scope

@Composable
fun Beginning(
    NavCheckIn: () -> Unit,
    NavLogIn: () -> Unit,
    viewModelappointment: AppointmentViewModel = hiltViewModel(),
    fileViewModel: FileViewModel = hiltViewModel(),
    patientViewModel: PatientViewModel = hiltViewModel(),
    favViewModel: FavViewModel = hiltViewModel(),
    medicalViewModel: MedicalViewModel = hiltViewModel(),
    viewModelOdont: OdontogramViewModel = hiltViewModel()
) {

    suspend {
        viewModelappointment.deleteAllAppointment()
        medicalViewModel.deleteAllMedical()
        patientViewModel.deleteAllPatient()
        fileViewModel.deleteAllFiles()
        favViewModel.deleteAllFavs()
        viewModelOdont.deleteOdontogram()

    }



    val context = LocalContext.current
    val activity = (LocalContext.current as? Activity)
    Column(
        horizontalAlignment = Alignment.CenterHorizontally, modifier = Modifier
            .padding(horizontal = 16.dp)
    ) {
        OutlinedButton(
            onClick = { NavLogIn() }, modifier = Modifier
                .fillMaxWidth()
                .height(80.dp),
            shape = RoundedCornerShape(30),
            elevation = ButtonDefaults.elevation(
                defaultElevation = 5.dp,
                pressedElevation = 5.dp,
                disabledElevation = 0.dp
            )
        ) {
            Text(
                text = "Iniciar sesion",
                fontSize = 15.sp,
                color = Color.Black,
                fontFamily = FontFamily.SansSerif
            )

        }
        BlockSpace()
        BlockSpace()
        OutlinedButton(
            onClick = NavCheckIn, modifier = Modifier
                .fillMaxWidth()
                .height(80.dp),
            shape = RoundedCornerShape(30),
            elevation = ButtonDefaults.elevation(
                defaultElevation = 5.dp,
                pressedElevation = 5.dp,
                disabledElevation = 0.dp
            )
        ) {
            Text(
                text = "Registrarse",
                fontSize = 15.sp,
                color = Color.Black,
                fontFamily = FontFamily.SansSerif
            )

        }
        Spacers()
        BlockSpaces()


    }
}
