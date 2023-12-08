package app.ibiocd.appointment.home.view.components.files

import android.content.Intent
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import app.ibiocd.appointment.appointment.view.AppointmentActivity
import app.ibiocd.appointment.model.Appointment
import app.ibiocd.appointment.model.Files
import app.ibiocd.appointment.model.Patient
import app.ibiocd.appointment.home.view.components.CardSimpleAppointment
import app.ibiocd.appointment.home.view.components.LoadingCardSimpleAppointment
import app.ibiocd.appointment.presentation.normalized.BlockSpace
import app.ibiocd.appointment.presentation.normalized.Spacers


@Composable
fun ListOdontograms(appointments: List<Appointment>,
                    files: List<Files>,
                    patient: List<Patient>?,
                    isLoading: Boolean,
                    ID: String,
                    onSelectItem: ((toDelete: Appointment) -> Unit)? = null){

    val context= LocalContext.current

    LazyColumn(modifier = Modifier
        .fillMaxSize()
        .padding(bottom = 60.dp)
        .background(Color.White))
    {


        var Title= "Odontogram"




        item {
            BlockSpace()

            Text(
                text = Title,
                style = TextStyle(color = Color.Black, fontSize = 20.sp, fontWeight = FontWeight.Normal),
                modifier = Modifier.padding(start = 35.dp)
            )
            Spacers()
        }

        var itemCount = appointments.size
        if (isLoading) itemCount++

        items(count = itemCount) { index ->
            var auxIndex = index;
            if (isLoading) {
                if (auxIndex == 0)
                    return@items LoadingCardSimpleAppointment()
                auxIndex--
            }
            if (itemCount!=0){

                val appointmentstudio = appointments[auxIndex]

                val filestudio= files.firstOrNull { it._id == appointments[auxIndex].files && it.odontogram != "" }
                if (filestudio != null && appointmentstudio.files==filestudio._id) {
                    if (patient!=null){
                        val patients= patient.firstOrNull { it._id == appointments[auxIndex].patient  }
                        if (patients != null) {
                            CardSimpleAppointment(appointmentstudio,filestudio,patients.name_last, onOpenAppointment = {
                                val intent = Intent(context,
                                    AppointmentActivity::class.java)
                                intent.putExtra("medicals","true")
                                intent.putExtra("id", ID)
                                intent.putExtra("appointment", appointmentstudio._id)
                                intent.putExtra("resume", "1")

                                context.startActivity(intent)
                            })
                        }
                    }
                }
            }

        }


    }
}
