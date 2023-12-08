package app.ibiocd.appointment.home.view.components.files

import android.content.Intent
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import app.ibiocd.appointment.R
import app.ibiocd.appointment.appointment.view.AppointmentActivity
import app.ibiocd.appointment.home.view.components.*
import app.ibiocd.appointment.model.Appointment
import app.ibiocd.appointment.model.Files
import app.ibiocd.appointment.profile.datasource.entity.Medical
import app.ibiocd.appointment.presentation.normalized.BlockSpace
import app.ibiocd.appointment.presentation.normalized.Spacers


@Composable
fun ListAppointmentPatient(appointments: List<Appointment>, files: List<Files>, medical: List<Medical>, isLoading: Boolean, ID:String) {

    val context= LocalContext.current

    LazyColumn(modifier = Modifier
        .fillMaxSize()
        .padding(bottom = 60.dp)
        .background(Color.White))
    {

        item {
            BlockSpace()
            Row( verticalAlignment = Alignment.CenterVertically) {
                Text(
                    text = "Study",
                    style = TextStyle(color = Color.Black, fontSize = 20.sp, fontWeight = FontWeight.Normal),
                    modifier = Modifier.padding(start = 20.dp)
                )
                Spacers()
                IconButton(onClick = { /*TODO*/ }) {
                    Icon( painterResource(id = R.drawable.ic_more), contentDescription ="" , modifier = Modifier.size(25.dp), tint = Color.Gray)
                }
            }

            Spacers()
        }

        var itemCountStudio = appointments.size
        if (isLoading) itemCountStudio++

        items(count = itemCountStudio) { index ->
            var auxIndex = index;
            if (isLoading) {
                if (auxIndex == 0)
                    return@items LoadingCardStudiAppointment()
                auxIndex--
            }
            if (itemCountStudio!=0){

            val appointmentstudio = appointments[auxIndex]

            val filestudio= files.firstOrNull { it._id == appointments[auxIndex].files && it.prescriptions != "" }
            val medicaltudio= medical.firstOrNull { it._id == appointments[auxIndex].medical  }
            if (filestudio != null) {
                if (appointmentstudio.files==filestudio._id){
                    if (medicaltudio != null) {
                        CardStudiAppointment(appointmentstudio,filestudio,medicaltudio.name_last,onOpenAppointment={
                            val intent = Intent(context,
                                AppointmentActivity::class.java)
                            intent.putExtra("medicals","false")
                            intent.putExtra("id", ID)
                            intent.putExtra("appointment", appointmentstudio._id)
                            intent.putExtra("resume", "0")

                            context.startActivity(intent)
                        })
                    }
                }
            }

            }

        }


        item {
            BlockSpace()
            Row( verticalAlignment = Alignment.CenterVertically) {
                Text(
                    text = "Laboratory",
                    style = TextStyle(color = Color.Black, fontSize = 20.sp, fontWeight = FontWeight.Normal),
                    modifier = Modifier.padding(start = 20.dp)
                )
                Spacers()
                IconButton(onClick = { /*TODO*/ }) {
                    Icon( painterResource(id = R.drawable.ic_more), contentDescription ="" , modifier = Modifier.size(25.dp), tint = Color.Gray)
                }
            }

            Spacers()
        }

        var itemCountLab = appointments.size
        if (isLoading) itemCountLab++

        items(count = itemCountLab) { index ->
            var auxIndex = index;
            if (isLoading) {
                if (auxIndex == 0)
                    return@items LoadingCardLabAppointment()
                auxIndex--
            }
            if (itemCountLab!=0){
                val appointmentlab = appointments[auxIndex]
                val filelab= files.firstOrNull { it._id == appointments[auxIndex].files && it.prescriptions != "" }
                val medicallab= medical.firstOrNull { it._id == appointments[auxIndex].medical  }
                if (filelab != null) {
                    if (appointmentlab.files==filelab._id){
                        if (medicallab != null) {
                            CardLabAppointment(appointmentlab,filelab,medicallab.name_last,onOpenAppointment={
                                val intent = Intent(context,
                                    AppointmentActivity::class.java)
                                intent.putExtra("medicals","false")
                                intent.putExtra("id", ID)
                                intent.putExtra("appointment", appointmentlab._id)
                                intent.putExtra("resume", "0")

                                context.startActivity(intent)
                            })
                        }
                    }
                }
            }

        }

        item {
            BlockSpace()
            Row( verticalAlignment = Alignment.CenterVertically) {
                Text(
                    text = "Recipe",
                    style = TextStyle(color = Color.Black, fontSize = 20.sp, fontWeight = FontWeight.Normal),
                    modifier = Modifier.padding(start = 20.dp)
                )
                Spacers()
                IconButton(onClick = { /*TODO*/ }) {
                    Icon( painterResource(id = R.drawable.ic_more), contentDescription ="" , modifier = Modifier.size(25.dp), tint = Color.Gray)
                }
            }

            Spacers()
        }


        var itemCountRecipe = appointments.size
        if (isLoading) itemCountRecipe++
        items(count = itemCountRecipe) { index ->
            var auxIndex = index;
            if (isLoading) {
                if (auxIndex == 0)
                    return@items LoadingCardRecipeAppointment()
                auxIndex--
            }
            if (itemCountRecipe!=0){
                val appointmentrecipe = appointments[auxIndex]

                val filerecipe= files.firstOrNull { it._id == appointments[auxIndex].files && it.prescriptions != "" }
                val medicalrecipe= medical.firstOrNull { it._id == appointments[auxIndex].medical  }

                if (filerecipe != null) {
                    if (appointmentrecipe.files==filerecipe._id){
                        if (medicalrecipe != null) {
                            CardRecipeAppointment(appointmentrecipe,filerecipe,medicalrecipe.name_last,onOpenAppointment={
                                val intent = Intent(context,
                                    AppointmentActivity::class.java)
                                intent.putExtra("medicals","false")
                                intent.putExtra("id", ID)
                                intent.putExtra("appointment", appointmentrecipe._id)
                                intent.putExtra("resume", "0")

                                context.startActivity(intent)
                            })
                        }

                    }
                }


            }


        }



    }
}
