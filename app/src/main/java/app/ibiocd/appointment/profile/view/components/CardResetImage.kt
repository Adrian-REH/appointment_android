package app.ibiocd.appointment.presentation.components.profile

import android.app.Activity
import android.net.Uri
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import coil.compose.rememberImagePainter
import app.ibiocd.appointment.R
import app.ibiocd.appointment.profile.datasource.entity.Medical
import app.ibiocd.appointment.model.Patient
import app.ibiocd.appointment.presentation.normalized.BlockSpace
import app.ibiocd.appointment.presentation.normalized.Spacers
import app.ibiocd.appointment.profile.viewmodel.MedicalViewModel
import app.ibiocd.appointment.profile.viewmodel.PatientViewModel
import coil.annotation.ExperimentalCoilApi

@OptIn(ExperimentalCoilApi::class)
@Composable
fun CardResetImage(
    ID:String,
    medicalb:Boolean,
    isLoading:Boolean,
    isImage:String,
    imageUri:String,
    onOpen:()->Unit,
    onAddImage:()->Unit,
    BackMedical:()->Unit,
    BackPatient: () -> Unit,
    medicalViewModel: MedicalViewModel= hiltViewModel(),
    patientViewModel: PatientViewModel = hiltViewModel(),
){
    val activity = (LocalContext.current as? Activity)

    val medicals by medicalViewModel.medicals.observeAsState(arrayListOf())
    val patients by patientViewModel.patients.observeAsState(arrayListOf())

    var btnconf by remember {
        mutableStateOf("Subir")
    }
    val medical= medicals.find { it._id==ID } ?: Medical("","","","","","","","","","","")
    val patient= patients.find { it._id==ID } ?: Patient("","","","","","","","")

    Card(
        shape = RoundedCornerShape(30.dp),
        elevation = 10.dp,
        modifier = Modifier
            .padding(horizontal = 20.dp, vertical = 5.dp)
            .fillMaxWidth()
    ) {
        Column(horizontalAlignment = Alignment.CenterHorizontally) {
            Spacers()
            BlockSpace()

            Image(
                modifier = Modifier
                    .size(200.dp)
                    .clip(CircleShape)
                    .clickable {
                        onOpen.invoke()
                    },

                painter = rememberImagePainter(
                    data = Uri.parse(imageUri),
                    builder = {
                        placeholder(R.drawable.placeholder)
                        error(R.drawable.placeholder)
                    }
                ),
                contentDescription = null,
                contentScale = ContentScale.FillHeight
            )
            Spacers()
            Row(modifier = Modifier
                .fillMaxWidth()
                .wrapContentWidth(Alignment.CenterHorizontally)) {
                Button(onClick = {
                    onAddImage.invoke()
                    if (isImage!=""){
                        if (medicalb){

                            medicalViewModel.uploadMedical(Medical(medical._id,medical.name_last,medical.dni,medical.phone,medical.profession,medical.email,medical.direction,medical.tuition,"https://appointmentibiocd.azurewebsites.net/"+isImage,medical.token_not,medical.hour_on))

                            BackMedical.invoke()
                        }else{

                            patientViewModel.uploadPatient(Patient(patient._id,patient.name_last,patient.dni,patient.phone,patient.email,patient.direction,"https://appointmentibiocd.azurewebsites.net/"+isImage,patient.token_not))
                            BackPatient.invoke()
                        }
                    }


                }) {
                    Text(btnconf, fontSize = 10.sp,fontFamily = FontFamily.SansSerif, fontWeight = FontWeight.Light)
                }
                Spacers()
                OutlinedButton(onClick = { onAddImage.invoke() }) {
                    Text("Cambiar", fontSize = 10.sp,fontFamily = FontFamily.SansSerif, fontWeight = FontWeight.Light)
                }
            }


            Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.Center){
                if(isLoading){
                    btnconf="Confirmar"
                    CircularProgressIndicator()
                }
            }


            BlockSpace()
        }
    }
}