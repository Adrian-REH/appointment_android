package app.ibiocd.appointment.presentation.components.profile

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import app.ibiocd.appointment.R
import app.ibiocd.appointment.profile.viewmodel.SpecialtyViewModel
import app.ibiocd.appointment.UserViewModel
import app.ibiocd.appointment.model.Specialty
import app.ibiocd.appointment.presentation.normalized.BlockSpace
import app.ibiocd.appointment.presentation.normalized.Spacers
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.supervisorScope
import okhttp3.internal.wait


@Composable
fun CardSpecialty(
    editb:Boolean,
    specialtys:Specialty,
    viewModel: UserViewModel = hiltViewModel(),
    specialtyViewModel: SpecialtyViewModel = hiltViewModel(),
    onAdd:(Specialty)->Unit

){

    var edit by rememberSaveable { mutableStateOf(false) }//EL PROFESIONAL EDITO
    var Precio by rememberSaveable { mutableStateOf("") }
    var Detalle by rememberSaveable { mutableStateOf("") }
    var Oferta by rememberSaveable { mutableStateOf("") }
    var Title by rememberSaveable { mutableStateOf("") }

    Precio=specialtys.price
    Detalle=specialtys.comment
    Oferta=specialtys.offer
    Title=specialtys.title

    Card(
        shape = RoundedCornerShape(30.dp),
        elevation = 10.dp,
        modifier = Modifier
            .padding(horizontal = 20.dp, vertical = 5.dp)
            .fillMaxWidth()
    ) {
        Column(modifier = Modifier.wrapContentWidth(Alignment.CenterHorizontally), horizontalAlignment = Alignment.CenterHorizontally) {
            Spacers()
            BlockSpace()


            OutlinedTextField(
                value = Title,
                onValueChange = {
                    Title = it
                    edit=true
                },
                label = { Text("Title") }
            )



            Spacers()

            OutlinedTextField(
                value = Oferta,
                onValueChange = { Oferta = it
                    edit=true},
                label = { Text("Oferta") }
            )


            Spacers()

            OutlinedTextField(
                value = Precio,
                onValueChange = { Precio = it
                    edit=true},
                label = { Text("Precio") }
            )

            Spacers()
            OutlinedTextField(
                value = Detalle,
                onValueChange = { Detalle = it
                    edit=true},
                label = { Text("Detalle") }
            )

            Spacers()


            if (editb){


                Row() {

                    Spacers()
                    IconButton(onClick = {
                        runBlocking {
                            specialtyViewModel.deleteSpecialty(specialtys)

                        }

                    },) {
                        Icon(  painter = painterResource(R.drawable.ic_delete), contentDescription = "", modifier = Modifier.size(30.dp))

                    }
                    if (edit){
                        Spacers()
                        IconButton(onClick = {
                            onAdd.invoke(Specialty(specialtys._id,Title,Detalle,specialtys.medical,Oferta,Precio))
                            edit=false

                        },) {
                            Icon(  painter = painterResource(R.drawable.ic_upload), contentDescription = "", modifier = Modifier.size(30.dp))

                        }
                    }






                }
            }
            BlockSpace()

        }
    }


}