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
import app.ibiocd.appointment.notification.viewmodel.NotificationViewModel
import app.ibiocd.appointment.model.*
import app.ibiocd.appointment.appointment.view.components.TabsAppointment
import app.ibiocd.appointment.file.viewmodel.FileViewModel
import app.ibiocd.appointment.home.viewmodel.FavViewModel
import app.ibiocd.appointment.profile.datasource.entity.Medical
import app.ibiocd.appointment.profile.viewmodel.*
import kotlinx.coroutines.launch

@OptIn(ExperimentalPagerApi::class)
@Composable
fun AppointmentCreate(
    medicalb: Boolean,
    ID: String,
    Save: () -> Unit,
    NavBack: () -> Unit,
    viewModel: UserViewModel = hiltViewModel(),
    medicalViewModel: MedicalViewModel = hiltViewModel(),
    patientViewModel: PatientViewModel = hiltViewModel(),
    fileViewModel: FileViewModel = hiltViewModel(),
    favViewModel: FavViewModel = hiltViewModel(),
    specialtyViewModel: SpecialtyViewModel = hiltViewModel(),
    dateViewModel: DateViewModel = hiltViewModel(),
    viewModelappointment: AppointmentViewModel = hiltViewModel(),
    viewModelNot: NotificationViewModel = hiltViewModel()
) {
    val pagerState = rememberPagerState(pageCount = 2)
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
    var patientname by remember { mutableStateOf(Patient("", "", "", "", "", "", "", "")) }
    var medicalname by remember {
        mutableStateOf(
            Medical(
                "",
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
    var specialtyprice by remember { mutableStateOf("") }
    val scope = rememberCoroutineScope()


    val idFile by fileViewModel.idFile.observeAsState("")

    val isLoading by viewModel.isLoading.observeAsState(false)
    val state = viewModelappointment.appointmentState.collectAsState(initial = null)

    val favoritelist by favViewModel.favs.observeAsState(arrayListOf())
    val patientlist by patientViewModel.patients.observeAsState(arrayListOf())
    val medicallist by medicalViewModel.medicals.observeAsState(arrayListOf())
    val specialtylist by specialtyViewModel.specialty.observeAsState(arrayListOf())
    val datelist by dateViewModel.date.observeAsState(arrayListOf())

    val patients = ArrayList<Patient>()
    val medicals = ArrayList<Medical>()


    patientname =
        patientlist.firstOrNull { AppointmentItem.patient.contentEquals(it._id) } ?: Patient(
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            ""
        )
    medicalname =
        medicallist.firstOrNull { AppointmentItem.medical.contentEquals(it._id) } ?: Medical(
            "",
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



    specialtyprice =
        specialtylist.firstOrNull { AppointmentItem.specialty.contentEquals(it._id) }?.price
            ?: ""
    if (!isLoading) {

        if (medicalb) {

            medicallist.firstOrNull { ID.contentEquals(it._id) }
                ?.let { medicals.add(it) }

            for (i in favoritelist.indices) {
                val patient =
                    patientlist.firstOrNull { favoritelist[i].patient.contentEquals(it._id) }
                if (patient != null) {
                    patients.add(patient)
                }
            }

        } else {

            patientlist.firstOrNull { ID.contentEquals(it._id) }
                ?.let { patients.add(it) }

            for (i in favoritelist.indices) {
                val medical =
                    medicallist.firstOrNull { favoritelist[i].medical.contentEquals(it._id) }
                if (medical != null && medical.hour_on != "") {
                    medicals.add(medical)
                }
            }
        }


    }


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
                                contentDescription = "BackHome",
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
                0 -> AppointmentOne(datelist, medicals, patients, specialtylist, onSelected = {

                    AppointmentItem = it

                    if (it.hora != "" && it.medical != "" && it.patient != "" && it.profession != "" && it.specialty != "" && it.fecha != "") {
                        AppointmentItem = it
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
                1 ->
                    AppointmentTwo(
                        patientname.name_last,
                        medicalname.name_last,
                        AppointmentItem,
                        specialtyprice,
                        Save = {

                            scope.launch {

                                if (it.hora != "" && it.medical != "" && it.patient != "" && it.profession != "" && it.specialty != "" && it.fecha != "") {
                                    viewModelappointment.addAppointment(it)

                                }
                            }

                            viewModelNot.newNotification(
                                PushNotification(
                                    NotificationData(
                                        "Nuevo turno!",
                                        " ${if(medicalb) medicalname.name_last else patientname.name_last} creo un nuevo turno para el ${it.fecha}"
                                    ),
                                    patientname.token_not
                                )
                            )
                        })


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
                        Toast.makeText(context, "Error: Intenta mas tarde", Toast.LENGTH_LONG)
                            .show()
                    }
                }
            }

        }


    }


}
