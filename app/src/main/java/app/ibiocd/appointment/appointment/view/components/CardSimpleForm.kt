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
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.toSize
import androidx.hilt.navigation.compose.hiltViewModel
import coil.compose.rememberImagePainter
import app.ibiocd.appointment.profile.datasource.entity.Medical
import app.ibiocd.appointment.model.Patient
import app.ibiocd.appointment.presentation.normalized.Spacers
import app.ibiocd.appointment.R
import app.ibiocd.appointment.appointment.viewmodel.AppointmentViewModel
import app.ibiocd.appointment.model.Appointment
import app.ibiocd.appointment.model.Specialty
import app.ibiocd.appointment.presentation.normalized.BlockSpace
import app.ibiocd.appointment.util.AppointmentConstant
import kotlinx.coroutines.runBlocking

@Composable
fun CardSimpleForm(medicallist: List<Medical>, patientlist: List<Patient>, specialtylist: List<Specialty>, onSelected:(Appointment) -> Unit, viewModelappointment: AppointmentViewModel = hiltViewModel()) {
    var comment by remember { mutableStateOf("") }
    val list =  listOf("Kotlin","Java","Dart","Phyton")
    var selectedMedicalItem by remember { mutableStateOf("")}
    var selectedSpecialtyItem by remember { mutableStateOf("")}
    var selectedProfessionItem by remember { mutableStateOf("")}
    var selectedPatientItem by remember { mutableStateOf("")}


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
                dropDownMenuMedical(medicallist, onSelected ={
                    selectedMedicalItem=it
                    onSelected(Appointment("","","","",selectedSpecialtyItem,selectedPatientItem,selectedMedicalItem,selectedProfessionItem,"",""))
                    runBlocking {

                        viewModelappointment.getAllAppointment()
                    }

                })
            }

            Spacers()
            dropDownMenuProfession(   medicallist.filter { selectedMedicalItem==it._id  }, onSelected = {
                selectedProfessionItem=it
                onSelected(Appointment("","","","",selectedSpecialtyItem,selectedPatientItem,selectedMedicalItem,selectedProfessionItem,"",""))

            })
            Spacers()
            dropDownMenuSpecialty(specialtylist.filter { selectedMedicalItem==it.medical  }, onSelected = {
                selectedSpecialtyItem=it
                onSelected(Appointment("","","","",selectedSpecialtyItem,selectedPatientItem,selectedMedicalItem,selectedProfessionItem,"",""))
            })
            Spacers()
            dropDownMenuPatient(patientlist, onSelected = {
                selectedPatientItem=it
                onSelected(Appointment("","","","",selectedSpecialtyItem,selectedPatientItem,selectedMedicalItem,selectedProfessionItem,"",""))

            })
            BlockSpace()
            Spacers()

        }
    }

}


@Composable
fun dropDownMenuMedical(list: List<Medical>, onSelected:(String) -> Unit) {
    var expanded by remember { mutableStateOf(false) }
    var selectedItem by remember { mutableStateOf("")}
    var textFiledSize by remember { mutableStateOf(Size.Zero)}
    
    val icon= if(expanded){
        painterResource(id = R.drawable.ic_keyboard_arrow_up)
    }else{
        painterResource(id = R.drawable.ic_keyboard_arrow_down)

    }
    Column() {
        OutlinedTextField(
            value = selectedItem,
            onValueChange = { selectedItem = it },
            label = { Text("medical") },
            modifier = Modifier
                .fillMaxWidth()
                .onGloballyPositioned { layoutCoordinates ->
                    textFiledSize = layoutCoordinates.size.toSize()
                },
                trailingIcon = {
                    Icon( icon, contentDescription =AppointmentConstant.DESCRIPTION_LIST_MEDICAL , modifier = Modifier
                        .size(25.dp)
                        .testTag("medical_list")
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
                    selectedItem=label.name_last
                    onSelected(label._id)
                    expanded=false }) {
                  Text(text = label.name_last)
                }
            }
        }
    }
}



@Composable
fun dropDownMenuProfession(list: List<Medical>, onSelected:(String) -> Unit) {
    var expanded by remember { mutableStateOf(false) }
    var selectedItem by remember { mutableStateOf("")}
    var textFiledSize by remember { mutableStateOf(Size.Zero)}

    val icon= if(expanded){
        painterResource(id = R.drawable.ic_keyboard_arrow_up)
    }else{
        painterResource(id = R.drawable.ic_keyboard_arrow_down)

    }
    Column() {
        OutlinedTextField(
            value = selectedItem,
            onValueChange = { selectedItem = it },
            label = { Text("Profession") },
            modifier = Modifier
                .fillMaxWidth()
                .onGloballyPositioned { layoutCoordinates ->
                    textFiledSize = layoutCoordinates.size.toSize()
                },
            trailingIcon = {
                Icon( icon, contentDescription = AppointmentConstant.DESCRIPTION_LIST_PROFESSION , modifier = Modifier
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
                    selectedItem= label.profession
                    onSelected(label.profession)

                    expanded=false
                }) {
                    Text(text = label.profession)
                }
            }
        }
    }
}

@Composable
fun dropDownMenuSpecialty(list: List<Specialty>?, onSelected:(String) -> Unit) {
    var expanded by remember { mutableStateOf(false) }
    var selectedItem by remember { mutableStateOf("")}
    var textFiledSize by remember { mutableStateOf(Size.Zero)}

    val icon= if(expanded){
        painterResource(id = R.drawable.ic_keyboard_arrow_up)
    }else{
        painterResource(id = R.drawable.ic_keyboard_arrow_down)

    }
    Column() {
        OutlinedTextField(
            value = selectedItem,
            onValueChange = { selectedItem = it },
            label = { Text("Specialty") },
            modifier = Modifier
                .fillMaxWidth()
                .onGloballyPositioned { layoutCoordinates ->
                    textFiledSize = layoutCoordinates.size.toSize()
                },
            trailingIcon = {
                Icon( icon, contentDescription =AppointmentConstant.DESCRIPTION_LIST_SPECIALITY , modifier = Modifier
                    .size(25.dp)
                    .clickable { expanded = !expanded }, tint = Color.Gray)

            }
        )

        DropdownMenu(
            expanded=expanded,
            onDismissRequest = {expanded=false},
            modifier = Modifier.width(with(LocalDensity.current){textFiledSize.width.toDp()})

        ){
            list?.forEach { label ->
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
@Composable
fun dropDownMenuPatient(list: List<Patient>, onSelected:(String) -> Unit) {
    var expanded by remember { mutableStateOf(false) }
    var selectedItem by remember { mutableStateOf("")}
    var textFiledSize by remember { mutableStateOf(Size.Zero)}

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
                Icon( icon, contentDescription =AppointmentConstant.DESCRIPTION_LIST_PATIENT , modifier = Modifier
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
