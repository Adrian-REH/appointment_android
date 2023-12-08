package app.ibiocd.appointment.home.view.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
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
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import app.ibiocd.appointment.R
import app.ibiocd.appointment.model.Appointment
import app.ibiocd.appointment.model.Files
import coil.compose.rememberImagePainter
import app.ibiocd.appointment.presentation.normalized.BlockSpace
import app.ibiocd.appointment.presentation.normalized.ImageLoading
import app.ibiocd.appointment.presentation.normalized.Spacers
import com.valentinilk.shimmer.shimmer


@Composable
fun CardStudiAppointment(
    appointment: Appointment,
    file: Files,
    las_name: String,
    onOpenAppointment: (() -> Unit)? = null){
    Card(
        shape = RoundedCornerShape(30.dp),
        elevation = 10.dp,
        modifier = Modifier
            .padding(horizontal = 20.dp, vertical = 5.dp)
            .fillMaxWidth()
    ) {


        Row(modifier = Modifier
            .padding(8.dp)
            .height(IntrinsicSize.Min)
            .padding(horizontal =20.dp, vertical = 20.dp)) {

            Column()
            {

                IconButton(onClick = { /*TODO*/ }, modifier = Modifier
                    .size(40.dp)
                    .clip(CircleShape)) {
                    Icon( painterResource(id = R.drawable.ic_bookmarks), contentDescription ="Studi" , modifier = Modifier.size(25.dp), tint = Color.Gray)

                }
                Spacers()
                IconButton(onClick = { /*TODO*/ }, modifier = Modifier
                    .size(40.dp)
                    .clip(CircleShape)) {
                    Icon( painterResource(id = R.drawable.ic_appointment_calendar), contentDescription ="Appointment" , modifier = Modifier.size(25.dp), tint = Color.Gray)

                }

            }
            BlockSpace()
            Divider(
                color= Color.LightGray,
                modifier = Modifier
                    .fillMaxHeight(1f)
                    .width(1.dp)
            )
            BlockSpace()
            Column(modifier = Modifier
                .fillMaxHeight()
            )
            {

                Image(
                    modifier = Modifier
                        .size(40.dp)
                        .clip(CircleShape),
                    painter = rememberImagePainter(
                        data = "",
                        builder = {
                            placeholder(R.drawable.placeholder)
                            error(R.drawable.placeholder)
                        }
                    ),
                    contentDescription = null,
                    contentScale = ContentScale.FillHeight
                )

                Spacers()//10

                    Text(las_name, modifier = Modifier.width(80.dp),fontSize = 14.sp, fontFamily = FontFamily.SansSerif, fontWeight = FontWeight.Medium)


            }
            Spacers()
            Column(modifier = Modifier
                .fillMaxHeight()
                .weight(1f)
                .wrapContentWidth(Alignment.End))
            {

                Text(appointment.fecha, modifier = Modifier.width(80.dp),fontSize = 10.sp, fontFamily = FontFamily.SansSerif, fontWeight = FontWeight.Light)
                Text("${appointment.hora}", modifier = Modifier.width(80.dp), fontSize = 10.sp,fontFamily = FontFamily.SansSerif, fontWeight = FontWeight.Light, color = Color.Gray)



                Button(onClick = {onOpenAppointment?.invoke()  },modifier = Modifier
                    .weight(1f)
                    .wrapContentHeight(Alignment.Bottom)) {
                    Text("See")
                }

            }

        }
    }
}


@Preview(name = "Card Simple Appointment")
@Composable
fun LoadingCardStudiAppointment() {
    Card(
        shape = RoundedCornerShape(30.dp),
        elevation = 10.dp,
        modifier = Modifier
            .padding(horizontal = 20.dp, vertical = 5.dp)
            .fillMaxWidth()
    ) {


        Row(modifier = Modifier
            .padding(8.dp)
            .height(IntrinsicSize.Min)
            .padding(horizontal = 20.dp, vertical = 20.dp)) {

            Column()
            {



                IconButton(onClick = { /*TODO*/ }, modifier = Modifier
                    .size(40.dp)
                    .clip(CircleShape)) {
                    ImageLoading()

                }
                Spacers()
                IconButton(onClick = { /*TODO*/ }, modifier = Modifier
                    .size(40.dp)
                    .clip(CircleShape)) {
                    ImageLoading()

                }

            }
            BlockSpace()
            Divider(
                color= Color.LightGray,
                modifier = Modifier
                    .fillMaxHeight(1f)
                    .width(1.dp)
            )
            BlockSpace()
            Column(modifier = Modifier
                .fillMaxHeight()
            )
            {

                IconButton(onClick = { /*TODO*/ }, modifier = Modifier
                    .size(40.dp)
                    .clip(CircleShape)) {
                    ImageLoading()

                }


                Spacers()//10
                Box(
                    modifier = Modifier
                        .height(13.dp)
                        .width(80.dp)
                        .shimmer()
                        .background(Color.Gray)
                )
                Spacers()//10
                Box(
                    modifier = Modifier
                        .height(13.dp)
                        .width(40.dp)
                        .shimmer()
                        .background(Color.Gray)
                )

            }
            Spacers()
            Column(modifier = Modifier
                .fillMaxHeight()
                .weight(1f)
                .wrapContentWidth(Alignment.End))
            {
                Box(
                    modifier = Modifier
                        .height(13.dp)
                        .width(80.dp)
                        .shimmer()
                        .background(Color.Gray)
                        .wrapContentWidth(Alignment.End)
                )
                Spacers()//10

                Box(
                    modifier = Modifier
                        .height(13.dp)
                        .width(40.dp)
                        .shimmer()
                        .background(Color.LightGray)
                        .wrapContentWidth(Alignment.End)
                )


                Button(onClick = {  },modifier = Modifier
                    .weight(1f)
                    .wrapContentHeight(Alignment.Bottom)
                    .wrapContentWidth(Alignment.End)) {
                    Box(
                        modifier = Modifier
                            .height(13.dp)
                            .width(40.dp)
                            .shimmer()
                            .background(Color.LightGray)
                    )
                }

            }

        }
    }
}

