package com.nopalsoft.simple.rest.presentation

import android.app.Activity
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import app.ibiocd.appointment.R
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.*
import androidx.compose.runtime.*
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
import app.ibiocd.appointment.profile.viewmodel.DateViewModel
import app.ibiocd.appointment.UserViewModel
import app.ibiocd.appointment.model.Date
import app.ibiocd.appointment.profile.datasource.entity.Medical
import app.ibiocd.appointment.presentation.components.profile.CardDate
import app.ibiocd.appointment.presentation.components.profile.CardDateLoading
import app.ibiocd.appointment.presentation.normalized.BlockSpace
import app.ibiocd.appointment.presentation.normalized.Spacers
import app.ibiocd.appointment.profile.viewmodel.MedicalViewModel
import kotlinx.coroutines.runBlocking

@Composable
fun ListSchedules(
    editb:Boolean,
    ID:String,
    viewModel: DateViewModel = hiltViewModel(),
    medicalViewModel: MedicalViewModel = hiltViewModel(),
    userViewModel: UserViewModel = hiltViewModel()

) {
    val context= LocalContext.current
    var email by remember { mutableStateOf("") }


    val isLoading by viewModel.isLoading.observeAsState(false)
    val dates by viewModel.date.observeAsState(arrayListOf())
    val medicals by medicalViewModel.medicals.observeAsState(arrayListOf())

    val listdate =dates.filter { ID.contentEquals(it.medical) }

    val activity = (LocalContext.current as? Activity)

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
                            viewModel.addDate(Date("",ID,"00:00 de 24:00","00:00 de 24:00","00:00 de 24:00","00:00 de 24:00","00:00 de 24:00","00:00 de 24:00","00:00 de 24:00"))

                            runBlocking {
                                viewModel.loadDate()

                            }

                        },) {
                            Icon(  painter = painterResource(R.drawable.ic_add_circle), contentDescription = "", modifier = Modifier.size(20.dp))

                        }
                        IconButton(onClick = {
                            runBlocking {
                                viewModel.loadDate()

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
                        text = "Horarios",
                        style = TextStyle(color = Color.Black, fontSize = 20.sp, fontWeight = FontWeight.Normal),
                        modifier = Modifier.padding(start = 20.dp)
                    )
                    Spacers()
                }

                Spacers()
            }

            var itemCount = listdate.size
            if (isLoading) itemCount++

            items(count = itemCount) { index ->
                var auxIndex = index;
                if (isLoading) {
                    if (auxIndex == 0)
                        return@items CardDateLoading()
                    auxIndex--
                }


                val medical = medicals.find { it._id==ID }
                if(listdate[auxIndex]._id==medicals.find { it._id==ID }?.hour_on){
                    CardDate(editb,listdate[auxIndex],true, onSelected = {dateupdate,boolean->


                        if (boolean){
                            if (medical != null) {
                                medicalViewModel.uploadMedical(Medical(medical._id,medical.name_last,medical.dni,medical.phone,medical.profession,medical.email,medical.direction,medical.tuition,medical.img,medical.token_not,dateupdate._id))
                            }
                        }




                    })
                }else{
                    if (editb){
                        CardDate(editb,listdate[auxIndex],false, onSelected = {dateupdate,boolean->

                            if (boolean){
                                if (medical != null) {
                                    medicalViewModel.uploadMedical(Medical(medical._id,medical.name_last,medical.dni,medical.phone,medical.profession,medical.email,medical.direction,medical.tuition,medical.img,medical.token_not,dateupdate._id))
                                }
                            }

                        })
                    }

                }




            }
        }

    }

}
