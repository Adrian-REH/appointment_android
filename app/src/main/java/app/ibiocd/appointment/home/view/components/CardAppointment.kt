package app.ibiocd.appointment.home.view.components

import android.content.Intent
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
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import app.ibiocd.appointment.R
import coil.compose.rememberImagePainter
import app.ibiocd.appointment.model.Appointment
import app.ibiocd.appointment.presentation.normalized.BlockSpace
import app.ibiocd.appointment.presentation.normalized.ImageLoading
import app.ibiocd.appointment.presentation.normalized.SimpleSpacers
import app.ibiocd.appointment.presentation.normalized.Spacers
import com.valentinilk.shimmer.shimmer


@Composable
fun CardAppointment(appointment: Appointment, img:String, Name_Last:String, onOpenClick: (() -> Unit)? = null){
    val context = LocalContext.current

    Card(
        shape = RoundedCornerShape(30.dp),
        elevation = 10.dp,
        modifier = Modifier
            .padding(horizontal = 5.dp, vertical = 10.dp)
            .width(IntrinsicSize.Min)//170.dp aprox 80%
            .height(IntrinsicSize.Min)// 100% de 170
            .testTag("card_appointment")
    ) {
        Column(modifier = Modifier
            .padding(horizontal = 20.dp, vertical = 30.dp)
            .height(IntrinsicSize.Min)) {
            Row()
            {
                Column()
                {

                    Image(
                        modifier = Modifier
                            .size(40.dp)
                            .clip(CircleShape),
                        painter = rememberImagePainter(
                            data = img,
                            builder = {
                                placeholder(R.drawable.placeholder)
                                error(R.drawable.placeholder)
                            }
                        ),
                        contentDescription = null,
                        contentScale = ContentScale.FillHeight
                    )

                    Spacers()//10
                    Text(Name_Last, modifier = Modifier.width(70.dp),fontSize = 14.sp, fontFamily = FontFamily.SansSerif, fontWeight = FontWeight.Medium)
                    SimpleSpacers()


                }
                Spacers()
                Column(
                    )
                {

                    Text(appointment.fecha, fontSize = 12.sp,fontFamily = FontFamily.SansSerif, fontWeight = FontWeight.Normal)

                    Text(appointment.hora, modifier = Modifier.width(40.dp), fontSize = 12.sp,fontFamily = FontFamily.SansSerif, fontWeight = FontWeight.Light)
                }

            }
            Spacers()
            BlockSpace()
            Button(onClick = {

                onOpenClick?.invoke()
            },modifier = Modifier
                .weight(1f)
                .fillMaxWidth()
                .wrapContentHeight(Alignment.Bottom)) {
                Text("See")
            }
        }

    }
}



@Preview(name = "Android greeting")
@Composable
fun LoadingCardAppointment(){
    Card(
        shape = RoundedCornerShape(30.dp),
        elevation = 10.dp,
        modifier = Modifier
            .padding(horizontal = 5.dp, vertical = 10.dp)
            .width(IntrinsicSize.Min)//170.dp aprox 80%
            .height(IntrinsicSize.Min)// 100% de 170
    ) {
        Column(modifier = Modifier
            .padding(horizontal = 20.dp, vertical = 30.dp)
            .height(IntrinsicSize.Min)) {
            Row()
            {
                Column()
                {


                    IconButton(onClick = { /*TODO*/ }, modifier = Modifier
                        .size(40.dp)
                        .clip(CircleShape)) {
                        ImageLoading()
                    }

                    BlockSpace()//10
                     Box(
                            modifier = Modifier
                                .height(15.dp)
                                .width(60.dp)
                                .shimmer()
                                .background(Color.LightGray)
                                .wrapContentWidth(Alignment.End)
                            )
                    Box(
                        modifier = Modifier
                            .height(15.dp)
                            .width(60.dp)
                            .shimmer()
                            .background(Color.LightGray)
                            .wrapContentWidth(Alignment.End)
                    )
                    SimpleSpacers()
                    Box(
                        modifier = Modifier
                            .height(15.dp)
                            .width(60.dp)
                            .shimmer()
                            .background(Color.LightGray)
                            .wrapContentWidth(Alignment.End)
                    )

                }
                Spacers()
                Column(
                )
                {

                    Box(
                        modifier = Modifier
                            .height(15.dp)
                            .width(50.dp)
                            .shimmer()
                            .background(Color.LightGray)
                            .wrapContentWidth(Alignment.End)
                    )
                    Box(
                        modifier = Modifier
                            .height(15.dp)
                            .width(50.dp)
                            .shimmer()
                            .background(Color.LightGray)
                            .wrapContentWidth(Alignment.End)
                    )
                }

            }
            SimpleSpacers()//5
            Spacers()//10
            BlockSpace()//20

            //
            Button(onClick = {  },modifier = Modifier
                .weight(1f)
                .fillMaxWidth()
                .wrapContentHeight(Alignment.Bottom)) {
                Box(
                    modifier = Modifier
                        .height(15.dp)
                        .width(40.dp)
                        .shimmer()
                        .background(Color.LightGray)
                )
            }

        }

    }
}

