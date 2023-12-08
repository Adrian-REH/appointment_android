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
import coil.compose.rememberImagePainter
import app.ibiocd.appointment.R
import app.ibiocd.appointment.model.Appointment
import app.ibiocd.appointment.model.Files
import app.ibiocd.appointment.presentation.normalized.ImageLoading
import app.ibiocd.appointment.presentation.normalized.Spacers
import com.valentinilk.shimmer.shimmer



@Composable
fun CardForm(appointment: Appointment, files: Files, lastname:String, onOpenAppointment: (() -> Unit)? = null){
    Card(
        shape = RoundedCornerShape(30.dp),
        elevation = 10.dp,
        modifier = Modifier
            .padding(horizontal = 5.dp, vertical = 10.dp)
            .width(IntrinsicSize.Min)//170.dp aprox 80%
            .height(170.dp)// 100% de 170
    ) {


        Row(modifier = Modifier
            .padding(horizontal = 20.dp, vertical = 20.dp)
            .height(IntrinsicSize.Min)) {

            Column(modifier = Modifier
                .fillMaxHeight())
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
                Text(lastname, modifier = Modifier.width(70.dp),fontSize = 14.sp, fontFamily = FontFamily.SansSerif, fontWeight = FontWeight.Medium)

            }
            Spacers()//10
            Divider(
                color= Color.LightGray,
                modifier = Modifier
                    .fillMaxHeight(1f)
                    .width(1.dp)
            )
            Spacers()//10
            Column(modifier = Modifier
                .fillMaxHeight()
                .weight(1f)
                .wrapContentWidth(Alignment.End)
                .width(40.dp)
                .fillMaxHeight())
            {

                Text("${appointment.fecha}", fontSize = 10.sp,fontFamily = FontFamily.SansSerif, fontWeight = FontWeight.Light)
                Text("${appointment.hora}", fontSize = 10.sp,fontFamily = FontFamily.SansSerif, fontWeight = FontWeight.Light)


                IconButton(onClick = { onOpenAppointment?.invoke() }, modifier = Modifier
                    .size(40.dp)
                    .clip(CircleShape)
                    .weight(1f)
                    .wrapContentHeight(Alignment.Bottom)
                    .wrapContentWidth(Alignment.End)) {
                    Icon( painterResource(id = R.drawable.ic_user_edit), contentDescription ="" , modifier = Modifier.size(30.dp), tint = Color.Gray)

                }


            }

        }
    }
}



@Preview(name = "Android greeting")
@Composable
fun LoadingCardForm(){
    Card(
        shape = RoundedCornerShape(30.dp),
        elevation = 10.dp,
        modifier = Modifier
            .padding(horizontal = 5.dp, vertical = 10.dp)
            .width(IntrinsicSize.Min)//170.dp aprox 80%
            .height(170.dp)// 100% de 170
    ) {


        Row(modifier = Modifier
            .padding(horizontal = 20.dp, vertical = 20.dp)
            .height(IntrinsicSize.Min)) {

            Column(modifier = Modifier
                .fillMaxHeight())
            {

                ImageLoading()


                Spacers()//10
                Box(
                    modifier = Modifier
                        .height(15.dp)
                        .width(70.dp)
                        .shimmer()
                        .background(Color.LightGray)
                        .wrapContentWidth(Alignment.End)
                )
                Spacers()//10

                Box(
                    modifier = Modifier
                        .height(15.dp)
                        .width(40.dp)
                        .shimmer()
                        .background(Color.LightGray)
                        .wrapContentWidth(Alignment.End)
                )


            }
            Spacers()//10
            Divider(
                color= Color.LightGray,
                modifier = Modifier
                    .fillMaxHeight(1f)
                    .width(1.dp)
            )
            Spacers()//10
            Column(modifier = Modifier
                .fillMaxHeight()
                .weight(1f)
                .wrapContentWidth(Alignment.End)
                .width(40.dp)
                .fillMaxHeight())
            {


                Box(
                    modifier = Modifier
                        .height(15.dp)
                        .width(40.dp)
                        .shimmer()
                        .background(Color.LightGray)
                        .wrapContentWidth(Alignment.End)
                )

                IconButton(onClick = { /*TODO*/ }, modifier = Modifier
                    .size(40.dp)
                    .clip(CircleShape)
                    .weight(1f)
                    .wrapContentHeight(Alignment.Bottom)
                    .wrapContentWidth(Alignment.End)) {

                    ImageLoading()
                }


            }

        }
    }
}

