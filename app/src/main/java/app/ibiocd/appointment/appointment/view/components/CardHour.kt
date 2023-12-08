package app.ibiocd.appointment.appointment.view.components

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import app.ibiocd.appointment.presentation.normalized.BlockSpace

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun CardHour(hour:String,onSelect:(String)-> Unit){
    Card(

        shape = RoundedCornerShape(30.dp),
        modifier = Modifier
            .padding(horizontal = 5.dp, vertical = 5.dp),
        onClick = {
            onSelect(hour)
        }

    ) {
        Column() {

            Row() {
                BlockSpace()
                Text(hour,fontSize = 16.sp, fontFamily = FontFamily.SansSerif, fontWeight = FontWeight.Normal)
                BlockSpace()

            }

        }


    }
}