package app.ibiocd.appointment.presentation.components.profile

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import app.ibiocd.appointment.presentation.normalized.BlockSpace
import app.ibiocd.appointment.presentation.normalized.Spacers
import app.ibiocd.appointment.R

@Composable
fun Service( Schedules: () -> Unit){
    val context= LocalContext.current
    Card(
        shape = RoundedCornerShape(100.dp),
        elevation = 10.dp,
        modifier = Modifier
            .padding(horizontal = 20.dp, vertical = 5.dp)
            .fillMaxWidth()
            .wrapContentWidth(Alignment.CenterHorizontally)
    ) {
        Column() {
            Spacers()
            Row {
                Spacers()
                BlockSpace()
                IconButton(onClick = {
                        Schedules.invoke()}, modifier = Modifier
                    .size(40.dp)
                    .clip(CircleShape)) {
                    Icon( painterResource(id = R.drawable.ic_clock), contentDescription ="" , modifier = Modifier.size(25.dp), tint = Color.Gray)

                }
                BlockSpace()

                BlockSpace()

                IconButton(onClick = { Schedules.invoke() }, modifier = Modifier
                    .size(40.dp)
                    .clip(CircleShape)) {
                    Icon( painterResource(id = R.drawable.ic_offer), contentDescription ="" , modifier = Modifier.size(25.dp), tint = Color.Gray)

                }
                BlockSpace()

                BlockSpace()
                IconButton(onClick = { Schedules }, modifier = Modifier
                    .size(40.dp)
                    .clip(CircleShape)) {
                    Icon( painterResource(id = R.drawable.ic_briefcase), contentDescription ="" , modifier = Modifier.size(25.dp), tint = Color.Gray)

                }
                BlockSpace()
                Spacers()

            }
            Spacers()

        }

    }
}