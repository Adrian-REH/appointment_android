package app.ibiocd.appointment.appointment.view.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.toSize
import androidx.hilt.navigation.compose.hiltViewModel
import coil.compose.rememberImagePainter
import app.ibiocd.appointment.R
import app.ibiocd.appointment.appointment.viewmodel.AppointmentViewModel
import app.ibiocd.appointment.model.*
import app.ibiocd.appointment.presentation.normalized.Spacers
import kotlinx.coroutines.runBlocking

@Composable
fun CardLabStudiForm(laboratory: List<Labs>, patientlist: List<Patient>,sedelis:List<Sedes>, specialtylist: List<Specialty>, onSelected:(Appointment) -> Unit,viewModelappointment: AppointmentViewModel = hiltViewModel()) {
    var comment by remember { mutableStateOf("") }
    var AppointmentItem by remember { mutableStateOf(Appointment("","","","","","","","","",""))}

    Card(
        shape = RoundedCornerShape(30.dp),
        elevation = 20.dp,
        modifier = Modifier
            .padding(horizontal = 30.dp)
            .fillMaxWidth()

    ){
        Column(modifier = Modifier.padding( 20.dp)) {
            Spacers()
            Row(verticalAlignment = Alignment.CenterVertically) {
                Image(
                    modifier = Modifier
                        .size(40.dp)
                        .clip(CircleShape),
                    painter = rememberImagePainter(
                        data = "user.thumbnail",
                        builder = {
                            placeholder(R.drawable.placeholder)
                            error(R.drawable.placeholder)
                        }
                    ),
                    contentDescription = null,
                    contentScale = ContentScale.FillHeight
                )
                Spacers()
                dropDownMenuLab(laboratory, onSelected = {
                    AppointmentItem.medical=it
                    onSelected(Appointment("","","","",AppointmentItem.specialty,AppointmentItem.patient,AppointmentItem.medical,AppointmentItem.profession,"",""))

                    runBlocking {

                        viewModelappointment.getAllAppointment()
                    }

                })
            }
            Spacers()
            dropDownMenuSede(sedelis.filter { AppointmentItem.medical==it.laboratory  }, onSelected = {
                AppointmentItem.profession=it
                onSelected(Appointment("","","","",AppointmentItem.specialty,AppointmentItem.patient,AppointmentItem.medical,AppointmentItem.profession,"",""))

            })
            Spacers()
            LabdropDownMenuPatient(patientlist, onSelected = {
                AppointmentItem.patient=it
                onSelected(Appointment("","","","",AppointmentItem.specialty,AppointmentItem.patient,AppointmentItem.medical,AppointmentItem.profession,"",""))

            })
            Spacers()
            LabdropDownMenuAnalist(specialtylist.filter { AppointmentItem.medical==it.medical  }, onSelected = {
                AppointmentItem.specialty=it
                onSelected(Appointment("","","","",AppointmentItem.specialty,AppointmentItem.patient,AppointmentItem.medical,AppointmentItem.profession,"",""))
            })
            Spacers()

        }
    }

}


@Composable
fun dropDownMenuLab(list: List<Labs>, onSelected:(String) -> Unit) {
    var expanded by remember { mutableStateOf(false) }
    var selectedItem by remember { mutableStateOf("") }
    var textFiledSize by remember { mutableStateOf(Size.Zero) }

    val icon= if(expanded){
        painterResource(id = R.drawable.ic_keyboard_arrow_up)
    }else{
        painterResource(id = R.drawable.ic_keyboard_arrow_down)

    }
    Column() {
        OutlinedTextField(
            value = selectedItem,
            onValueChange = { selectedItem = it },
            label = { Text("Laboratory") },
            modifier = Modifier
                .fillMaxWidth()
                .onGloballyPositioned { layoutCoordinates ->
                    textFiledSize = layoutCoordinates.size.toSize()
                },
            trailingIcon = {
                Icon( icon, contentDescription ="LaboratoryList" , modifier = Modifier
                    .size(25.dp)
                    .clickable { expanded = !expanded }, tint = Color.Gray)

            }
        )

        DropdownMenu(
            expanded=expanded,
            onDismissRequest = {expanded=false},
            modifier = Modifier.width(with(LocalDensity.current){textFiledSize.width.toDp()})

        ){
            list.forEach { label ->
                DropdownMenuItem(onClick = {
                    selectedItem= label.name_lab
                    onSelected(label._id)

                    expanded=false
                }) {
                    Text(text = label.name_lab)
                }
            }
        }
    }
}

@Composable
fun dropDownMenuSede(list: List<Sedes>, onSelected:(String) -> Unit) {
    var expanded by remember { mutableStateOf(false) }
    var selectedItem by remember { mutableStateOf("") }
    var textFiledSize by remember { mutableStateOf(Size.Zero) }

    val icon= if(expanded){
        painterResource(id = R.drawable.ic_keyboard_arrow_up)
    }else{
        painterResource(id = R.drawable.ic_keyboard_arrow_down)

    }
    Column() {
        OutlinedTextField(
            value = selectedItem,
            onValueChange = { selectedItem = it },
            label = { Text("Sede") },
            modifier = Modifier
                .fillMaxWidth()
                .onGloballyPositioned { layoutCoordinates ->
                    textFiledSize = layoutCoordinates.size.toSize()
                },
            trailingIcon = {
                Icon( icon, contentDescription ="SedeList" , modifier = Modifier
                    .size(25.dp)
                    .clickable { expanded = !expanded }, tint = Color.Gray)

            }
        )

        DropdownMenu(
            expanded=expanded,
            onDismissRequest = {expanded=false},
            modifier = Modifier.width(with(LocalDensity.current){textFiledSize.width.toDp()})

        ){
            list.forEach { label ->
                DropdownMenuItem(onClick = {
                    selectedItem=label.name_sede
                    onSelected(label.direction)
                    expanded=false }) {
                    Text(text = label.name_sede)
                }
            }
        }
    }
}

@Composable
fun LabdropDownMenuPatient(list: List<Patient>, onSelected:(String) -> Unit) {
    var expanded by remember { mutableStateOf(false) }
    var selectedItem by remember { mutableStateOf("") }
    var textFiledSize by remember { mutableStateOf(Size.Zero) }

    val icon= if(expanded){
        painterResource(id = R.drawable.ic_keyboard_arrow_up)
    }else{
        painterResource(id = R.drawable.ic_keyboard_arrow_down)

    }
    Column() {
        OutlinedTextField(
            value = selectedItem,
            onValueChange = { selectedItem = it },
            label = { Text("Patient") },
            modifier = Modifier
                .fillMaxWidth()
                .onGloballyPositioned { layoutCoordinates ->
                    textFiledSize = layoutCoordinates.size.toSize()
                },
            trailingIcon = {
                Icon( icon, contentDescription ="PatientList" , modifier = Modifier
                    .size(25.dp)
                    .clickable { expanded = !expanded }, tint = Color.Gray)

            }
        )

        DropdownMenu(
            expanded=expanded,
            onDismissRequest = {expanded=false},
            modifier = Modifier.width(with(LocalDensity.current){textFiledSize.width.toDp()})

        ){
            list.forEach { label ->
                DropdownMenuItem(onClick = {
                    selectedItem= label.name_last
                    onSelected(label._id)
                    expanded=false }) {
                    Text(text = label.name_last)
                }
            }
        }
    }
}

@Composable
fun LabdropDownMenuAnalist(list: List<Specialty>, onSelected:(String) -> Unit) {
    var expanded by remember { mutableStateOf(false) }
    var selectedItem by remember { mutableStateOf("") }
    var textFiledSize by remember { mutableStateOf(Size.Zero) }

    val icon= if(expanded){
        painterResource(id = R.drawable.ic_keyboard_arrow_up)
    }else{
        painterResource(id = R.drawable.ic_keyboard_arrow_down)

    }
    Column() {
        OutlinedTextField(
            value = selectedItem,
            onValueChange = { selectedItem = it },
            label = { Text("Analisis") },
            modifier = Modifier
                .fillMaxWidth()
                .onGloballyPositioned { layoutCoordinates ->
                    textFiledSize = layoutCoordinates.size.toSize()
                },
            trailingIcon = {
                Icon( icon, contentDescription ="AnalisisList" , modifier = Modifier
                    .size(25.dp)
                    .clickable { expanded = !expanded }, tint = Color.Gray)

            }
        )

        DropdownMenu(
            expanded=expanded,
            onDismissRequest = {expanded=false},
            modifier = Modifier.width(with(LocalDensity.current){textFiledSize.width.toDp()})

        ){
            list.forEach { label ->
                DropdownMenuItem(onClick = {
                    selectedItem= label.title
                    onSelected(label.title)

                    expanded=false
                }) {
                    Text(text = label.title)
                }
            }
        }
    }
}
