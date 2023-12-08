package com.nopalsoft.simple.rest.presentation

import android.content.Intent
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import app.ibiocd.appointment.R
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.*
import androidx.compose.runtime.Composable
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
import coil.compose.rememberImagePainter
import app.ibiocd.appointment.home.view.HomeActivity
import app.ibiocd.appointment.profile.view.ProfileActivity
import app.ibiocd.appointment.presentation.normalized.BlockSpace
import app.ibiocd.appointment.presentation.normalized.BlockSpaces
import app.ibiocd.appointment.presentation.normalized.Spacers
import com.valentinilk.shimmer.shimmer

@Composable
fun MenuPatient(
    ID: String,
    name: String,
    img: String,
    navegarFile: (String) -> Unit,
    navegarCalendar: () -> Unit,

    ) {
    val context = LocalContext.current

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
                    .height(60.dp))
                {
                    BlockSpace()
                    if (name==""){
                        Box(
                            modifier = Modifier
                                .size(60.dp)
                                .shimmer()
                                .background(Color.LightGray)
                                .wrapContentWidth(Alignment.End)
                                .clip(CircleShape)
                        )
                        Spacers()
                        Column() {
                            Box(
                                modifier = Modifier
                                    .height(15.dp)
                                    .width(100.dp)
                                    .shimmer()
                                    .background(Color.LightGray)
                                    .wrapContentWidth(Alignment.End)
                            )
                            Spacers()
                            Box(
                                modifier = Modifier
                                    .height(15.dp)
                                    .width(120.dp)
                                    .shimmer()
                                    .background(Color.LightGray)
                                    .wrapContentWidth(Alignment.End)
                            )
                        }
                    }else{
                        Image(
                            modifier = Modifier
                                .size(60.dp)
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
                        Spacers()
                        Column() {
                            Text("Hola",fontSize = 20.sp, fontFamily = FontFamily.SansSerif, fontWeight = FontWeight.Normal)
                            Text("${name.split(" ")[0]}!", fontSize = 20.sp,fontFamily = FontFamily.SansSerif, fontWeight = FontWeight.Medium)

                        }
                    }

                    IconButton(onClick = {

                        val intent = Intent(context, ProfileActivity::class.java)
                        intent.putExtra("patients","${true}")
                        intent.putExtra("edit","${true}")
                        intent.putExtra("id",ID)

                        // intent.putExtra("medicals","adfadsf")
                        context.startActivity(intent)
                        val activity: HomeActivity = HomeActivity()
                        // on below line we are finishing activity.
                        activity.finish()

                    }, modifier = Modifier
                        .weight(1f)
                        .wrapContentWidth(Alignment.End)) {
                        Icon( painterResource(id = R.drawable.ic_forward), contentDescription = "", tint = Color.Gray)
                    }
                }
                Spacers()
                BlockSpace()


                //Cuenta()
                Servicios(navegarFile,navegarCalendar)
                Help()
                Legal()



                Spacers()
                BlockSpace()
                Text("Log out",fontSize = 16.sp, fontFamily = FontFamily.SansSerif, fontWeight = FontWeight.Normal, modifier = Modifier.padding(start = 20.dp))
                Spacers()
                BlockSpace()
                Text("Version 1.0.0(44345)",fontSize = 10.sp, fontFamily = FontFamily.SansSerif, fontWeight = FontWeight.Normal, modifier = Modifier.padding(start = 20.dp))
                BlockSpaces()
            }

        }

    }
}

@Composable
fun Help(){
    Text("Ayuda",fontSize = 20.sp, fontFamily = FontFamily.SansSerif, fontWeight = FontWeight.Normal, modifier = Modifier.padding(start = 20.dp))
    Spacers()
    Box(modifier = Modifier
        .height(IntrinsicSize.Min)
        .fillMaxWidth()
        .clickable {

        })
    {
        Row(verticalAlignment = Alignment.CenterVertically, modifier = Modifier.padding(start = 10.dp)) {
            IconButton(onClick = { /*TODO*/ }, modifier = Modifier.wrapContentWidth(Alignment.End)) {
                Icon( painterResource(id = R.drawable.ic_help), contentDescription = "", tint = Color.Gray)
            }
            Spacers( )
            Text("Obtener Ayuda",fontSize = 16.sp, fontFamily = FontFamily.SansSerif, fontWeight = FontWeight.Normal)

            IconButton(onClick = { /*TODO*/ }, modifier = Modifier
                .weight(1f)
                .wrapContentWidth(Alignment.End)) {
                Icon( painterResource(id = R.drawable.ic_forward), contentDescription = "", tint = Color.Gray)
            }
        }


    }
    Divider(modifier = Modifier
        .height(1.dp)
        .fillMaxWidth()
        .padding(horizontal = 20.dp))
    Spacers()
    BlockSpace()
}
@Composable
fun Servicios(    navegarFile: (String) -> Unit,navegarCalendar: () -> Unit
){

    Text("Servicios",fontSize = 20.sp, fontFamily = FontFamily.SansSerif, fontWeight = FontWeight.Normal, modifier = Modifier.padding(start = 20.dp))
    Spacers()

    Box(modifier = Modifier
        .height(IntrinsicSize.Min)
        .fillMaxWidth()
        .clickable {
            navegarFile.invoke("0")
        })
    {
        Row(verticalAlignment = Alignment.CenterVertically, modifier = Modifier.padding(start = 10.dp)) {
            IconButton(onClick = {navegarFile.invoke("0") }, modifier = Modifier.wrapContentWidth(Alignment.End)) {
                Icon( painterResource(id = R.drawable.ic_list_box), contentDescription = "", tint = Color.Gray)
            }
            Spacers( )
            Text("Historial medico",fontSize = 16.sp, fontFamily = FontFamily.SansSerif, fontWeight = FontWeight.Normal)

            IconButton(onClick = { navegarFile.invoke("0") }, modifier = Modifier
                .weight(1f)
                .wrapContentWidth(Alignment.End)) {
                Icon( painterResource(id = R.drawable.ic_forward), contentDescription = "", tint = Color.Gray)
            }
        }


    }
    Divider(modifier = Modifier
        .height(1.dp)
        .fillMaxWidth()
        .padding(horizontal = 20.dp))
    Box(modifier = Modifier
        .height(IntrinsicSize.Min)
        .fillMaxWidth()
        .clickable {
            navegarFile.invoke("1")
        })
    {
        Row(verticalAlignment = Alignment.CenterVertically, modifier = Modifier.padding(start = 10.dp)) {
            IconButton(onClick = { navegarFile.invoke("1") }, modifier = Modifier.wrapContentWidth(Alignment.End)) {
                Icon( painterResource(id = R.drawable.ic_bookmarks), contentDescription = "", tint = Color.Gray)
            }
            Spacers( )
            Text("Estudios",fontSize = 16.sp, fontFamily = FontFamily.SansSerif, fontWeight = FontWeight.Normal)

            IconButton(onClick = { navegarFile.invoke("1") }, modifier = Modifier
                .weight(1f)
                .wrapContentWidth(Alignment.End)) {
                Icon( painterResource(id = R.drawable.ic_forward), contentDescription = "", tint = Color.Gray)
            }
        }


    }
    Divider(modifier = Modifier
        .height(1.dp)
        .fillMaxWidth()
        .padding(horizontal = 20.dp))
    Box(modifier = Modifier
        .height(IntrinsicSize.Min)
        .fillMaxWidth()
        .clickable {
            navegarFile.invoke("2")
        })
    {
        Row(verticalAlignment = Alignment.CenterVertically, modifier = Modifier.padding(start = 10.dp)) {
            IconButton(onClick = { navegarFile.invoke("2") }, modifier = Modifier.wrapContentWidth(Alignment.End)) {
                Icon( painterResource(id = R.drawable.ic_lab), contentDescription = "", tint = Color.Gray)
            }
            Spacers( )
            Text("Laboratorio",fontSize = 16.sp, fontFamily = FontFamily.SansSerif, fontWeight = FontWeight.Normal)

            IconButton(onClick = { navegarFile.invoke("2") }, modifier = Modifier
                .weight(1f)
                .wrapContentWidth(Alignment.End)) {
                Icon( painterResource(id = R.drawable.ic_forward), contentDescription = "", tint = Color.Gray)
            }
        }


    }
    Divider(modifier = Modifier
        .height(1.dp)
        .fillMaxWidth()
        .padding(horizontal = 20.dp))
    Box(modifier = Modifier
        .height(IntrinsicSize.Min)
        .fillMaxWidth()
        .clickable {
            navegarFile.invoke("3")
        })
    {
        Row(verticalAlignment = Alignment.CenterVertically, modifier = Modifier.padding(start = 10.dp)) {
            IconButton(onClick = { navegarFile.invoke("3") }, modifier = Modifier.wrapContentWidth(Alignment.End)) {
                Icon( painterResource(id = R.drawable.ic_paper), contentDescription = "", tint = Color.Gray)
            }
            Spacers( )
            Text("Recetas",fontSize = 16.sp, fontFamily = FontFamily.SansSerif, fontWeight = FontWeight.Normal)

            IconButton(onClick = { navegarFile.invoke("3") }, modifier = Modifier
                .weight(1f)
                .wrapContentWidth(Alignment.End)) {
                Icon( painterResource(id = R.drawable.ic_forward), contentDescription = "", tint = Color.Gray)
            }
        }


    }

    Divider(modifier = Modifier
        .height(1.dp)
        .fillMaxWidth()
        .padding(horizontal = 20.dp))
    Box(modifier = Modifier
        .height(IntrinsicSize.Min)
        .fillMaxWidth()
        .clickable {
            navegarCalendar.invoke()
        })
    {
        Row(verticalAlignment = Alignment.CenterVertically, modifier = Modifier.padding(start = 10.dp)) {
            IconButton(onClick = { navegarCalendar.invoke() }, modifier = Modifier.wrapContentWidth(Alignment.End)) {
                Icon( painterResource(id = R.drawable.ic_appointment_calendar), contentDescription = "", tint = Color.Gray)
            }
            Spacers( )
            Text("Calendario",fontSize = 16.sp, fontFamily = FontFamily.SansSerif, fontWeight = FontWeight.Normal)

            IconButton(onClick = { navegarCalendar.invoke() }, modifier = Modifier
                .weight(1f)
                .wrapContentWidth(Alignment.End)) {
                Icon( painterResource(id = R.drawable.ic_forward), contentDescription = "", tint = Color.Gray)
            }
        }


    }
    Divider(modifier = Modifier
        .height(1.dp)
        .fillMaxWidth()
        .padding(horizontal = 20.dp))

/*    Divider(modifier = Modifier
        .height(1.dp)
        .fillMaxWidth()
        .padding(horizontal = 20.dp))
    Box(modifier = Modifier
        .height(IntrinsicSize.Min)
        .fillMaxWidth()
        .clickable {

        })
    {
        Row(verticalAlignment = Alignment.CenterVertically, modifier = Modifier.padding(start = 10.dp)) {
            IconButton(onClick = { *//*TODO*//* }, modifier = Modifier.wrapContentWidth(Alignment.End)) {
                Icon( painterResource(id = R.drawable.ic_person), contentDescription = "", tint = Color.Gray)
            }
            Spacers( )
            Text("Ser paciente",fontSize = 16.sp, fontFamily = FontFamily.SansSerif, fontWeight = FontWeight.Normal)

            IconButton(onClick = { *//*TODO*//* }, modifier = Modifier
                .weight(1f)
                .wrapContentWidth(Alignment.End)) {
                Icon( painterResource(id = R.drawable.ic_forward), contentDescription = "", tint = Color.Gray)
            }
        }


    }*/
    Divider(modifier = Modifier
        .height(1.dp)
        .fillMaxWidth()
        .padding(horizontal = 20.dp))
    Spacers()
    BlockSpace()
}
@Composable
fun Cuenta(navegarFavs:()->Unit){

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
                Icon( painterResource(id = R.drawable.ic_notifications), contentDescription = "", tint = Color.Gray)
            }
            Spacers( )
            Text("Notificaciones",fontSize = 16.sp, fontFamily = FontFamily.SansSerif, fontWeight = FontWeight.Normal)

            IconButton(onClick = { /*TODO*/ }, modifier = Modifier
                .weight(1f)
                .wrapContentWidth(Alignment.End)) {
                Icon( painterResource(id = R.drawable.ic_forward), contentDescription = "", tint = Color.Gray)
            }
        }


    }
    Divider(modifier = Modifier
        .height(1.dp)
        .fillMaxWidth()
        .padding(horizontal = 20.dp))
    Box(modifier = Modifier
        .height(IntrinsicSize.Min)
        .fillMaxWidth()
        .clickable {
            navegarFavs.invoke()
        })
    {
        Row(verticalAlignment = Alignment.CenterVertically, modifier = Modifier.padding(start = 10.dp)) {
            IconButton(onClick = { navegarFavs.invoke() }, modifier = Modifier.wrapContentWidth(Alignment.End)) {
                Icon( painterResource(id = R.drawable.ic_favorite_border), contentDescription = "", tint = Color.Gray)
            }
            Spacers( )
            Text("Favoritos",fontSize = 16.sp, fontFamily = FontFamily.SansSerif, fontWeight = FontWeight.Normal)

            IconButton(onClick = { navegarFavs.invoke() }, modifier = Modifier
                .weight(1f)
                .wrapContentWidth(Alignment.End)) {
                Icon( painterResource(id = R.drawable.ic_forward), contentDescription = "", tint = Color.Gray)
            }
        }


    }
    Divider(modifier = Modifier
        .height(1.dp)
        .fillMaxWidth()
        .padding(horizontal = 20.dp))
    Box(modifier = Modifier
        .height(IntrinsicSize.Min)
        .fillMaxWidth()
        .clickable {

        })
    {
        Row(verticalAlignment = Alignment.CenterVertically, modifier = Modifier.padding(start = 10.dp)) {
            IconButton(onClick = { /*TODO*/ }, modifier = Modifier.wrapContentWidth(Alignment.End)) {
                Icon( painterResource(id = R.drawable.ic_dashboard), contentDescription = "", tint = Color.Gray)
            }
            Spacers( )
            Text("Resumen",fontSize = 16.sp, fontFamily = FontFamily.SansSerif, fontWeight = FontWeight.Normal)

            IconButton(onClick = { /*TODO*/ }, modifier = Modifier
                .weight(1f)
                .wrapContentWidth(Alignment.End)) {
                Icon( painterResource(id = R.drawable.ic_forward), contentDescription = "", tint = Color.Gray)
            }
        }


    }
    Divider(modifier = Modifier
        .height(1.dp)
        .fillMaxWidth()
        .padding(horizontal = 20.dp))
    Box(modifier = Modifier
        .height(IntrinsicSize.Min)
        .fillMaxWidth()
        .clickable {

        })
    {
        Row(verticalAlignment = Alignment.CenterVertically, modifier = Modifier.padding(start = 10.dp)) {
            IconButton(onClick = { /*TODO*/ }, modifier = Modifier.wrapContentWidth(Alignment.End)) {
                Icon( painterResource(id = R.drawable.ic_lock), contentDescription = "", tint = Color.Gray)
            }
            Spacers( )
            Text("Seguridad",fontSize = 16.sp, fontFamily = FontFamily.SansSerif, fontWeight = FontWeight.Normal)

            IconButton(onClick = { /*TODO*/ }, modifier = Modifier
                .weight(1f)
                .wrapContentWidth(Alignment.End)) {
                Icon( painterResource(id = R.drawable.ic_forward), contentDescription = "", tint = Color.Gray)
            }
        }


    }
    Divider(modifier = Modifier
        .height(1.dp)
        .fillMaxWidth()
        .padding(horizontal = 20.dp))
    Spacers()
    BlockSpace()
}
@Composable
fun Legal(){
    Text("Legal",fontSize = 20.sp, fontFamily = FontFamily.SansSerif, fontWeight = FontWeight.Normal, modifier = Modifier.padding(start = 20.dp))
    Spacers()
    Box(modifier = Modifier
        .height(IntrinsicSize.Min)
        .fillMaxWidth()
        .clickable {

        })
    {
        Row(verticalAlignment = Alignment.CenterVertically, modifier = Modifier.padding(start = 10.dp)) {
            IconButton(onClick = { /*TODO*/ }, modifier = Modifier.wrapContentWidth(Alignment.End)) {
                Icon( painterResource(id = R.drawable.ic_book), contentDescription = "", tint = Color.Gray)
            }
            Spacers( )
            Text("Politica de privacidad",fontSize = 16.sp, fontFamily = FontFamily.SansSerif, fontWeight = FontWeight.Normal)

            IconButton(onClick = { /*TODO*/ }, modifier = Modifier
                .weight(1f)
                .wrapContentWidth(Alignment.End)) {
                Icon( painterResource(id = R.drawable.ic_forward), contentDescription = "", tint = Color.Gray)
            }
        }


    }
    Divider(modifier = Modifier
        .height(1.dp)
        .fillMaxWidth()
        .padding(horizontal = 20.dp))
    Box(modifier = Modifier
        .height(IntrinsicSize.Min)
        .fillMaxWidth()
        .clickable {

        })
    {
        Row(verticalAlignment = Alignment.CenterVertically, modifier = Modifier.padding(start = 10.dp)) {
            IconButton(onClick = { /*TODO*/ }, modifier = Modifier.wrapContentWidth(Alignment.End)) {
                Icon( painterResource(id = R.drawable.ic_book), contentDescription = "", tint = Color.Gray)
            }
            Spacers( )
            Text("Terminos de Servicio",fontSize = 16.sp, fontFamily = FontFamily.SansSerif, fontWeight = FontWeight.Normal)

            IconButton(onClick = { /*TODO*/ }, modifier = Modifier
                .weight(1f)
                .wrapContentWidth(Alignment.End)) {
                Icon( painterResource(id = R.drawable.ic_forward), contentDescription = "", tint = Color.Gray)
            }
        }


    }

}