package com.nopalsoft.simple.rest.presentation

import android.app.Activity
import android.content.Intent
import android.widget.Toast
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Refresh
import androidx.compose.material.icons.filled.Search
import androidx.compose.runtime.*
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import app.ibiocd.appointment.appointment.viewmodel.LabViewModel
import coil.compose.rememberImagePainter
import app.ibiocd.appointment.R
import app.ibiocd.appointment.home.SearchWidgetState
import app.ibiocd.appointment.auth.view.SessionActivity
import app.ibiocd.appointment.appointment.viewmodel.AppointmentViewModel
import app.ibiocd.appointment.appointment.view.AppointmentActivity
import app.ibiocd.appointment.home.navegation.HomeDestinations
import app.ibiocd.appointment.home.view.components.CardAppointment
import app.ibiocd.appointment.home.view.components.CardCreateAppointment
import app.ibiocd.appointment.home.view.components.LoadingCardAppointment
import app.ibiocd.appointment.home.viewmodel.FavViewModel
import app.ibiocd.appointment.presentation.normalized.BlockSpace
import app.ibiocd.appointment.presentation.normalized.Spacers
import app.ibiocd.appointment.profile.viewmodel.MedicalViewModel
import app.ibiocd.appointment.profile.viewmodel.PatientViewModel
import com.valentinilk.shimmer.shimmer
import kotlinx.coroutines.runBlocking


@Composable
fun Home(
    medical: Boolean,
    img: String,
    name: String,
    ID: String,
    navController:NavHostController,
    labViewModel: LabViewModel = hiltViewModel(),
    favViewModel: FavViewModel = hiltViewModel(),
    patientViewModel: PatientViewModel = hiltViewModel(),
    medicalViewModel: MedicalViewModel = hiltViewModel(),
    viewModelappointment: AppointmentViewModel = hiltViewModel()
) {

    val context = LocalContext.current


    val appointment by viewModelappointment.appointment.observeAsState(arrayListOf())
    val state by viewModelappointment.appointmentState.collectAsState(initial = null)

    val favs by favViewModel.favs.observeAsState(arrayListOf())
    val medicals by medicalViewModel.medicals.observeAsState(arrayListOf())
    val laboratorys by labViewModel.laboratory.observeAsState(arrayListOf())
    val patients by patientViewModel.patients.observeAsState(arrayListOf())

    Scaffold(
        topBar = {
            DefaultAppBar(onRefreshClicked = {
                runBlocking {

                     viewModelappointment.getAllAppointment()
                }

            })


        })
    {
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(bottom = 60.dp)
                .background(Color.White)
        ) {
            item {
                Column()
                {

                    Spacers()
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(50.dp)
                    )
                    {
                        BlockSpace()
                        if (name == "") {
                            Box(
                                modifier = Modifier
                                    .size(50.dp)
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
                        } else {
                            Image(
                                modifier = Modifier
                                    .size(50.dp)
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
                                Text(
                                    "Hola",
                                    fontSize = 20.sp,
                                    fontFamily = FontFamily.SansSerif,
                                    fontWeight = FontWeight.Normal,
                                    modifier = Modifier.testTag("greeting"),
                                    )
                                Text(
                                    "${name.split(" ")[0]}!",
                                    fontSize = 20.sp,
                                    fontFamily = FontFamily.SansSerif,
                                    fontWeight = FontWeight.Medium
                                )

                            }
                        }


                    }
                    BlockSpace()
                    Box() {
                        CardCreateAppointment(medical, ID, onSelect = {
                            if (favs.isNotEmpty()) {
                                val intent = Intent(
                                    context,
                                    AppointmentActivity::class.java
                                )
                                intent.putExtra("medicals", "$medical")
                                intent.putExtra("id", ID)
                                intent.putExtra("appointment", "")
                                intent.putExtra("resume", "6")

                                context.startActivity(intent)
                            } else {
                                if (medical) Toast.makeText(
                                    context,
                                    "Ningun paciente lo agrego como favorito",
                                    Toast.LENGTH_SHORT
                                ).show()
                                else Toast.makeText(
                                    context,
                                    "Por favor primero agrege un medico a favorito",
                                    Toast.LENGTH_SHORT
                                ).show()
                            }


                        })
                    }


                }
                if (!medical) {
                    Spacers()
                    BlockSpace()
                    Column(modifier = Modifier.fillMaxWidth()) {
                        Text(
                            "Medicos ",
                            fontSize = 20.sp,
                            fontFamily = FontFamily.SansSerif,
                            fontWeight = FontWeight.Normal,
                            modifier = Modifier.padding(start = 20.dp)
                        )
                        Spacers()

                        LazyRow(modifier = Modifier.fillMaxWidth()) {


                            items(count = 1) { index ->
                                var auxIndex = index;

                                if (auxIndex == 0) {
                                    BlockSpace()
                                    MedicalSearchImage(onSearch = {
                                        navController.navigate(
                                            HomeDestinations.SearchMedical.route
                                        )
                                    })
                                }


                                MedicalImage(1, "Odontologia")


                                //val user = users[auxIndex]

                            }

                        }
                    }

                }


                Spacers()
                BlockSpace()
                Column(modifier = Modifier.fillMaxWidth()) {
                    Text(
                        "Turnos",
                        fontSize = 20.sp,
                        fontFamily = FontFamily.SansSerif,
                        fontWeight = FontWeight.Normal,
                        modifier = Modifier.padding(start = 20.dp)
                    )
                    Spacers()

                    LazyRow {
                        var itemCount2 = appointment.size
                        if (state?.isLoading == true) itemCount2++

                        items(count = itemCount2) { index ->
                            var auxIndex = index;

                            if (auxIndex == 0) {
                                BlockSpace()
                            }

                            if (state?.isLoading == true) {
                                if (auxIndex == 0)
                                    return@items LoadingCardAppointment()
                                auxIndex--
                            }

                            if (itemCount2 != 0) {
                                val appointments = appointment[auxIndex]


                                val datemedical =
                                    medicals.firstOrNull { it._id == appointments.medical }
                                val laboratory =
                                    laboratorys.firstOrNull { it._id == appointments.medical }
                                val datepatient =
                                    patients.firstOrNull { it._id == appointments.patient }

                                if (!medical && appointments.patient == ID) {
                                    if (datemedical != null) {
                                        CardAppointment(
                                            appointments,
                                            datemedical.direction,
                                            datemedical.name_last,
                                            onOpenClick = {
                                                val intent = Intent(
                                                    context,
                                                    AppointmentActivity::class.java
                                                )
                                                intent.putExtra("medicals", "$medical")
                                                intent.putExtra("id", ID)
                                                intent.putExtra("appointment", appointments._id)
                                                intent.putExtra("resume", "0")

                                                context.startActivity(intent)
                                            })
                                    } else if (laboratory != null) {
                                        CardAppointment(
                                            appointments,
                                            laboratory.telefono,
                                            laboratory.name_lab,
                                            onOpenClick = {
                                                val intent = Intent(
                                                    context,
                                                    AppointmentActivity::class.java
                                                )
                                                intent.putExtra("medicals", "$medical")
                                                intent.putExtra("id", ID)
                                                intent.putExtra("appointment", appointments._id)
                                                intent.putExtra("resume", "2")

                                                context.startActivity(intent)
                                            })
                                    }

                                }
                                else if (medical && appointments.medical == ID) {
                                    if (datepatient != null) {
                                        CardAppointment(
                                            appointments,
                                            datepatient.direction,
                                            datepatient.name_last,
                                            onOpenClick = {
                                                val intent = Intent(
                                                    context,
                                                    AppointmentActivity::class.java
                                                )
                                                intent.putExtra("medicals", "$medical")
                                                intent.putExtra("id", ID)
                                                intent.putExtra("appointment", appointments._id)
                                                intent.putExtra("resume", "1")

                                                context.startActivity(intent)
                                            })
                                    }

                                }

                            }


                        }

                    }
                }


            }

        }

    }


}

@Composable
private fun HomeAppBar(
    searchWidgetState: SearchWidgetState,
    searchTextState: String,
    onTextChange: (String) -> Unit,
    onCloseClicked: () -> Unit,
    onSearchClicked: (String) -> Unit,
    onSearchTriggered: () -> Unit
) {
    when (searchWidgetState) {
        SearchWidgetState.CLOSED -> {
            DefaultAppBar(
                onRefreshClicked = onSearchTriggered
            )
        }
        SearchWidgetState.OPENED -> {
            SearchAppBar(
                text = searchTextState,
                onTextChange = onTextChange,
                onCloseClicked = onCloseClicked,
                onSearchClicked = onSearchClicked
            )
        }
    }
}

@Composable
private fun DefaultAppBar(
    onRefreshClicked: () -> Unit,
) {
    val context = LocalContext.current
    val activity = (LocalContext.current as? Activity)

    TopAppBar(
        title = {
        },
        navigationIcon = {
            IconButton(onClick = {
                val intent = Intent(context, SessionActivity::class.java)
                context.startActivity(intent)
                activity?.finish()
            }) {
                Icon(
                    painter = painterResource(R.drawable.ic_log_out),
                    contentDescription = "",
                    modifier = Modifier.size(20.dp)
                )
            }
        },
        actions = {
            IconButton(
                onClick = {
                    onRefreshClicked()

                }
            ) {
                Icon(
                    imageVector = Icons.Filled.Refresh,
                    contentDescription = "Search Icon",
                    tint = Color.Black
                )
            }
        },
        elevation = 0.dp,
        backgroundColor = Color.White
    )
}

@Composable
fun MedicalImage(user: Int, Title: String) {
    Column(horizontalAlignment = Alignment.CenterHorizontally) {
        Card(
            shape = RoundedCornerShape(30.dp),
            elevation = 10.dp,
            modifier = Modifier
                .padding(horizontal = 5.dp, vertical = 5.dp)
                .size(50.dp)
        ) {

            IconButton(onClick = { /*TODO*/ }, modifier = Modifier.size(50.dp)) {
                Image(
                    modifier = Modifier
                        .size(50.dp)
                        .clip(CircleShape),
                    painter = rememberImagePainter(
                        data = "${user}",
                        builder = {
                            placeholder(R.drawable.placeholder)
                            error(R.drawable.placeholder)
                        }
                    ),
                    contentDescription = null,
                    contentScale = ContentScale.FillHeight
                )

            }
        }
        Text(
            Title,
            fontSize = 10.sp,
            fontFamily = FontFamily.SansSerif,
            fontWeight = FontWeight.Normal
        )
    }


}

@Composable
fun MedicalSearchImage(onSearch: () -> Unit) {
    Column(horizontalAlignment = Alignment.CenterHorizontally) {

        Card(
            shape = RoundedCornerShape(30.dp),
            elevation = 2.dp,
            modifier = Modifier
                .padding(horizontal = 5.dp, vertical = 5.dp)
                .size(50.dp)
        ) {
            IconButton(onClick = { onSearch.invoke() }, modifier = Modifier.size(50.dp)) {
                Icon(painterResource(R.drawable.ic_search), contentDescription = "")

            }
        }
        Text(
            "Search ",
            fontSize = 10.sp,
            fontFamily = FontFamily.SansSerif,
            fontWeight = FontWeight.Normal
        )
    }

}

@Composable
private fun SearchAppBar(
    text: String,
    onTextChange: (String) -> Unit,
    onCloseClicked: () -> Unit,
    onSearchClicked: (String) -> Unit,
) {
    Surface(
        modifier = Modifier
            .fillMaxWidth()
            .height(56.dp),
        elevation = 0.dp,
        color = Color.White
    ) {
        TextField(modifier = Modifier
            .fillMaxWidth(),
            value = text,
            onValueChange = {
                onTextChange(it)
            },
            placeholder = {
                Text(

                    modifier = Modifier
                        .alpha(ContentAlpha.medium),
                    text = "Search here...",
                    color = Color.Black
                )
            },
            textStyle = TextStyle(
                fontSize = MaterialTheme.typography.subtitle1.fontSize
            ),
            singleLine = true,
            leadingIcon = {
                IconButton(
                    modifier = Modifier
                        .alpha(ContentAlpha.medium),
                    onClick = { onSearchClicked(text) }
                ) {
                    Icon(
                        imageVector = Icons.Filled.Search,
                        contentDescription = "Search Icon",
                        tint = Color.Black
                    )
                }
            },
            trailingIcon = {
                IconButton(
                    onClick = {
                        if (text.isNotEmpty()) {
                            onTextChange("")
                        } else {
                            onCloseClicked()
                        }
                    }
                ) {
                    Icon(
                        imageVector = Icons.Default.Close,
                        contentDescription = "Close Icon",
                        tint = Color.Black
                    )
                }
            },
            keyboardOptions = KeyboardOptions(
                imeAction = ImeAction.Search
            ),
            keyboardActions = KeyboardActions(
                onSearch = {
                    onSearchClicked(text)
                }
            ),
            colors = TextFieldDefaults.textFieldColors(
                backgroundColor = Color.Transparent,
                cursorColor = Color.Black.copy(alpha = ContentAlpha.medium)
            ))
    }
}




