package app.ibiocd.appointment.presentation.components.profile

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import app.ibiocd.appointment.R
import app.ibiocd.appointment.profile.datasource.entity.Medical
import app.ibiocd.appointment.presentation.normalized.BlockSpace
import app.ibiocd.appointment.presentation.normalized.Spacers
import app.ibiocd.appointment.profile.viewmodel.MedicalViewModel


@Composable
fun CardResetTuition(ID:String,
                     BackMedical:()->Unit,
                     medicalViewModel: MedicalViewModel = hiltViewModel(),
){
    var tuition by remember { mutableStateOf("") }
    val medicals by medicalViewModel.medicals.observeAsState(arrayListOf())

    val medical= medicals.find { it._id==ID } ?: Medical("","","","","","","","","","","")

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
            Row(modifier = Modifier.fillMaxWidth().padding(horizontal = 20.dp).size(25.dp), verticalAlignment = Alignment.CenterVertically) {
                Icon( painterResource(id = R.drawable.ic_medical), contentDescription ="" , modifier = Modifier.size(30.dp), tint = Color.Gray)
                Spacers()
                Text("Ingrese sus datos", fontSize = 14.sp,fontFamily = FontFamily.SansSerif, fontWeight = FontWeight.Bold)

            }
            BlockSpace()
            OutlinedTextField(
                value = tuition,
                onValueChange = { tuition = it },
                label = { Text("Matricula") }
            )
            BlockSpace()
            Row(modifier = Modifier
                .fillMaxWidth()
                .wrapContentWidth(Alignment.CenterHorizontally)) {
                Button(onClick = {


                    medicalViewModel.uploadMedical(Medical(medical._id,medical.name_last,medical.dni,medical.phone,medical.profession,medical.email,medical.direction,tuition,medical.img,medical.token_not,medical.hour_on))
                        BackMedical.invoke()


                }) {
                    Text("Confirmar", fontSize = 10.sp,fontFamily = FontFamily.SansSerif, fontWeight = FontWeight.Light)
                }
                Spacers()
                OutlinedButton(onClick = {  BackMedical.invoke() }) {
                    Text("Cancelar", fontSize = 10.sp,fontFamily = FontFamily.SansSerif, fontWeight = FontWeight.Light)
                }
            }
            Spacers()
            BlockSpace()
        }
    }
}