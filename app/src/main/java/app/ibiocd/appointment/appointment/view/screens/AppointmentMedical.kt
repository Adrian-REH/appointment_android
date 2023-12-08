package com.nopalsoft.simple.rest.presentation

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import app.ibiocd.appointment.file.viewmodel.FileViewModel
import app.ibiocd.appointment.UserViewModel
import app.ibiocd.appointment.appointment.viewmodel.AppointmentViewModel
import app.ibiocd.appointment.appointment.viewmodel.OdontogramViewModel
import app.ibiocd.appointment.model.Appointment
import app.ibiocd.appointment.model.Files
import app.ibiocd.appointment.appointment.view.components.CardFile
import app.ibiocd.appointment.appointment.view.components.CardSimpleResume
import app.ibiocd.appointment.presentation.normalized.BlockSpace
import app.ibiocd.appointment.presentation.normalized.Spacers
import app.ibiocd.appointment.profile.viewmodel.MedicalViewModel
import app.ibiocd.appointment.profile.viewmodel.PatientViewModel

@Composable
fun AppointmentMedical(
    appointmentID: String,
    onDownload:(String)->Unit,
    onNav:()->Unit,
    viewModel: UserViewModel = hiltViewModel(),
    fileViewModel: FileViewModel = hiltViewModel(),
    viewModelOdont: OdontogramViewModel = hiltViewModel(),
    patientViewModel: PatientViewModel = hiltViewModel(),
    medicalViewModel: MedicalViewModel = hiltViewModel(),
    viewModelappointment: AppointmentViewModel = hiltViewModel(),

    ) {
    var AppointmentItem by remember { mutableStateOf(Appointment("","","","","","","","","","")) }
    var FileItem by remember { mutableStateOf(Files("","","","","","","","")) }
    var patientname by remember { mutableStateOf("") }
    var medicalname by remember { mutableStateOf("") }
    val isLoading by viewModel.isLoading.observeAsState(false)
    val appointment by viewModelappointment.appointment.observeAsState(arrayListOf())
    val patientlist by patientViewModel.patients.observeAsState(arrayListOf())
    val medicallist by medicalViewModel.medicals.observeAsState(arrayListOf())
    val fileslist by fileViewModel.files.observeAsState(arrayListOf())

    AppointmentItem=appointment.find { it._id.contentEquals(appointmentID) }?:Appointment("","","","","","","","","","")
    FileItem=fileslist.find { it._id.contentEquals(AppointmentItem.files) }?: Files("","","","","","","","")
    medicalname= medicallist.find { it._id.contentEquals(AppointmentItem.medical) }?.name_last ?:""
    patientname= patientlist.find { it._id.contentEquals(AppointmentItem.patient) }?.name_last ?:""

    LazyColumn(modifier = Modifier
        .fillMaxSize()
        .padding(bottom = 60.dp)
        .background(Color.White))
    {

        item {
            BlockSpace()
            Text("Resumen ",fontSize = 20.sp, fontFamily = FontFamily.SansSerif, fontWeight = FontWeight.Normal,modifier = Modifier.padding(start = 20.dp))
            Spacers()
            CardSimpleResume(patientname,medicalname,AppointmentItem,AppointmentItem.price, onComment = {
                AppointmentItem.coment=it
            })

            BlockSpace()
            Column(modifier = Modifier
                .fillMaxWidth()
                .wrapContentWidth(Alignment.CenterHorizontally), horizontalAlignment = Alignment.CenterHorizontally) {

                Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.Center){
                    if(isLoading){
                        CircularProgressIndicator()
                    }
                }
            }
            BlockSpace()
        }

        item {
            Column(modifier = Modifier
                .fillMaxWidth()
                .wrapContentWidth(Alignment.CenterHorizontally), horizontalAlignment = Alignment.CenterHorizontally) {


                CardFile("Prescription",FileItem.prescriptions,false,onDownload={
                    onDownload.invoke(it)

                },
                onSelect = {

                })
                Spacers()
                CardFile("Odontogram",FileItem.odontogram,true,onDownload={
                   // onDownload.invoke(it)

                },
                    onSelect = {
                        viewModelOdont.loadOdontogram(FileItem.odontogram)
                        onNav.invoke()
                    })
                Spacers()
                CardFile("Form",FileItem.form,false,onDownload={
                    onDownload.invoke(it)
                },
                    onSelect = {

                    })

            }
        }


    }
}
