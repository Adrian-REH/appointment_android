package com.nopalsoft.simple.rest.presentation

import android.util.Log
import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import app.ibiocd.appointment.*
import app.ibiocd.appointment.R
import com.google.accompanist.pager.ExperimentalPagerApi
import com.google.accompanist.pager.HorizontalPager
import com.google.accompanist.pager.rememberPagerState
import app.ibiocd.appointment.appointment.viewmodel.AppointmentViewModel
import app.ibiocd.appointment.model.*
import app.ibiocd.appointment.appointment.view.components.TabsAppointment
import app.ibiocd.appointment.appointment.viewmodel.LabViewModel
import app.ibiocd.appointment.profile.viewmodel.DateViewModel
import app.ibiocd.appointment.file.viewmodel.FileViewModel
import app.ibiocd.appointment.home.viewmodel.FavViewModel
import app.ibiocd.appointment.home.viewmodel.SedeViewModel
import app.ibiocd.appointment.profile.viewmodel.PatientViewModel
import app.ibiocd.appointment.profile.viewmodel.SpecialtyViewModel
import kotlinx.coroutines.launch

@OptIn(ExperimentalPagerApi::class)

@Composable
fun AppointmentLabCreate(
    medicalb: Boolean,
    ID: String,
    Save: () -> Unit,
    NavBack: () -> Unit,
    viewModel: UserViewModel = hiltViewModel(),
    patientViewModel: PatientViewModel = hiltViewModel(),
    sedeViewModel: SedeViewModel = hiltViewModel(),
    labViewModel: LabViewModel = hiltViewModel(),
    fileViewModel: FileViewModel = hiltViewModel(),
    favViewModel: FavViewModel = hiltViewModel(),
    specialtyViewModel: SpecialtyViewModel = hiltViewModel(),
    dateViewModel: DateViewModel = hiltViewModel(),
    viewModelappointment: AppointmentViewModel = hiltViewModel()
) {
    val pagerState = rememberPagerState(pageCount = 2)
    val scope = rememberCoroutineScope()
    val state = viewModelappointment.appointmentState.collectAsState(initial = null)


    val context = LocalContext.current
    var AppointmentItem by remember {
        mutableStateOf(
            Appointment(
                "",
                "",
                "",
                "",
                "",
                "",
                "",
                "",
                "",
                ""
            )
        )
    }
    var patientname by remember { mutableStateOf("") }
    var labname by remember { mutableStateOf("") }
    var specialtyprice by remember { mutableStateOf("") }

    val isLoading by viewModel.isLoading.observeAsState(false)
    val datelist by dateViewModel.date.observeAsState(arrayListOf())

    val favs by favViewModel.favs.observeAsState(arrayListOf())
    val patientlist by patientViewModel.patients.observeAsState(arrayListOf())
    val lablist by labViewModel.laboratory.observeAsState(arrayListOf())

    val specialtylist by specialtyViewModel.specialty.observeAsState(arrayListOf())
    val sedelist by sedeViewModel.sedeslab.observeAsState(arrayListOf())
    val idFile by fileViewModel.idFile.observeAsState("")
    val patients = ArrayList<Patient>()



    if (!isLoading) {
        for (i in favs.indices) {
            val patient = patientlist.firstOrNull { favs[i].patient.contentEquals(it._id) }
            if (patient != null) {
                patients.add(patient)
            }
        }
    }

    specialtyprice =
        specialtylist.firstOrNull { AppointmentItem.specialty.contentEquals(it._id) }?.price
            ?: ""
    patientname =
        patientlist.firstOrNull { AppointmentItem.patient.contentEquals(it._id) }?.name_last
            ?: ""
    labname = lablist.firstOrNull { AppointmentItem.medical.contentEquals(it._id) }?.name_lab
        ?: ""
    AppointmentItem.files = idFile

    Scaffold(
        topBar = {
            Column(
                modifier = Modifier
                    .background(Color.White)

            ) {

                TopAppBar(
                    title = { },
                    navigationIcon = {
                        IconButton(onClick = {
                            NavBack.invoke()

                        }) {
                            Icon(
                                painter = painterResource(R.drawable.ic_arrow_back),
                                contentDescription = "",
                                modifier = Modifier.size(20.dp)
                            )
                        }
                    },
                    backgroundColor = Color.White,
                    elevation = 0.dp
                )

                TabsAppointment(pagerState = pagerState)


            }

        }
    ) {
        HorizontalPager(state = pagerState, verticalAlignment = Alignment.Top) { page ->


            when (page) {
                0 -> AppointmentLabOne(
                    datelist,
                    lablist,
                    patients,
                    sedelist,
                    specialtylist,
                    onSelected = {
                        AppointmentItem = it
                        if (it.hora != "" && it.medical != "" && it.patient != "" && it.profession != "" && it.specialty != "" && it.fecha != "") {
                            fileViewModel.addFile(
                                Files(
                                    "",
                                    "",
                                    "",
                                    "",
                                    "",
                                    "",
                                    AppointmentItem.patient,
                                    AppointmentItem.medical
                                )
                            )

                            scope.launch {
                                pagerState.animateScrollToPage(1)
                            }
                        }
                    })
                1 -> AppointmentLabTwo(
                    patientname,
                    labname,
                    AppointmentItem,
                    specialtyprice,
                    Save = {
                        scope.launch {
                            viewModelappointment.addAppointment(it)

                        }


                    })
            }

        }


        LaunchedEffect(key1 = state.value?.isSuccess) {
            scope.launch {
                if (state.value?.isSuccess != null) {
                    val success = state.value?.isSuccess
                    Log.d("SUCCESS", success.toString())
                    Toast.makeText(context, "Turno creado!", Toast.LENGTH_LONG).show()

                    Save.invoke()
                }
            }
        }

        LaunchedEffect(key1 = state.value?.isError) {
            scope.launch {
                if (state.value?.isError?.isNotEmpty() == true) {
                    val success = state.value?.isError
                    Log.d("ERROR", success.toString())
                    Toast.makeText(context, "Error: Intenta mas tarde", Toast.LENGTH_LONG).show()
                }
            }
        }
    }


}
