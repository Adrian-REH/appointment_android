package com.nopalsoft.simple.rest.presentation

import android.content.Intent
import android.util.Log
import android.widget.CalendarView
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.runtime.*
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.viewinterop.AndroidView
import androidx.hilt.navigation.compose.hiltViewModel
import app.ibiocd.appointment.home.viewmodel.HomeViewModel
import app.ibiocd.appointment.home.SearchWidgetState
import app.ibiocd.appointment.UserViewModel
import app.ibiocd.appointment.auth.view.SessionActivity
import app.ibiocd.appointment.appointment.viewmodel.AppointmentViewModel
import app.ibiocd.appointment.home.view.components.CardAppointment
import app.ibiocd.appointment.home.view.components.LoadingCardAppointment
import app.ibiocd.appointment.presentation.normalized.BlockSpace
import app.ibiocd.appointment.profile.viewmodel.MedicalViewModel
import app.ibiocd.appointment.profile.viewmodel.PatientViewModel
import java.util.Calendar.*

@Composable
fun Calendar(
    medical: Boolean,
    homeViewModel: HomeViewModel= hiltViewModel(),
    viewModel: UserViewModel = hiltViewModel(),
    patientViewModel: PatientViewModel = hiltViewModel(),
    medicalViewModel: MedicalViewModel = hiltViewModel(),
    viewModelappointment: AppointmentViewModel = hiltViewModel()
) {
    val searchWidgetState by homeViewModel.searchWidgetState
    val searchTextState by homeViewModel.searchTextState
    val context = LocalContext.current

    val isLoading by viewModel.isLoading.observeAsState(false)
    val appointment by viewModelappointment.appointment.observeAsState(arrayListOf())
    val medicals by medicalViewModel.medicals.observeAsState(arrayListOf())
    val patients by patientViewModel.patients.observeAsState(arrayListOf())

    var date by remember {
        mutableStateOf("")
    }
    Scaffold(
        topBar = {
            HomeAppBar(
                searchWidgetState = searchWidgetState,
                searchTextState = searchTextState,
                onTextChange = {
                    homeViewModel.updateSearchTextState(newValue = it)
                },
                onCloseClicked = {
                    homeViewModel.updateSearchWidgetState(newValue = SearchWidgetState.CLOSED)
                },
                onSearchClicked = {
                    Log.d("Searched Text", it)
                },
                onSearchTriggered = {
                    homeViewModel.updateSearchWidgetState(newValue = SearchWidgetState.OPENED)
                }
            )

        }) {

        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(bottom = 60.dp)
                .background(Color.White)
        ) {
            item {
                Column(modifier = Modifier.fillMaxWidth())
                {


                    BlockSpace()

                    Text(
                        "Selecciona una fecha",
                        fontSize = 20.sp,
                        fontFamily = FontFamily.SansSerif,
                        fontWeight = FontWeight.Normal,
                        modifier = Modifier.padding(start = 20.dp).testTag("title_calendar_select")
                    )
                    BlockSpace()
                    Card(
                        shape = RoundedCornerShape(30.dp),
                        elevation = 10.dp,
                        modifier = Modifier
                            .padding(horizontal = 20.dp)
                            .fillMaxWidth()


                    ) {
                        date(onSearchClicked = {
                            date = it
                        })

                    }

                    BlockSpace()
                    Text(
                        "Appointments",
                        fontSize = 20.sp,
                        fontFamily = FontFamily.SansSerif,
                        fontWeight = FontWeight.Normal,
                        modifier = Modifier.padding(start = 20.dp)
                    )
                    BlockSpace()

                    LazyRow {
                        val appointmentsearch = appointment.filter { it.fecha == date }

                        var itemCount2 = appointmentsearch.size
                        if (isLoading) itemCount2++
                        if (itemCount2 != 0) {
                            items(count = itemCount2) { index ->
                                var auxIndex = index;

                                if (auxIndex == 0) {
                                    BlockSpace()

                                }
                                if (isLoading) {
                                    if (auxIndex == 0)
                                        return@items LoadingCardAppointment()
                                    auxIndex--
                                }

                                val appointments = appointmentsearch[auxIndex]


                                val datemedical =
                                    medicals.firstOrNull { it._id == appointments.medical }
                                val datepatient =
                                    patients.firstOrNull { it._id == appointments.patient }
                                if (!medical) {
                                    if (datemedical != null) {
                                        CardAppointment(
                                            appointments,
                                            datemedical.direction,
                                            datemedical.name_last
                                        )
                                    }

                                } else {
                                    if (datepatient != null) {
                                        CardAppointment(
                                            appointments,
                                            datepatient.direction,
                                            datepatient.name_last
                                        )
                                    }

                                }


                                //val user = users[auxIndex]

                            }

                        }

                    }
                }


            }

        }

    }


}


@Composable
fun date(onSearchClicked: (String) -> Unit) {

    val c = getInstance()
    val today = ("${c.get(DAY_OF_MONTH)}/${c.get(MONTH) + 1}/${
        c.get(
            YEAR
        )
    }")

    var fecha by remember {
        mutableStateOf(today)
    }
    Column(
        modifier = Modifier
            .fillMaxWidth(1f), horizontalAlignment = Alignment.CenterHorizontally
    ) {


        AndroidView(factory = { CalendarView(it) }, update = {
            it.minDate = c.timeInMillis



            it.setOnDateChangeListener { calendarView, year, moth, day ->
                fecha = ("${day}/${moth + 1}/$year")
                c.set(year, moth, day)
            }
        })
        Button(onClick = { onSearchClicked(fecha) }) {
            Text(text = "Search")

        }
        BlockSpace()

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
                onSearchClicked = onSearchTriggered
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
private fun DefaultAppBar(onSearchClicked: () -> Unit) {
    val context = LocalContext.current

    TopAppBar(
        title = { },
        backgroundColor = Color.White,
        elevation = 0.dp,
        actions = {
            IconButton(
                onClick = {
                    context.startActivity(
                        Intent(
                            context,
                            SessionActivity::class.java
                        )
                    )
                },
            ) {
                Icon(
                    imageVector = Icons.Filled.AddCircle,
                    contentDescription = "",
                    tint = Color.Black
                )

            }
        }
    )
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




























