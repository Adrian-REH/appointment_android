package com.nopalsoft.simple.rest.presentation

import android.app.Activity
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import app.ibiocd.appointment.R
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import coil.compose.rememberImagePainter
import com.google.accompanist.pager.ExperimentalPagerApi
import com.google.accompanist.pager.rememberPagerState
import app.ibiocd.appointment.model.Patient
import app.ibiocd.appointment.presentation.components.profile.Check
import app.ibiocd.appointment.presentation.normalized.BlockSpace
import app.ibiocd.appointment.presentation.normalized.Spacers
import app.ibiocd.appointment.profile.viewmodel.PatientViewModel

@OptIn(ExperimentalPagerApi::class)
@Composable
fun ResumenPatient(    ID:String,    patientViewModel: PatientViewModel = hiltViewModel()


) {
    val pagerState = rememberPagerState(pageCount = 4)
    val activity = (LocalContext.current as? Activity)

    val context= LocalContext.current
    val patients by patientViewModel.patients.observeAsState(arrayListOf())


    val patient= patients.find { it._id==ID } ?: Patient("","","","","","","","")

    Scaffold(
        topBar = {
            Column(
                modifier = Modifier
                    .background(Color.White)

            ) {

                TopAppBar(
                    title = {  },
                    navigationIcon ={
                        IconButton(onClick = {
                            activity?.finish()


                        }) {
                            Icon( painter = painterResource(R.drawable.ic_arrow_back), contentDescription = "", modifier = Modifier.size(20.dp))
                        }
                    },
                    backgroundColor = Color.White,
                    elevation = 0.dp,
                    actions = {

                        IconButton(onClick = {
                            // context.startActivity(Intent(context, SessionActivity::class.java))
                        },) {
                            Icon(  painter = painterResource(R.drawable.ic_edit), contentDescription = "", modifier = Modifier.size(20.dp))

                        }
                    }
                )



            }

        }
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth(), horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Row(
                modifier = Modifier
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
                        data = patient.img,
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
                    Text(
                        "Hola ",
                        fontSize = 24.sp,
                        fontFamily = FontFamily.SansSerif,
                        fontWeight = FontWeight.Normal
                    )
                    Text(
                        patient.name_last,
                        fontSize = 24.sp,
                        fontFamily = FontFamily.SansSerif,
                        fontWeight = FontWeight.Medium
                    )

                }


            }
            BlockSpace()

            Divider(modifier = Modifier
                .fillMaxWidth()
                .height(1.dp)
                .padding(horizontal = 20.dp))
            BlockSpace()

            Box(modifier = Modifier
                .height(IntrinsicSize.Min)
                .fillMaxWidth()
                .padding(horizontal = 20.dp)) {
                Column() {
                    Text("Informacion",fontSize = 20.sp, fontFamily = FontFamily.SansSerif, fontWeight = FontWeight.Normal)
                    Spacers()
                    Text("Informacion",fontSize = 12.sp, fontFamily = FontFamily.SansSerif, fontWeight = FontWeight.Normal)

                }
            }

            Box(modifier = Modifier
                .height(IntrinsicSize.Min)
                .fillMaxWidth()
                .clickable {

                })
            {
                Row(verticalAlignment = Alignment.CenterVertically, modifier = Modifier.padding(start = 10.dp)) {
                    IconButton(onClick = { /*TODO*/ }, modifier = Modifier.wrapContentWidth(Alignment.End)) {
                        Icon( painterResource(id = R.drawable.ic_person), contentDescription = "", tint = Color.Gray)
                    }
                    Spacers( )
                    Column() {
                        Text("DNI",fontSize = 16.sp, fontFamily = FontFamily.SansSerif, fontWeight = FontWeight.Normal)
                        Text(patient.dni,fontSize = 10.sp, fontFamily = FontFamily.SansSerif, fontWeight = FontWeight.Normal, color = Color.Gray)

                    }


                }


            }

            Divider(modifier = Modifier
                .fillMaxWidth()
                .height(1.dp)
                .padding(horizontal = 20.dp))
            Box(modifier = Modifier
                .height(IntrinsicSize.Min)
                .fillMaxWidth()
                .clickable {

                })
            {
                Row(verticalAlignment = Alignment.CenterVertically, modifier = Modifier.padding(start = 10.dp)) {
                    IconButton(onClick = { /*TODO*/ }, modifier = Modifier.wrapContentWidth(Alignment.End)) {
                        Icon( painterResource(id = R.drawable.ic_email), contentDescription = "", tint = Color.Gray)
                    }
                    Spacers( )
                    Column() {
                        Text("Email",fontSize = 16.sp, fontFamily = FontFamily.SansSerif, fontWeight = FontWeight.Normal)
                        Text(patient.email,fontSize = 10.sp, fontFamily = FontFamily.SansSerif, fontWeight = FontWeight.Normal, color = Color.Gray)

                    }


                }


            }

            Divider(modifier = Modifier
                .fillMaxWidth()
                .height(1.dp)
                .padding(horizontal = 20.dp))

            Box(modifier = Modifier
                .height(IntrinsicSize.Min)
                .fillMaxWidth()
                .clickable {

                })
            {
                Row(verticalAlignment = Alignment.CenterVertically, modifier = Modifier.padding(start = 10.dp)) {
                    IconButton(onClick = { /*TODO*/ }, modifier = Modifier.wrapContentWidth(Alignment.End)) {
                        Icon( painterResource(id = R.drawable.ic_phone), contentDescription = "", tint = Color.Gray)
                    }
                    Spacers( )
                    Column() {
                        Text("Phone",fontSize = 16.sp, fontFamily = FontFamily.SansSerif, fontWeight = FontWeight.Normal)
                        Text("+54 ${patient.phone}",fontSize = 10.sp, fontFamily = FontFamily.SansSerif, fontWeight = FontWeight.Normal, color = Color.Gray)

                    }


                }


            }

            Divider(modifier = Modifier
                .fillMaxWidth()
                .height(1.dp)
                .padding(horizontal = 20.dp))


            Box(modifier = Modifier
                .height(IntrinsicSize.Min)
                .fillMaxWidth()
                .clickable {

                })
            {
                Row(verticalAlignment = Alignment.CenterVertically, modifier = Modifier.padding(start = 10.dp)) {
                    IconButton(onClick = { /*TODO*/ }, modifier = Modifier.wrapContentWidth(Alignment.End)) {
                        Icon( painterResource(id = R.drawable.ic_map), contentDescription = "", tint = Color.Gray)
                    }
                    Spacers( )
                    Column() {
                        Text("Location",fontSize = 16.sp, fontFamily = FontFamily.SansSerif, fontWeight = FontWeight.Normal)
                        if (patient.direction!=""){
                            Text(patient.direction.split(":")[2],fontSize = 10.sp, fontFamily = FontFamily.SansSerif, fontWeight = FontWeight.Normal, color = Color.Gray)

                        }
                    }


                }


            }

            Spacers()
            Check()
        }
    }

}
