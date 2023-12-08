package app.ibiocd.appointment.presentation.components.profile

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Button
import androidx.compose.material.Card
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import app.ibiocd.appointment.presentation.normalized.BlockSpace
import app.ibiocd.appointment.presentation.normalized.Spacers


@Preview
@Composable
fun Check(){
    var detalle="deetaallee"
    Card(
        shape = RoundedCornerShape(30.dp),
        elevation = 10.dp,
        modifier = Modifier
            .padding(horizontal = 20.dp, vertical = 5.dp)
            .fillMaxWidth()
    ) {
        Column(modifier = Modifier.fillMaxWidth()) {
            Spacers()

            BlockSpace()
            Text("Verifica tu identidad", fontSize = 10.sp,fontFamily = FontFamily.SansSerif, fontWeight = FontWeight.Light, modifier = Modifier.padding(start = 20.dp))
            Spacers()


            Text("Agrega la insignia de verificado para que los \n" +
                    "pacientes sepan que realmente sos vos :", fontSize = 10.sp,fontFamily = FontFamily.SansSerif, fontWeight = FontWeight.Light, modifier = Modifier.padding(start = 20.dp))
            Text(detalle, fontSize = 10.sp,fontFamily = FontFamily.SansSerif, fontWeight = FontWeight.Light, modifier = Modifier.padding(start = 20.dp))
            Spacers()
            Button(onClick = { /*TODO*/ },  modifier = Modifier.padding(start = 20.dp)) {
                Text("VERIFICAR", fontSize = 10.sp,fontFamily = FontFamily.SansSerif, fontWeight = FontWeight.Light)

            }
            Spacers()
            BlockSpace()
        }
    }

}
