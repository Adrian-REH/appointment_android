package com.nopalsoft.simple.rest.presentation

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import app.ibiocd.appointment.UserViewModel
import app.ibiocd.appointment.model.Appointment
import app.ibiocd.appointment.appointment.view.components.CardLabStudiResume
import app.ibiocd.appointment.presentation.normalized.BlockSpace
import app.ibiocd.appointment.presentation.normalized.Spacers

@Composable
fun AppointmentLabTwo(
    patientname:String,
    labname:String,
    appointment: Appointment,
    specialtyprice: String,
    viewModel: UserViewModel = hiltViewModel(),
    Save: (Appointment) -> Unit
) {
    val isLoading by viewModel.isLoading.observeAsState(false)

    var AppointmentItem by remember { mutableStateOf(appointment) }

    AppointmentItem=appointment
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        Spacers()
        BlockSpace()
        Text("Resumen ",fontSize = 20.sp, fontFamily = FontFamily.SansSerif, fontWeight = FontWeight.Normal,modifier = Modifier.padding(start = 20.dp))
        Spacers()
        CardLabStudiResume(patientname,labname,appointment)

        BlockSpace()
        Column(modifier = Modifier
            .fillMaxWidth()
            .wrapContentWidth(Alignment.CenterHorizontally), horizontalAlignment = Alignment.CenterHorizontally) {
            OutlinedButton(
                onClick = {

                    if (!isLoading){

                        AppointmentItem.price=specialtyprice
                        Save.invoke(AppointmentItem)
                    }
                }
                ,
                modifier = Modifier
                    .height(40.dp)
                    .wrapContentWidth(Alignment.CenterHorizontally),
                shape = RoundedCornerShape(50),
            ) {
                Text("CONFIRMAR",fontSize = 14.sp, fontFamily = FontFamily.SansSerif, fontWeight = FontWeight.Normal,modifier = Modifier.padding(start = 20.dp), color = Color.Black)
                Spacers()

            }
            Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.Center){
                if(isLoading){
                    CircularProgressIndicator()
                }
            }
        }


    }
}
