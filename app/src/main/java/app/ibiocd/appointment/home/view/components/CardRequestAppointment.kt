package app.ibiocd.appointment.home.view.components

import android.content.Intent
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import app.ibiocd.appointment.R
import app.ibiocd.appointment.presentation.normalized.BlockSpace
import app.ibiocd.appointment.presentation.normalized.BlockSpaces
import app.ibiocd.appointment.presentation.normalized.Spacers



@Composable
fun CardCreateAppointment(medical: Boolean,identiti:String, onSelect:()->Unit) {
    val context = LocalContext.current

    Card(
        shape = RoundedCornerShape(30.dp),
        elevation = 20.dp,
        modifier = Modifier
            .padding(horizontal = 20.dp)
            .fillMaxWidth()
            .testTag("card_new_appointment")
    ) {

        Row(modifier = Modifier
            .height(IntrinsicSize.Min)
            .wrapContentHeight(Alignment.CenterVertically), verticalAlignment = Alignment.CenterVertically) {


            Column(modifier = Modifier
                .fillMaxHeight()
                .weight(1f)
                .padding(start = 20.dp)
                .padding(vertical = 20.dp))
            {

                Text("Presencial/Virtual", modifier = Modifier.width(80.dp),fontSize = 11.sp, fontFamily = FontFamily.SansSerif, fontWeight = FontWeight.Light, color = Color.Gray)
                BlockSpace()
                Text("Solicita un turno", modifier = Modifier.width(80.dp).testTag("request_new_appointment"), fontSize = 14.sp,fontFamily = FontFamily.SansSerif, fontWeight = FontWeight.Bold)
                BlockSpaces()

                Row(modifier = Modifier.width(IntrinsicSize.Min), verticalAlignment = Alignment.CenterVertically) {
                    Button(onClick = {
                        onSelect.invoke()
                    },modifier = Modifier
                        .weight(1f)
                        .height(70.dp)
                        .testTag("btn_new_appointment")
                        .wrapContentHeight(Alignment.Bottom)) {

                        Text("VAMOS")
                    }
                    Spacers()
                    Icon( painterResource(id = R.drawable.ic_add_circle), contentDescription ="" , modifier = Modifier.size(30.dp), tint = Color.Gray)

                }

            }

            Image(painterResource(id = R.drawable.ic__request_appointment), contentDescription = "")
        }
    }
}
