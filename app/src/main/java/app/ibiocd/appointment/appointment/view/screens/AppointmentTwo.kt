package com.nopalsoft.simple.rest.presentation

import android.widget.Toast
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Alignment.Companion.CenterHorizontally
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import app.ibiocd.appointment.UserViewModel
import app.ibiocd.appointment.model.Appointment
import app.ibiocd.appointment.appointment.view.components.CardSimpleResume
import app.ibiocd.appointment.presentation.normalized.BlockSpace
import app.ibiocd.appointment.presentation.normalized.Spacers

@Composable
fun AppointmentTwo(
    patientname: String, medicalname: String,
    appointment: Appointment,
    specialtyprice: String,
    viewModel: UserViewModel = hiltViewModel(),
    Save: (Appointment) -> Unit
) {
    var AppointmentItem by remember { mutableStateOf(appointment) }
    val isLoading by viewModel.isLoading.observeAsState(false)

    val context= LocalContext.current
    AppointmentItem=appointment
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {

        BlockSpace()
        Text("Resumen ",fontSize = 20.sp, fontFamily = FontFamily.SansSerif, fontWeight = FontWeight.Normal,modifier = Modifier.padding(start = 20.dp))
        Spacers()
        CardSimpleResume(patientname,medicalname,appointment,specialtyprice, onComment = {
            AppointmentItem.coment=it
        })
        BlockSpace()
        Column(modifier = Modifier
            .fillMaxWidth()
            .wrapContentWidth(Alignment.CenterHorizontally), horizontalAlignment = Alignment.CenterHorizontally) {
            OutlinedButton(
                onClick = {
                    if (AppointmentItem.hora!=""&& AppointmentItem.medical!=""&& AppointmentItem.patient!=""&& AppointmentItem.profession!=""&& AppointmentItem.specialty!=""&& AppointmentItem.fecha!=""){
                        if (!isLoading){

                            AppointmentItem.price=specialtyprice
                            Save.invoke(AppointmentItem)
                        }
                    }else{
                        Toast.makeText(context,"Por favor llena antes el formulario",Toast.LENGTH_LONG).show()
                    }

                }
                ,
                modifier = Modifier
                    .height(40.dp)
                    .wrapContentWidth(CenterHorizontally),
                shape = RoundedCornerShape(50),
            ) {
                Text("CONFIRMAR",fontSize = 14.sp, fontFamily = FontFamily.SansSerif, fontWeight = FontWeight.Normal,modifier = Modifier.padding(start = 20.dp), color = Color.Black)
                Spacers()

            }
            Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.Center){
                if(isLoading ){
                    CircularProgressIndicator()
                }
            }
        }


    }
}
