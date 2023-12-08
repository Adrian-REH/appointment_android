package app.ibiocd.appointment.presentation.screens.profile

import android.app.Activity
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
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
import app.ibiocd.appointment.R
import app.ibiocd.appointment.profile.datasource.entity.Medical
import coil.compose.rememberImagePainter
import app.ibiocd.appointment.presentation.components.profile.Check
import app.ibiocd.appointment.presentation.normalized.BlockSpace
import app.ibiocd.appointment.presentation.normalized.Spacers
import app.ibiocd.appointment.profile.viewmodel.MedicalViewModel
import coil.annotation.ExperimentalCoilApi

@OptIn(ExperimentalCoilApi::class)
@Composable
fun EditMedical(
    ID:String,
    resetdni: () -> Unit,
    resetemail: () -> Unit,
    resettuition: () -> Unit,
    resetphone: () -> Unit,
    resetlocation: () -> Unit,
    resetname: () -> Unit,
    resetprofession: () -> Unit,
    Schedules: () -> Unit,
    Specialty: () -> Unit,
    resetimage: () -> Unit,
    medicalViewModel: MedicalViewModel = hiltViewModel()
) {
    val medicals by medicalViewModel.medicals.observeAsState(arrayListOf())
    val activity = (LocalContext.current as? Activity)


    val medical= medicals.find { it._id==ID }?: Medical("","","","","","","",":","","","")


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
                            resetname.invoke()
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
                        .clip(CircleShape).clickable { resetimage.invoke() },
                    painter = rememberImagePainter(
                        data = medical.img,
                        builder = {
                            placeholder(R.drawable.placeholder)
                            error(R.drawable.placeholder)
                        }
                    ),
                    contentDescription = null,
                    contentScale = ContentScale.FillHeight
                )
                Spacers()
                Column {
                    Text(
                        "Hola ",
                        fontSize = 24.sp,
                        fontFamily = FontFamily.SansSerif,
                        fontWeight = FontWeight.Normal
                    )
                    Text(
                        medical.name_last,
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
                Column {
                    Text("Informacion",fontSize = 20.sp, fontFamily = FontFamily.SansSerif, fontWeight = FontWeight.Normal)
                    Spacers()
                    Text("Informacion",fontSize = 12.sp, fontFamily = FontFamily.SansSerif, fontWeight = FontWeight.Normal)

                }
            }

            Box(modifier = Modifier
                .height(IntrinsicSize.Min)
                .fillMaxWidth()
                .clickable {
                    resetdni.invoke()
                })
            {
                Row(verticalAlignment = Alignment.CenterVertically, modifier = Modifier.padding(start = 10.dp)) {
                    IconButton(onClick = resetdni, modifier = Modifier.wrapContentWidth(Alignment.End)) {
                        Icon( painterResource(id = R.drawable.ic_person), contentDescription = "", tint = Color.Gray)
                    }
                    Spacers( )
                    Column {
                        Text("DNI",fontSize = 16.sp, fontFamily = FontFamily.SansSerif, fontWeight = FontWeight.Normal)
                        Text(medical.dni,fontSize = 10.sp, fontFamily = FontFamily.SansSerif, fontWeight = FontWeight.Normal, color = Color.Gray)

                    }

                    IconButton(onClick =  resetdni , modifier = Modifier
                        .weight(1f)
                        .wrapContentWidth(Alignment.End)) {
                        Icon( painterResource(id = R.drawable.ic_edit), contentDescription = "", tint = Color.Gray)
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
                    resetemail.invoke()
                })
            {
                Row(verticalAlignment = Alignment.CenterVertically, modifier = Modifier.padding(start = 10.dp)) {
                    IconButton(onClick = resetemail, modifier = Modifier.wrapContentWidth(Alignment.End)) {
                        Icon( painterResource(id = R.drawable.ic_email), contentDescription = "", tint = Color.Gray)
                    }
                    Spacers( )
                    Column {
                        Text("Email",fontSize = 16.sp, fontFamily = FontFamily.SansSerif, fontWeight = FontWeight.Normal)
                        Text(medical.email,fontSize = 10.sp, fontFamily = FontFamily.SansSerif, fontWeight = FontWeight.Normal, color = Color.Gray)

                    }

                    IconButton(onClick =  resetemail , modifier = Modifier
                        .weight(1f)
                        .wrapContentWidth(Alignment.End)) {
                        Icon( painterResource(id = R.drawable.ic_edit), contentDescription = "", tint = Color.Gray)
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
                    resetphone.invoke()
                })
            {
                Row(verticalAlignment = Alignment.CenterVertically, modifier = Modifier.padding(start = 10.dp)) {
                    IconButton(onClick = resetphone, modifier = Modifier.wrapContentWidth(Alignment.End)) {
                        Icon( painterResource(id = R.drawable.ic_phone), contentDescription = "", tint = Color.Gray)
                    }
                    Spacers( )
                    Column {
                        Text("Phone",fontSize = 16.sp, fontFamily = FontFamily.SansSerif, fontWeight = FontWeight.Normal)
                        Text("+54" + medical.phone,fontSize = 10.sp, fontFamily = FontFamily.SansSerif, fontWeight = FontWeight.Normal, color = Color.Gray)

                    }

                    IconButton(onClick =  resetphone , modifier = Modifier
                        .weight(1f)
                        .wrapContentWidth(Alignment.End)) {
                        Icon( painterResource(id = R.drawable.ic_edit), contentDescription = "", tint = Color.Gray)
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
                    resetphone.invoke()
                })
            {
                Row(verticalAlignment = Alignment.CenterVertically, modifier = Modifier.padding(start = 10.dp)) {
                    IconButton(onClick = resetphone, modifier = Modifier.wrapContentWidth(Alignment.End)) {
                        Icon( painterResource(id = R.drawable.ic_map), contentDescription = "", tint = Color.Gray)
                    }
                    Spacers( )
                    Column {
                        Text("Location",fontSize = 16.sp, fontFamily = FontFamily.SansSerif, fontWeight = FontWeight.Normal)
                        if (medical.direction!=""){
                            Text(medical.direction.split(":")[2],fontSize = 10.sp, fontFamily = FontFamily.SansSerif, fontWeight = FontWeight.Normal, color = Color.Gray)

                        }
                    }

                    IconButton(onClick =  resetlocation , modifier = Modifier
                        .weight(1f)
                        .wrapContentWidth(Alignment.End)) {
                        Icon( painterResource(id = R.drawable.ic_edit), contentDescription = "", tint = Color.Gray)
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
                    resetprofession.invoke()
                })
            {
                Row(verticalAlignment = Alignment.CenterVertically, modifier = Modifier.padding(start = 10.dp)) {
                    IconButton(onClick = resetprofession, modifier = Modifier.wrapContentWidth(Alignment.End)) {
                        Icon( painterResource(id = R.drawable.ic_briefcase), contentDescription = "", tint = Color.Gray)
                    }
                    Spacers( )
                    Column {
                        Text("Profession",fontSize = 16.sp, fontFamily = FontFamily.SansSerif, fontWeight = FontWeight.Normal)
                        Text(medical.profession,fontSize = 10.sp, fontFamily = FontFamily.SansSerif, fontWeight = FontWeight.Normal, color = Color.Gray)

                    }

                    IconButton(onClick =  resetprofession , modifier = Modifier
                        .weight(1f)
                        .wrapContentWidth(Alignment.End)) {
                        Icon( painterResource(id = R.drawable.ic_edit), contentDescription = "", tint = Color.Gray)
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


                    resettuition.invoke()
                })
            {
                Row(verticalAlignment = Alignment.CenterVertically, modifier = Modifier.padding(start = 10.dp)) {
                    IconButton(onClick = resettuition, modifier = Modifier.wrapContentWidth(Alignment.End)) {
                        Icon( painterResource(id = R.drawable.ic_medical), contentDescription = "", tint = Color.Gray)
                    }
                    Spacers( )
                    Column {
                        Text("Tuition",fontSize = 16.sp, fontFamily = FontFamily.SansSerif, fontWeight = FontWeight.Normal)
                        Text(medical.tuition.split(":")[0],fontSize = 10.sp, fontFamily = FontFamily.SansSerif, fontWeight = FontWeight.Normal, color = Color.Gray)

                    }

                    IconButton(onClick =  resettuition , modifier = Modifier
                        .weight(1f)
                        .wrapContentWidth(Alignment.End)) {
                        Icon( painterResource(id = R.drawable.ic_edit), contentDescription = "", tint = Color.Gray)
                    }
                }


            }

            Divider(modifier = Modifier
                .fillMaxWidth()
                .height(1.dp)
                .padding(horizontal = 20.dp))

            Spacers()
            if (medical.tuition.split(":")[1]=="f"){
                Check()
            }

            BlockSpace()
            Card(
                shape = RoundedCornerShape(100.dp),
                elevation = 10.dp,
                modifier = Modifier
                    .padding(horizontal = 20.dp, vertical = 5.dp)
                    .fillMaxWidth()
                    .wrapContentWidth(Alignment.CenterHorizontally)
            ) {
                Column {
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
                        IconButton(onClick = { Specialty.invoke() }, modifier = Modifier
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
    }

}
