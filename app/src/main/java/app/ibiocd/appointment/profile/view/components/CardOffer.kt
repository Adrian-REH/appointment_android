package app.ibiocd.appointment.presentation.components.profile

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.material.Divider
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
fun CardOffer(){
    var detalle="deetaallee"
    Card(
        shape = RoundedCornerShape(30.dp),
        elevation = 10.dp,
        modifier = Modifier
            .padding(horizontal = 20.dp, vertical = 5.dp)
            .fillMaxWidth()
    ) {
        Column( modifier = Modifier.padding(horizontal = 20.dp)) {
            BlockSpace()
            Spacers()

            Divider(modifier = Modifier.fillMaxWidth().height(1.dp))
            Spacers()

            Text("FARMACITY", fontSize = 10.sp,fontFamily = FontFamily.SansSerif, fontWeight = FontWeight.Light)
            Spacers()

            Divider(modifier = Modifier.fillMaxWidth().height(1.dp))
            Spacers()

            Text("% DE DESCUENTO", fontSize = 10.sp,fontFamily = FontFamily.SansSerif, fontWeight = FontWeight.Light)
            Spacers()

            Divider(modifier = Modifier.fillMaxWidth().height(1.dp))
            Spacers()

            Text("Detalle:", fontSize = 10.sp,fontFamily = FontFamily.SansSerif, fontWeight = FontWeight.Light)
            Spacers()

            Text(detalle, fontSize = 10.sp,fontFamily = FontFamily.SansSerif, fontWeight = FontWeight.Light)
            BlockSpace()
            Spacers()

        }
    }

}
