package com.nopalsoft.simple.rest.presentation

import android.util.Log
import androidx.compose.foundation.layout.*
import app.ibiocd.appointment.R
import androidx.compose.foundation.lazy.LazyRow
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
import app.ibiocd.appointment.UserViewModel
import app.ibiocd.appointment.appointment.viewmodel.AppointmentViewModel
import app.ibiocd.appointment.model.*
import app.ibiocd.appointment.appointment.view.components.CardHour
import app.ibiocd.appointment.appointment.view.components.CardLabStudiForm
import app.ibiocd.appointment.presentation.normalized.BlockSpace
import app.ibiocd.appointment.presentation.normalized.Spacers
import com.google.accompanist.pager.ExperimentalPagerApi
import com.google.accompanist.pager.HorizontalPager
import com.google.accompanist.pager.rememberPagerState
import kotlinx.coroutines.launch

@OptIn(ExperimentalPagerApi::class)
@Composable
fun AppointmentLabOne(datelist:List<Date>,laboratory: List<Labs>, patientlist: List<Patient>,sedelist:List<Sedes>, specialtylist: List<Specialty>, onSelected:(Appointment) -> Unit,viewModel: UserViewModel = hiltViewModel(),viewModelappointment: AppointmentViewModel = hiltViewModel()) {
    val     user =4
    val pagerState = rememberPagerState(pageCount = 2)
    val scope = rememberCoroutineScope()
    val appointmentlist by viewModelappointment.appointment.observeAsState(arrayListOf())
    val isLoading by viewModel.isLoading.observeAsState(false)

    val list = listOf("Form Appointment ", "Fecha")
    var AppointmentItem by remember { mutableStateOf(Appointment("","","","","","","","","",""))}
    var day by remember { mutableStateOf(0)}
    var date by remember { mutableStateOf(Date("","","","","","","","","")) }


    val listturnhora= arrayOf("09:00","09:15","09:30","09:45","10:00","10:15","10:30","10:45","11:00","11:25","11:45","12:00","12:15","12:30","12:45","13:00","13:15","13:30","13:45","14:00","14:15","14:30","14:45","15:00","15:15","15:30","15:45","16:00","16:15","16:30","16:45","17:00","17:15","17:30","17:45","18:00","18:15","18:30","18:45")
    val array= ArrayList<String>()
    array.addAll(listturnhora)

    if (AppointmentItem.medical!=""){
        val hour_on= laboratory.find { AppointmentItem.medical.contentEquals(it._id) }?.hour_on
        val applistmedical= appointmentlist.filter { AppointmentItem.medical.contentEquals(it.medical) }
        date= datelist.find { hour_on.contentEquals( it._id) }?:Date("","","09:00 de 18:45","09:00 de 18:45","09:00 de 18:45","09:00 de 18:45","09:00 de 18:45","09:00 de 18:45","09:00 de 18:45")



        when(day){
            0-> array.removeIf{it<date.domingo.split(" de ")[0] ||it>date.domingo.split(" de ")[1] }
            1-> array.removeIf{it<date.lunes.split(" de ")[0] ||it>date.lunes.split(" de ")[1] }
            2-> array.removeIf{it<date.martes.split(" de ")[0] ||it>date.martes.split(" de ")[1] }
            3-> array.removeIf{it<date.miercoles.split(" de ")[0] ||it>date.miercoles.split(" de ")[1] }
            4-> array.removeIf{it<date.jueves.split(" de ")[0] ||it>date.jueves.split(" de ")[1] }
            5-> array.removeIf{it<date.viernes.split(" de ")[0] ||it>date.viernes.split(" de ")[1] }
            6-> array.removeIf{it<date.sabado.split(" de ")[0] ||it>date.sabado.split(" de ")[1] }
        }

        for ((j,value) in applistmedical.withIndex()){
            if (value.fecha==AppointmentItem.fecha){
                array.removeIf { it==value.hora

                }

            }
        }


    }
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        Spacers()
        BlockSpace()
        Text(list[pagerState.currentPage],fontSize = 20.sp, fontFamily = FontFamily.SansSerif, fontWeight = FontWeight.Normal,modifier = Modifier.padding(start = 20.dp))
        Spacers()

        HorizontalPager(state = pagerState, verticalAlignment = Alignment.Top) { page ->
            when(page){
                0 -> CardLabStudiForm(laboratory,patientlist,sedelist,specialtylist, onSelected={
                    AppointmentItem=it
                    onSelected(Appointment("",AppointmentItem.fecha,AppointmentItem.hora,"",AppointmentItem.specialty,AppointmentItem.patient,AppointmentItem.medical,AppointmentItem.profession,"",""))


                    Log.d("ONSELECTED",AppointmentItem.toString())
                })
                1 ->                     Card(
                    shape = RoundedCornerShape(30.dp),
                    elevation = 10.dp,
                    modifier = Modifier
                        .padding(horizontal = 20.dp)
                        .fillMaxWidth()


                ) {
                    Dates(onSearchClicked = { i,j->
                        AppointmentItem.fecha=i
                        day=j
                        onSelected(Appointment("",AppointmentItem.fecha,AppointmentItem.hora,"",AppointmentItem.specialty,AppointmentItem.patient,AppointmentItem.medical,AppointmentItem.profession,"",""))
                        Log.d("ONSELECTED",AppointmentItem.toString())

                    })

                }
            }

        }
        if (isLoading){
            Column(modifier = Modifier
                .fillMaxWidth()
                .wrapContentWidth(Alignment.CenterHorizontally), horizontalAlignment = Alignment.CenterHorizontally)
            {
                CircularProgressIndicator()

            }

        }
        Spacers()
        Column(modifier = Modifier
            .fillMaxWidth()
            .wrapContentWidth(Alignment.CenterHorizontally), horizontalAlignment = Alignment.CenterHorizontally)
        {



            if (pagerState.currentPage==0){
                OutlinedButton(
                    onClick = {
                        scope.launch {
                            pagerState.animateScrollToPage(1)}
                    }
                    ,
                    modifier = Modifier
                        .height(40.dp),
                    shape = RoundedCornerShape(50)) {
                    Text("Calendar",fontSize = 14.sp, fontFamily = FontFamily.SansSerif, fontWeight = FontWeight.Normal,modifier = Modifier.padding(start = 20.dp), color = Color.Black)
                    Spacers()
                    Icon(painterResource(id = R.drawable.ic_forward), contentDescription ="CalendarSee", modifier = Modifier.size(20.dp), tint = Color.Black )

                }
            }else{
                OutlinedButton(
                    onClick = {
                        scope.launch {
                            pagerState.animateScrollToPage(0)}
                    }
                    ,
                    modifier = Modifier
                        .height(40.dp),
                    shape = RoundedCornerShape(50)) {

                    Icon(painterResource(id = R.drawable.ic_arrow_back), contentDescription ="FormSee", modifier = Modifier.size(20.dp), tint = Color.Black )

                    Text("Form",fontSize = 14.sp, fontFamily = FontFamily.SansSerif, fontWeight = FontWeight.Normal,modifier = Modifier.padding(start = 20.dp), color = Color.Black)
                    Spacers()

                }
            }

        }
        Spacers()

        if (AppointmentItem.fecha!=""){
            Card(
                shape = RoundedCornerShape(30.dp),
                elevation = 10.dp,
                modifier = Modifier
                    .padding(horizontal = 20.dp)
                    .fillMaxWidth(),



                ) {
                Column(horizontalAlignment = Alignment.CenterHorizontally) {
                    Text(AppointmentItem.fecha,fontSize = 18.sp, fontFamily = FontFamily.SansSerif, fontWeight = FontWeight.Normal,modifier = Modifier.padding(horizontal = 20.dp, vertical = 10.dp))
                    Spacers()
                    Text("Hora ",fontSize = 20.sp, fontFamily = FontFamily.SansSerif, fontWeight = FontWeight.Normal,modifier = Modifier.padding(start = 20.dp))

                    Spacers()
                    Divider(modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 20.dp)
                        .height(1.dp))

                    LazyRow(modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 20.dp, vertical = 10.dp)){

                        var itemCount2 = array.size
                        if (isLoading) itemCount2++

                        items(count = itemCount2) { index ->
                            var auxIndex = index;

                            if (isLoading) {
                                if (auxIndex == 0)
                                    return@items //MeidcalImage(user, Title)
                                auxIndex--
                            }

                            CardHour(array[auxIndex],onSelect = {
                                AppointmentItem.hora=it
                                onSelected(Appointment("",AppointmentItem.fecha,AppointmentItem.hora,"",AppointmentItem.specialty,AppointmentItem.patient,AppointmentItem.medical,AppointmentItem.profession,"",""))
                                Log.d("ONSELECTED",AppointmentItem.toString())

                            })

                        }

                    }
                    Divider(modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 20.dp)
                        .height(1.dp))
                    BlockSpace()
                }



            }
        }
    }
}
