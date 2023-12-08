package app.ibiocd.appointment.appointment.view.components

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import app.ibiocd.appointment.presentation.normalized.Spacers
import app.ibiocd.appointment.R

@Composable
fun CardFile(title:String, data: String, isOdontogram:Boolean, onDownload:(String)->Unit, onSelect:()->Unit) {
    Card(
        shape = RoundedCornerShape(100.dp),
        elevation = 10.dp,
        modifier = Modifier
            .padding(horizontal = 20.dp, vertical = 5.dp)
            .fillMaxWidth()
    ) {
        Row(verticalAlignment = Alignment.CenterVertically, modifier = Modifier.wrapContentWidth(Alignment.CenterHorizontally)) {
            Spacers()
            Row(verticalAlignment = Alignment.CenterVertically,modifier = Modifier
                .weight(1f)
                .wrapContentWidth(Alignment.Start)) {
                IconButton(onClick = {
                    if (isOdontogram)onSelect.invoke()
                    else if (!isOdontogram && data!="")onDownload.invoke(data)
                    else if (!isOdontogram && data=="") onSelect.invoke()

                }, modifier = Modifier
                    .size(40.dp)
                    .clip(CircleShape)

                    .wrapContentHeight(Alignment.Bottom)) {

                        if (isOdontogram) Icon( painterResource(id = R.drawable.ic_edit), contentDescription ="" , modifier = Modifier.size(30.dp), tint = Color.Gray)
                        else if (!isOdontogram && data!="") Icon( painterResource(id = R.drawable.ic_download), contentDescription ="" , modifier = Modifier.size(30.dp), tint = Color.Gray)
                        else if (!isOdontogram && data=="") Icon( painterResource(id = R.drawable.ic_add_circle), contentDescription ="" , modifier = Modifier.size(30.dp), tint = Color.Gray)


                }
                Spacers()
                Text(title,fontSize = 10.sp, fontFamily = FontFamily.SansSerif, fontWeight = FontWeight.Normal,)

            }


            Spacers()

        }
    }
}