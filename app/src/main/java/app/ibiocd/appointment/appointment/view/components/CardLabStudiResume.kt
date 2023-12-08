package app.ibiocd.appointment.appointment.view.components

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.material.Divider
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import app.ibiocd.appointment.model.Appointment
import app.ibiocd.appointment.presentation.normalized.BlockSpace
import app.ibiocd.appointment.presentation.normalized.Spacers


@Composable
fun CardLabStudiResume(patientname:String,
                       labname:String,
                       appointment: Appointment) {
    var comment by remember { mutableStateOf("") }


    Card(
        shape = RoundedCornerShape(30.dp),
        elevation = 20.dp,
        modifier = Modifier
            .padding(horizontal = 30.dp)
            .fillMaxWidth()

    ){
        Column(modifier = Modifier.padding(horizontal = 20.dp)) {
            Spacers()

            BlockSpace()
            Divider(modifier = Modifier
                .fillMaxWidth()
                .height(1.dp))
            Spacers()
            Text(patientname,fontSize = 16.sp, fontFamily = FontFamily.SansSerif, fontWeight = FontWeight.Normal)
            Spacers()
            Divider(modifier = Modifier
                .fillMaxWidth()
                .height(1.dp))
            Spacers()

            Text(labname,fontSize = 16.sp, fontFamily = FontFamily.SansSerif, fontWeight = FontWeight.Normal)
            Spacers()
            Divider(modifier = Modifier
                .fillMaxWidth()
                .height(1.dp))
            Spacers()

            Text("${appointment.profession} / ${appointment.specialty}",fontSize = 16.sp, fontFamily = FontFamily.SansSerif, fontWeight = FontWeight.Normal)
            Spacers()
            Divider(modifier = Modifier
                .fillMaxWidth()
                .height(1.dp))
            Spacers()
            Row() {
                Text(appointment.fecha,fontSize = 16.sp, fontFamily = FontFamily.SansSerif, fontWeight = FontWeight.Normal)
                Spacers()
                Text(appointment.hora,fontSize = 16.sp, fontFamily = FontFamily.SansSerif, fontWeight = FontWeight.Normal)

            }
            Spacers()
            Divider(modifier = Modifier
                .fillMaxWidth()
                .height(1.dp))



            BlockSpace()

            Row(modifier = Modifier.fillMaxWidth(), verticalAlignment = Alignment.Bottom) {
                Column(modifier = Modifier.weight(1f).wrapContentWidth(Alignment.End)) {
                    Text("$3.500",fontSize = 10.sp, fontFamily = FontFamily.SansSerif, fontWeight = FontWeight.Normal)
                    Text("$2500",fontSize = 16.sp, fontFamily = FontFamily.SansSerif, fontWeight = FontWeight.Normal)

                }
                Spacers()

                Text("$2500",fontSize = 10.sp, fontFamily = FontFamily.SansSerif, fontWeight = FontWeight.Normal, color = Color.Green)

            }

            BlockSpace()
            Spacers()
        }
    }
}