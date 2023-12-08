package app.ibiocd.appointment.presentation.screens.Home

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.rememberImagePainter
import app.ibiocd.appointment.presentation.normalized.BlockSpace
import app.ibiocd.appointment.presentation.normalized.Spacers
import app.ibiocd.appointment.R

@Composable
fun Notification(

) {
    LazyColumn(modifier = Modifier
        .fillMaxSize()
        .padding(bottom = 60.dp)
        .background(Color.White))
    {
        item {
            Column(modifier = Modifier.fillMaxSize()) {


                Spacers()
                BlockSpace()
                Row(modifier = Modifier
                    .fillMaxWidth()
                    .height(60.dp)
                )
                {
                    BlockSpace()
                    Image(
                        modifier = Modifier
                            .size(60.dp)
                            .clip(CircleShape),
                        painter = rememberImagePainter(
                            data = "user.thumbnail",
                            builder = {
                                placeholder(R.drawable.placeholder)
                                error(R.drawable.placeholder)
                            }
                        ),
                        contentDescription = null,
                        contentScale = ContentScale.FillHeight
                    )
                    Spacers()
                    Column() {
                        Text("Hola ",fontSize = 24.sp, fontFamily = FontFamily.SansSerif, fontWeight = FontWeight.Normal)
                        Text("Adrian!", fontSize = 24.sp,fontFamily = FontFamily.SansSerif, fontWeight = FontWeight.Medium)

                    }
                }
                Spacers()
                BlockSpace()
                Text("Cuenta",fontSize = 20.sp, fontFamily = FontFamily.SansSerif, fontWeight = FontWeight.Normal, modifier = Modifier.padding(start = 20.dp))
                Spacers()

                Box(modifier = Modifier
                    .height(IntrinsicSize.Min)
                    .fillMaxWidth()
                    .clickable {

                    })
                {
                    Row(verticalAlignment = Alignment.CenterVertically, modifier = Modifier.padding(start = 10.dp)) {
                        IconButton(onClick = { /*TODO*/ }, modifier = Modifier.wrapContentWidth(Alignment.End)) {
                            Icon( painterResource(id = R.drawable.ic_favorite_border), contentDescription = "", tint = Color.Gray)
                        }
                        Spacers( )
                        Text("Favoritos",fontSize = 16.sp, fontFamily = FontFamily.SansSerif, fontWeight = FontWeight.Normal)

                        IconButton(onClick = { /*TODO*/ }, modifier = Modifier
                            .weight(1f)
                            .wrapContentWidth(Alignment.End)) {
                            Icon( painterResource(id = R.drawable.ic_forward), contentDescription = "", tint = Color.Gray)
                        }
                    }


                }

            }

        }

    }

}
