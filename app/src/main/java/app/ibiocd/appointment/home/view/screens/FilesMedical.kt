package com.nopalsoft.simple.rest.presentation

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import app.ibiocd.appointment.file.viewmodel.FileViewModel
import com.google.accompanist.pager.ExperimentalPagerApi
import com.google.accompanist.pager.HorizontalPager
import com.google.accompanist.pager.rememberPagerState
import app.ibiocd.appointment.home.viewmodel.HomeViewModel
import app.ibiocd.appointment.home.SearchWidgetState
import app.ibiocd.appointment.UserViewModel
import app.ibiocd.appointment.appointment.viewmodel.AppointmentViewModel
import app.ibiocd.appointment.home.view.components.TabsMedical
import app.ibiocd.appointment.home.view.components.files.*
import app.ibiocd.appointment.profile.viewmodel.PatientViewModel
import kotlinx.coroutines.runBlocking

@OptIn(ExperimentalPagerApi::class)
@Composable
fun FilesMedical(
    int: Int,
    Medical: Boolean,
    ID: String,
    homeViewModel: HomeViewModel = hiltViewModel(),
    patientViewModel: PatientViewModel = hiltViewModel(),
    viewModel: UserViewModel = hiltViewModel(),
    fileViewModel: FileViewModel = hiltViewModel(),
    viewModelappointment: AppointmentViewModel = hiltViewModel()
) {
    val searchWidgetState by homeViewModel.searchWidgetState
    val searchTextState by homeViewModel.searchTextState
    val pagerState = rememberPagerState(pageCount = 6, int)
    val isLoading by viewModel.isLoading.observeAsState(false)

    val context = LocalContext.current
    val appointment by viewModelappointment.appointment.observeAsState(arrayListOf())
    val patient by patientViewModel.patients.observeAsState(arrayListOf())
    val files by fileViewModel.files.observeAsState(arrayListOf())


    runBlocking {
        patientViewModel.loadAllPatient()
        viewModelappointment.getAllAppointment()
        fileViewModel.findAllFiles()
    }
    Scaffold(
        topBar = {
            Column(
                modifier = Modifier
                    .background(Color.White)

            ) {


                TabsMedical(pagerState = pagerState)


            }

        }
    ) {

        HorizontalPager(state = pagerState, verticalAlignment = Alignment.Top) { page ->
            when (page) {
                0 -> ListAppointmentMedical(appointment, files, patient, isLoading, ID)
                1 -> ListStudy(appointment, files, null, patient, isLoading, Medical, ID)
                2 -> ListLaboratory(appointment, files, null, patient, isLoading, Medical, ID)
                3 -> ListRecipe(appointment, files, null, patient, isLoading, Medical, ID)
                4 -> ListOdontograms(appointment, files, patient, isLoading, ID)
                5 -> ListForms(appointment, files, patient, isLoading, ID)
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





