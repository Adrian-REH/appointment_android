package app.ibiocd.appointment.presentation.screens.Appointment

import android.util.Log
import android.widget.Toast
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.toSize
import androidx.hilt.navigation.compose.hiltViewModel
import app.ibiocd.appointment.file.viewmodel.FileViewModel
import app.ibiocd.appointment.R
import app.ibiocd.appointment.UserViewModel
import app.ibiocd.appointment.appointment.viewmodel.AppointmentViewModel
import app.ibiocd.appointment.appointment.viewmodel.OdontogramViewModel
import app.ibiocd.appointment.model.*
import app.ibiocd.appointment.presentation.normalized.BlockSpace
import coil.annotation.ExperimentalCoilApi
import com.bumptech.glide.integration.compose.ExperimentalGlideComposeApi
import com.bumptech.glide.integration.compose.GlideImage
import kotlinx.coroutines.launch


@OptIn(ExperimentalCoilApi::class, ExperimentalGlideComposeApi::class)
@Composable
fun Odontogram(
    appointmentID:String,
    viewModelappointment: AppointmentViewModel = hiltViewModel(),
    fileViewModel: FileViewModel = hiltViewModel(),
    viewModelUser: UserViewModel = hiltViewModel(),
    viewModel: OdontogramViewModel = hiltViewModel()
) {
    var appointment by remember {
        mutableStateOf(Appointment("","","","","","","","","",""))
    }
    var file by remember {
        mutableStateOf(Files("","","","","","","",""))
    }

    var odontograma by remember {
        mutableStateOf(Odontogram("","","",""))
    }
    val idodontogramstate =viewModel.odontogramState.collectAsState(initial = null)
    val scope = rememberCoroutineScope()
    val context= LocalContext.current
    val isLoading by viewModelUser.isLoading.observeAsState(false)

    val appointmentlist by viewModelappointment.appointment.observeAsState(arrayListOf())
    appointment= appointmentlist.find { appointmentID.contentEquals(it._id) }?: Appointment("","","","","","","","","","")

    val filelist by fileViewModel.files.observeAsState(arrayListOf())
    file=filelist.find { appointment.files.contentEquals(it._id) }?: Files("","","","","","","","")

    val odontogramlist by viewModel.odontogram.observeAsState(arrayListOf())
    val toothlist by viewModel.tooth.observeAsState(arrayListOf())

    odontograma= odontogramlist.find { it._id.contentEquals(file.odontogram) }?:Odontogram("","","","")
    var list= arrayListOf<Tooths>()

    if (odontograma.data!=""){
        for (i in 0 until toothlist.size){
            list.add(Tooths(toothlist[i].number,toothlist[i].imgTop,toothlist[i].imgBot))
        }


    }else{
        for (i in 0 until 32){
            list.add(
                Tooths(i.toString(),
                    "https://appointmentibiocd.azurewebsites.net/Dientes/superior/sup${i}.png",
                    "https://appointmentibiocd.azurewebsites.net/Dientes/inferior/inf${i}.png"))
        }
    }


    var numbertooth by rememberSaveable{ mutableStateOf("") }
    var Bot by rememberSaveable { mutableStateOf(true) }
    var Top by rememberSaveable { mutableStateOf(true) }

    var SelectToothBot by rememberSaveable{ mutableStateOf("") }
    var SelectToothTop by rememberSaveable{ mutableStateOf("") }
    val listError= ArrayList<String>() //LISTA DE ERRORES DEL DIENTE DE ARRIBA

    for (i in 0 until 12){
        listError.add("https://cdn-icons-png.flaticon.com/512/91/91160.png")
    }

    Column(modifier = Modifier
        .fillMaxSize()
        .padding(30.dp)){
            dropDownTooth(list, onSelected = { select->


                numbertooth=select
                SelectToothTop = list.find { it.number.contentEquals(numbertooth) }?.imgBot ?: ""
                SelectToothBot= list.find { it.number.contentEquals(numbertooth) }?.imgTop ?: ""

            })


        LazyRow(modifier = Modifier
            .fillMaxWidth()
            .wrapContentWidth(Alignment.CenterHorizontally))
        {
            if (Top){
                item(){

                    Card(
                        shape = RoundedCornerShape(60.dp),
                        elevation = 20.dp,
                        modifier = Modifier
                            .height(280.dp)
                            .width(120.dp)
                            .padding(20.dp)
                            .clickable {
                                Top = !Top

                            }
                    ) {

                        GlideImage(
                            model = SelectToothTop,
                            contentDescription = null,
                            modifier = Modifier.padding(10.dp).clickable(onClick = {  }).fillParentMaxSize(),
                        )



                    }
                }
            }else{

                val itemCount = listError.size

                //ITEMs DE LISTA DE ERROR
                items(count = itemCount){ index ->
                    Card(
                        shape = RoundedCornerShape(60.dp),
                        elevation = 20.dp,
                        modifier = Modifier
                            .height(280.dp)
                            .width(120.dp)
                            .padding(20.dp)
                            .clickable {


                                Top = !Top
                                SelectToothTop = listError[index]
                                toothlist
                                    .filter { it.number == numbertooth }
                                    .forEach { it.imgTop = SelectToothTop }

                            }

                    ) {


                    }
                }
            }
        }

        BlockSpace()
        LazyRow(modifier = Modifier
            .fillMaxWidth()
            .wrapContentWidth(Alignment.CenterHorizontally) )
        {
            if (Bot){
                item(){
                    Card(
                        shape = RoundedCornerShape(60.dp),
                        elevation = 20.dp,
                        modifier = Modifier
                            .height(130.dp)
                            .width(130.dp)
                            .padding(20.dp)
                            .clickable {
                                Bot = !Bot
                            }
                    ) {

                        GlideImage(
                            model = SelectToothBot,
                            contentDescription = null,
                            modifier = Modifier.padding(10.dp).clickable(onClick = {  }).fillParentMaxSize(),
                        )

                    }
                }
            }else{
                //ITEMs DE LISTA DE ERROR
                val itemCount2 = listError.size

                items(count = itemCount2){ index ->
                    Card(
                        shape = RoundedCornerShape(60.dp),
                        elevation = 20.dp,
                        modifier = Modifier
                            .height(130.dp)
                            .width(130.dp)
                            .padding(20.dp)
                            .clickable {
                                Bot = !Bot

                                SelectToothBot = listError[index]
                                toothlist
                                    .filter { it.number == numbertooth }
                                    .forEach { it.imgBot = SelectToothBot }

                            }
                    ) {


                    }
                }
            }
        }



        Button(onClick = {
            if (file.odontogram==""){

                viewModel.addOdontogram(ApiTooth(list),appointment.patient,appointment.medical)

            }else{

                viewModel.updateontogram(odontograma._id,ApiTooth(list),appointment.patient,appointment.medical)

            }


        },modifier = Modifier
            .fillMaxWidth()
            .height(60.dp)
            .padding(horizontal = 30.dp),
            colors = ButtonDefaults.buttonColors(backgroundColor = Color.White),
            shape = RoundedCornerShape(30),
            elevation = ButtonDefaults.elevation(
                defaultElevation = 10.dp,
                pressedElevation = 8.dp,
                disabledElevation = 0.dp
            )
        ) {

            if (file.odontogram!=""){

                 if(idodontogramstate.value?.isLoading==true || isLoading) CircularProgressIndicator( modifier = Modifier.size(20.dp), strokeWidth = 2.dp)
                else Text("Update")
            }else{

                 if(idodontogramstate.value?.isLoading==true || isLoading) CircularProgressIndicator( modifier = Modifier.size(20.dp), strokeWidth = 2.dp)
                else Text("Create")
            }
        }


    }

    LaunchedEffect(key1 = idodontogramstate.value?.isSuccess ){
        scope.launch {
            if (idodontogramstate.value?.isSuccess?._id !=""){
                val success = idodontogramstate.value?.isSuccess

                file.odontogram=success?._id.toString()
                Log.d("SUCCESS",file.toString() + success?._id.toString())

                fileViewModel.uploadFile(Files(file._id,file.laboratory,file.prescriptions,file.stadies,success?._id.toString(),file.form,file.patient,file.medical))



            }
        }
    }

    LaunchedEffect(key1 = idodontogramstate.value?.isError ){
        scope.launch {
            if (idodontogramstate.value?.isError?.isNotEmpty()==true){
                val success = idodontogramstate.value?.isError
                Log.d("ERROR",success.toString())
                Toast.makeText(context,"Error: Intenta mas tarde", Toast.LENGTH_LONG).show()
            }
        }
    }
}

@Composable
fun dropDownTooth(list: List<Tooths>, onSelected:(String) -> Unit) {
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
            label = { Text("Numero de diente") },
            modifier = Modifier
                .fillMaxWidth()
                .onGloballyPositioned { layoutCoordinates ->
                    textFiledSize = layoutCoordinates.size.toSize()
                },
            trailingIcon = {
                Icon( icon, contentDescription ="ToothList" , modifier = Modifier
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
                    selectedItem=label.number
                    onSelected(label.number)
                    expanded=false }) {
                    Text(text = label.number)
                }
            }
        }
    }


}
