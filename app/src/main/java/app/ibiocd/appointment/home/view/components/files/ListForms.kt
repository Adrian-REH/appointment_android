package app.ibiocd.appointment.home.view.components.files

import android.content.Intent
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
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
import app.ibiocd.appointment.home.view.components.CardForm
import app.ibiocd.appointment.home.view.components.LoadingCardForm
import app.ibiocd.appointment.presentation.normalized.BlockSpace
import app.ibiocd.appointment.presentation.normalized.Spacers


@OptIn(ExperimentalFoundationApi::class)
@Composable
fun ListForms(appointments: List<Appointment>,
              files: List<Files>,
              patient: List<Patient>?,
              isLoading: Boolean,
              ID: String,
              onSelectItem: ((toDelete: Appointment) -> Unit)? = null){

    val context= LocalContext.current

    var Title= "Forms"

    Column(modifier = Modifier
        .fillMaxSize()) {

        Spacers()
        BlockSpace()
        Text(
            text = Title,
            style = TextStyle(color = Color.Black, fontSize = 20.sp, fontWeight = FontWeight.Normal),
            modifier = Modifier.padding(start = 20.dp)
        )
        BlockSpace()
        LazyVerticalGrid( GridCells.Adaptive(minSize = 160.dp), modifier = Modifier
            .fillMaxSize()
            .padding(bottom = 60.dp)
        )
        {


            var itemCount = appointments.size
            if (isLoading) itemCount++

            items(count = itemCount) { index ->
                var auxIndex = index;
                if (isLoading) {
                    if (auxIndex == 0)
                        return@items LoadingCardForm()
                    auxIndex--
                }
                if (itemCount!=0){

                    val appointmentstudio = appointments[auxIndex]

                    val filestudio= files.firstOrNull { it._id == appointments[auxIndex].files && it.form != "" }
                    if (filestudio != null && appointmentstudio.files==filestudio._id) {

                        if (patient!=null){
                            val patients= patient.firstOrNull { it._id == appointments[auxIndex].patient  }
                            if (patients != null) {
                                CardForm(appointmentstudio,filestudio,patients.name_last, onOpenAppointment = {
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

}