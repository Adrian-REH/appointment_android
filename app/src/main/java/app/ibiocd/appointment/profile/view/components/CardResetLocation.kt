package app.ibiocd.appointment.presentation.components.profile

import android.util.Log
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
import app.ibiocd.appointment.UserViewModel
import app.ibiocd.appointment.profile.datasource.entity.Medical
import app.ibiocd.appointment.model.Patient
import app.ibiocd.appointment.presentation.normalized.BlockSpace
import app.ibiocd.appointment.presentation.normalized.Spacers
import app.ibiocd.appointment.profile.viewmodel.MedicalViewModel
import app.ibiocd.appointment.profile.viewmodel.PatientViewModel
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.model.CameraPosition
import com.google.android.gms.maps.model.LatLng
import com.google.maps.android.compose.*

@Composable
fun CardResetLocation(ID:String,
                      medicalb:Boolean,
                      BackMedical:()->Unit,
                      BackPatient: () -> Unit,
                      viewModel: UserViewModel = hiltViewModel(),
                      medicalViewModel: MedicalViewModel = hiltViewModel(),
                      patientViewModel: PatientViewModel = hiltViewModel(),
){
    var address by remember { mutableStateOf("") }
    var country by remember { mutableStateOf("") }
    var neightbourhood by remember { mutableStateOf("") }
    var LatLngMap by remember { mutableStateOf(LatLng(0.623938,-58.382782)) }


    val medicals by medicalViewModel.medicals.observeAsState(arrayListOf())
    val patients by patientViewModel.patients.observeAsState(arrayListOf())

    val medical= medicals.find { it._id==ID } ?: Medical("","","","","","","::","","","","")
    val patient= patients.find { it._id==ID } ?: Patient("","","","","","::","","")


    val cameraPositionState: CameraPositionState = rememberCameraPositionState {
        position = CameraPosition.fromLatLngZoom(LatLngMap, 11f)
    }
    val settingMap by remember {
        mutableStateOf(MapUiSettings(zoomControlsEnabled = false))
    }
    val isLoading by viewModel.isLoading.observeAsState(false)
    val LatLngDir by viewModel.LatLngDir.observeAsState(arrayListOf())
    if (LatLngDir.isNotEmpty()){
        LatLngMap=LatLng(LatLngDir[1],LatLngDir[0])
        cameraPositionState.move(update = CameraUpdateFactory.newCameraPosition( CameraPosition(LatLng(LatLngDir[1],LatLngDir[0]), 15f, 0f, 0f)))

    }


    if (medical.direction !="::"){
        LatLngMap=LatLng(medical.direction.split(":")[1].toDouble(),medical.direction.split(":")[0].toDouble())
        cameraPositionState.move(update = CameraUpdateFactory.newCameraPosition( CameraPosition(LatLngMap, 15f, 0f, 0f)))

    }
    if (patient.direction!="::"){
        LatLngMap=LatLng(patient.direction.split(":")[1].toDouble(),patient.direction.split(":")[0].toDouble())
        cameraPositionState.move(update = CameraUpdateFactory.newCameraPosition( CameraPosition(LatLngMap, 15f, 0f, 0f)))


    }


    Column(horizontalAlignment = Alignment.CenterHorizontally) {




            Card(
                shape = RoundedCornerShape(30.dp),
                elevation = 10.dp,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 20.dp)
                    .height(200.dp)

            ){
                GoogleMap(modifier = Modifier
                    .fillMaxWidth()
                    .height(350.dp),
                    cameraPositionState = cameraPositionState,
                    properties = MapProperties(isMyLocationEnabled = false),
                    uiSettings = settingMap

                ){


                    Marker(position =LatLngMap )
                }
            }





        Spacers()
        Card(
            shape = RoundedCornerShape(30.dp),
            elevation = 10.dp,
            modifier = Modifier
                .padding(horizontal = 20.dp, vertical = 5.dp)
                .fillMaxWidth()
        ) {
            Column(horizontalAlignment = Alignment.CenterHorizontally, modifier = Modifier
                .fillMaxWidth()) {





                Spacers()
                BlockSpace()
                Row(modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 20.dp)
                    .size(25.dp), verticalAlignment = Alignment.CenterVertically) {
                    Icon( painterResource(id = R.drawable.ic_map), contentDescription ="" , modifier = Modifier.size(30.dp), tint = Color.Gray)
                    Spacers()
                    Text("Ingrese sus datos", fontSize = 14.sp,fontFamily = FontFamily.SansSerif, fontWeight = FontWeight.Bold)

                }
                BlockSpace()
                OutlinedTextField(
                    value = address,
                    onValueChange = { address = it },
                    label = { Text("Direccion") }
                )
                Spacers()
                OutlinedTextField(
                    value = neightbourhood,
                    onValueChange = { neightbourhood = it },
                    label = { Text("Barrio") }
                )
                Spacers()
                OutlinedTextField(
                    value = country,
                    onValueChange = { country = it },
                    label = { Text("Pais") }
                )
                Spacers()



                BlockSpace()
                Row(modifier = Modifier
                    .fillMaxWidth()
                    .wrapContentWidth(Alignment.CenterHorizontally)) {
                    Button(onClick = {

                        if (medicalb){

                            medicalViewModel.uploadMedical(Medical(medical._id,medical.name_last,medical.dni,medical.phone,medical.profession,medical.email,LatLngMap.longitude.toString() +":"+ LatLngMap.latitude.toString()+":"+address+","+ neightbourhood +","+country,medical.tuition,medical.img,medical.token_not,medical.hour_on))
                            //Log.d("medicaltUpload",medical(medicals._id,medicals.name_last,medicals.dni,medicals.phone,medicals.profession,medicals.email,LatLngMap.longitude.toString() +":"+ LatLngMap.latitude.toString()+":"+address+","+ neightbourhood +","+country,medicals.tuition,medicals.img,medicals.hour_on).toString())

                            BackMedical.invoke()
                        }else{

                            patientViewModel.uploadPatient(Patient(patient._id,patient.name_last,patient.dni,patient.phone,patient.email,LatLngMap.longitude.toString() +":"+ LatLngMap.latitude.toString()+":"+address+","+ neightbourhood +","+country,patient.img,patient.token_not))
                            //Log.d("patientUpload",Patient(patients._id,patients.name_last,patients.dni,patients.phone,patients.email,LatLngMap.longitude.toString() +":"+ LatLngMap.latitude.toString()+":"+address+","+ neightbourhood +","+country,patients.img).toString())
                            BackPatient.invoke()

                            Log.d("LATITUDYLONGITUD",LatLngMap.longitude.toString() +":"+ LatLngMap.latitude.toString())
                            Log.d("LATITUDYLONGITUD",LatLngMap.toString())
                        }

                    }) {
                        Text("Confirmar", fontSize = 10.sp,fontFamily = FontFamily.SansSerif, fontWeight = FontWeight.Light)
                    }
                    Spacers()
                    OutlinedButton(onClick = {

                        if (address!="" && neightbourhood!=""&&country!="")
                        viewModel.getDirectionLatLng(address,neightbourhood,country)


                    }) {

                        if (isLoading){
                            CircularProgressIndicator(modifier = Modifier.size(20.dp), strokeWidth = 2.dp)

                        }
                        Spacers()
                        Text("Buscar", fontSize = 10.sp,fontFamily = FontFamily.SansSerif, fontWeight = FontWeight.Light)
                    }


                }
                Spacers()
                BlockSpace()
            }
        }
    }

}