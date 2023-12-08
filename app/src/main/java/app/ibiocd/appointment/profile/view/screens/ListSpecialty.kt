package com.nopalsoft.simple.rest.presentation

import android.app.Activity
import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import app.ibiocd.appointment.R
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import app.ibiocd.appointment.profile.viewmodel.SpecialtyViewModel
import app.ibiocd.appointment.UserViewModel
import app.ibiocd.appointment.model.Specialty
import app.ibiocd.appointment.presentation.components.profile.CardSpecialty
import app.ibiocd.appointment.presentation.normalized.BlockSpace
import app.ibiocd.appointment.presentation.normalized.Spacers
import kotlinx.coroutines.runBlocking

@Composable
fun ListSpecialty(
    editb:Boolean,
    ID:String,
    viewModel: UserViewModel = hiltViewModel(),
    specialtyViewModel: SpecialtyViewModel = hiltViewModel(),

    ) {
    val isLoading by viewModel.isLoading.observeAsState(false)
    val specialty by specialtyViewModel.specialty.observeAsState(arrayListOf())
    val listspecialt =specialty.filter { ID.contentEquals(it.medical) }

    val activity = (LocalContext.current as? Activity)
    val context= LocalContext.current
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
                            runBlocking {
                                specialtyViewModel.addSpecialty(Specialty("","Title ${listspecialt.size + 1}","Comment ${listspecialt.size + 1}", ID,"",""))
                                specialtyViewModel.loadSpecialty()

                            }

                        },) {
                            Icon(  painter = painterResource(R.drawable.ic_add_circle), contentDescription = "", modifier = Modifier.size(20.dp))

                        }
                        IconButton(onClick = {
                            runBlocking {
                                specialtyViewModel.loadSpecialty()

                            }

                        },) {
                        Icon(  painter = painterResource(R.drawable.ic_refresh), contentDescription = "", modifier = Modifier.size(20.dp))

                    }
                    }
                )



            }

        }
    ) {

        LazyColumn(modifier = Modifier
            .fillMaxSize()
            .padding(bottom = 60.dp)
            .background(Color.White))
        {




            item {
                BlockSpace()
                Row( verticalAlignment = Alignment.CenterVertically) {
                    Text(
                        text = "Specialty",
                        style = TextStyle(color = Color.Black, fontSize = 20.sp, fontWeight = FontWeight.Normal),
                        modifier = Modifier.padding(start = 20.dp)
                    )

                }

                Spacers()
            }

            var itemCount = listspecialt.size
            if (isLoading) itemCount++

            items(count = itemCount) { index ->
                var auxIndex = index;
                if (isLoading) {
                    if (auxIndex == 0)
                        return@items CircularProgressIndicator()
                    auxIndex--
                }

                    val specialtys = listspecialt[auxIndex]

                Log.d("VALORASD",specialtys.title)
                    CardSpecialty(editb,specialtys, onAdd = {
                        runBlocking {
                            specialtyViewModel.uploadSpecialty(it)                    //

                        }
                    })



            }
        }

    }

}
