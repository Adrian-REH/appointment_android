package app.ibiocd.appointment.home.view.components.files

import android.content.Intent
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
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
import app.ibiocd.appointment.model.Patient
import app.ibiocd.appointment.presentation.normalized.BlockSpace
import app.ibiocd.appointment.presentation.normalized.Spacers

@Composable
fun ListAppointmentMedical(appointments: List<Appointment>,
                           files: List<Files>,
                           patient: List<Patient>,
                           isLoading: Boolean,
                           ID: String
                           ){
    val context= LocalContext.current

    LazyColumn(modifier = Modifier
        .fillMaxSize()
        .padding(bottom = 60.dp)
        .background(Color.White))
    {




        //FORMS
        item {
            Column {

                Spacers()
                BlockSpace()
                Row( verticalAlignment = Alignment.CenterVertically) {
                    Text(
                        text = "Forms",
                        style = TextStyle(color = Color.Black, fontSize = 20.sp, fontWeight = FontWeight.Normal),
                        modifier = Modifier.padding(start = 20.dp)
                    )
                    Spacers()
                    IconButton(onClick = { /*TODO*/ }) {
                        Icon( painterResource(id = R.drawable.ic_more), contentDescription ="" , modifier = Modifier.size(25.dp), tint = Color.Gray)
                    }
                }

                Spacers()

                LazyRow{
                    var itemCountForm = appointments.size
                    if (isLoading) itemCountForm++

                    items(count = itemCountForm) { index ->
                        var auxIndex = index;
                        if (isLoading) {
                            if (auxIndex == 0)
                                return@items LoadingCardForm()
                            auxIndex--
                        }
                        if (itemCountForm!=0){

                            val appointmentSForm = appointments[auxIndex]
                            val fileform= files.firstOrNull { it._id == appointments[auxIndex].files && it.prescriptions != "" }
                            val patientform= patient.firstOrNull { it._id == appointments[auxIndex].patient  }
                            if (fileform != null) {

                                if (appointmentSForm.files==fileform._id){
                                    if (patientform != null) {

                                        CardForm(appointmentSForm,fileform,patientform.name_last, onOpenAppointment = {
                                            val intent = Intent(context,
                                                AppointmentActivity::class.java)
                                            intent.putExtra("medicals","true")
                                            intent.putExtra("id", ID)
                                            intent.putExtra("appointment", appointmentSForm._id)
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


        //STUDY
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
        var itemCountStudi =appointments.size
        if (isLoading) itemCountStudi++
        items(count = itemCountStudi) { index ->
            var auxIndex = index;
            if (isLoading) {
                if (auxIndex == 0)
                    return@items LoadingCardStudiAppointment()
                auxIndex--
            }
            if (itemCountStudi!=0){
                val appointmentStudi = appointments[auxIndex]
                val fileStudi= files.firstOrNull { it._id == appointments[auxIndex].files && it.prescriptions != "" }
                val patientStudi= patient.firstOrNull { it._id == appointments[auxIndex].patient  }

                if (fileStudi != null) {
                    if (appointmentStudi.files==fileStudi._id){
                        if (patientStudi != null) {
                            CardStudiAppointment(appointmentStudi,fileStudi,patientStudi.name_last)

                        }
                    }

                }
            }
            //CardStudiAppointment(users)
        }

        //laboratory
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
        var itemCountLab =appointments.size
        if (isLoading) itemCountLab++
        items(count = itemCountLab) { index ->
            var auxIndex = index;
            if (isLoading) {
                if (auxIndex == 0)
                    return@items LoadingCardLabAppointment()
                auxIndex--
            }
            if (itemCountLab!=0){
                val appointmentLab = appointments[auxIndex]
                val fileLab= files.firstOrNull { it._id == appointments[auxIndex].files && it.prescriptions != "" }
                val patientLab= patient.firstOrNull { it._id == appointments[auxIndex].patient  }

                if (fileLab != null) {
                    if (appointmentLab.files==fileLab._id){
                        if (patientLab != null) {
                            CardLabAppointment(appointmentLab,fileLab,patientLab.name_last)

                        }
                    }

                }
            }
        }

        //Recipe
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
        var itemCountRecipe =appointments.size
        if (isLoading) itemCountRecipe++
        items(count = itemCountRecipe) { index ->
            var auxIndex = index;
            if (isLoading) {
                if (auxIndex == 0)
                    return@items LoadingCardRecipeAppointment()
                auxIndex--
            }
            if (itemCountRecipe!=0){
                val appointmentRecipe = appointments[auxIndex]
                val fileRecipe= files.firstOrNull { it._id == appointments[auxIndex].files && it.prescriptions != "" }
                val patientRecipe= patient.firstOrNull { it._id == appointments[auxIndex].patient  }

                if (fileRecipe != null) {
                    if (appointmentRecipe.files==fileRecipe._id){
                        if (patientRecipe != null) {
                            CardRecipeAppointment(appointmentRecipe,fileRecipe,patientRecipe.name_last)

                        }
                    }

                }
            }
        }

        //Odontograms
        item {
            BlockSpace()
            Row( verticalAlignment = Alignment.CenterVertically) {
                Text(
                    text = "Odontograms",
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
        var itemCountSimple =appointments.size
        if (isLoading) itemCountSimple++
        items(count = itemCountSimple) { index ->
            var auxIndex = index;
            if (isLoading) {
                if (auxIndex == 0)
                    return@items LoadingCardSimpleAppointment()
                auxIndex--
            }
            if (itemCountSimple!=0){
                val appointmentSimple = appointments[auxIndex]
                val filesimple= files.firstOrNull { it._id == appointments[auxIndex].files && it.prescriptions != "" }
                val patientsimple= patient.firstOrNull { it._id == appointments[auxIndex].patient  }

                if (filesimple != null) {
                    if (appointmentSimple.files==filesimple._id){
                        if (patientsimple != null) {
                            CardSimpleAppointment(appointmentSimple,filesimple,patientsimple.name_last, onOpenAppointment = {
                                val intent = Intent(context,
                                    AppointmentActivity::class.java)
                                intent.putExtra("medicals","true")
                                intent.putExtra("id", ID)
                                intent.putExtra("appointment", appointmentSimple._id)
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