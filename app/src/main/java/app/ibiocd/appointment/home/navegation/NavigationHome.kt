package app.ibiocd.appointment.home.navegation

import android.util.Log
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Scaffold
import androidx.compose.material.Surface
import androidx.compose.runtime.*
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import app.ibiocd.appointment.appointment.viewmodel.LabViewModel
import app.ibiocd.appointment.home.viewmodel.SedeViewModel
import app.ibiocd.appointment.profile.viewmodel.DateViewModel
import app.ibiocd.appointment.file.viewmodel.FileViewModel
import app.ibiocd.appointment.profile.viewmodel.SpecialtyViewModel
import app.ibiocd.appointment.appointment.viewmodel.AppointmentViewModel
import app.ibiocd.appointment.home.navegation.HomeDestinations.*
import app.ibiocd.appointment.home.viewmodel.FavViewModel
import app.ibiocd.appointment.presentation.components.BottomNavigationBar
import app.ibiocd.appointment.presentation.screens.Home.SearchMedical
import app.ibiocd.appointment.profile.viewmodel.MedicalViewModel
import app.ibiocd.appointment.profile.viewmodel.PatientViewModel
import app.ibiocd.appointment.util.UtilJwtParser
import app.ibiocd.appointment.util.UtilSharedToken
import kotlinx.coroutines.*


@Composable
fun NavigationHome(
    Medical: Boolean,
    user: String,
    navControllerHome: NavHostController,
    navController: NavHostController,
    dateViewModel: DateViewModel = hiltViewModel(),
    favViewModel: FavViewModel = hiltViewModel(),
    sedeViewModel: SedeViewModel = hiltViewModel(),
    labViewModel: LabViewModel = hiltViewModel(),
    fileViewModel: FileViewModel = hiltViewModel(),
    medicalViewModel: MedicalViewModel = hiltViewModel(),
    patientViewModel: PatientViewModel = hiltViewModel(),
    specialtyViewModel: SpecialtyViewModel = hiltViewModel(),
    viewModelappointment: AppointmentViewModel = hiltViewModel()

) {
    val context= LocalContext.current

    val username = UtilSharedToken(context).getJwt()?.let { UtilJwtParser(it).getUsername() }.orEmpty()

    Surface(
        modifier = Modifier.fillMaxSize(),
        color = MaterialTheme.colors.background
    ) {
        NavHost(navController = navControllerHome, startDestination = LoadScreen.route) {
            composable(LoadScreen.route) {
                LaunchedEffect(key1 = true) {

                    /**
                     * Aqui pido todos los datos necesarios para vincular Medicos, Paciente y Laboratorio
                     */
                    withContext(Dispatchers.IO) {
                        val response = listOf(
                            async { patientViewModel.loadAllPatient() },
                            async { medicalViewModel.loadAllMedical() },
                            async { labViewModel.loadLab() }, // TODO debe obtenerse todos independiente de si es medico o paciente.
                            async { sedeViewModel.loadSede() }, //TODO se debe pedir dentro de LAB
                            async { specialtyViewModel.loadSpecialty() }, //TODO se debe pedir dentro del medico
                            async { dateViewModel.loadDate() }, //TODO se debe pedir dentro del medico
                        ).awaitAll()

                    }
                    /**
                     * Aqui pido el resto de datos, como los turnos sacados, los Favoritos y los Archivos.
                     */
                    withContext(Dispatchers.IO) {
                        listOf(
                            async { favViewModel.getAllFavs() },
                            async { viewModelappointment.getAllAppointment() }, //TODO El back End debe devolver la lista respecto a username
                            async { fileViewModel.findAllFiles() },

                            ).awaitAll()

                    }

                    withContext(Dispatchers.Main) {
                        navControllerHome.popBackStack()
                        navControllerHome.navigate(Navegation.route)
                    }
                }
                Column(
                    modifier = Modifier
                        .fillMaxSize()
                        .wrapContentSize(Alignment.Center),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    CircularProgressIndicator()

                }
                //    viewModel.getDirectionLatLng(address, neighbourhood, country)


            }


            composable(Navegation.route) {
                var name by remember { mutableStateOf("") }
                var img by remember { mutableStateOf("") }

                var navigationItems: List<HomeDestinations>

                if (Medical) {
                    navigationItems = listOf(
                        Home,
                        Calendar,
                        Files,
                        Menu
                    )
                } else {
                    navigationItems = listOf(
                        Home,
                        Favs,
                        Calendar,
                        Files,
                        Menu
                    )
                }
                val medical by medicalViewModel.medical.observeAsState()
                val patient by patientViewModel.patient.observeAsState()
                if (Medical) {
                    name = medical?.name_last
                        ?: ""
                    img = medical?.img
                        ?: ""
                } else {
                    name = patient?.name_last
                        ?: ""
                    img = patient?.img
                        ?: ""
                }
                Scaffold(
                    bottomBar = {
                        BottomNavigationBar(
                            navController = navController,
                            items = navigationItems
                        )
                    }

                ) {

                    if (Medical) {
                        NavigationMedicalHost(
                            navControllerHome,
                            navController,
                            Medical,
                            img,
                            name,
                            username
                        )

                    } else {

                        NavigationPatientHost(
                            navControllerHome,
                            navController,
                            Medical,
                            img,
                            name,
                            username
                        )

                    }
                }

            }
            composable(SearchMedical.route) {
                SearchMedical(ID= username, onBackHome = {
                    navControllerHome.popBackStack()
                    navControllerHome.navigate(Navegation.route)

                })
            }
        }

    }
}