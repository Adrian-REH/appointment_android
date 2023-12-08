package com.nopalsoft.simple.rest.presentation

import android.widget.CalendarView
import androidx.compose.foundation.layout.*
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
import androidx.compose.ui.viewinterop.AndroidView
import androidx.hilt.navigation.compose.hiltViewModel
import app.ibiocd.appointment.appointment.view.components.CardHour
import app.ibiocd.appointment.appointment.view.components.CardSimpleForm
import app.ibiocd.appointment.presentation.normalized.BlockSpace
import app.ibiocd.appointment.presentation.normalized.Spacers
import app.ibiocd.appointment.R
import app.ibiocd.appointment.UserViewModel
import app.ibiocd.appointment.appointment.viewmodel.AppointmentViewModel
import app.ibiocd.appointment.model.*
import app.ibiocd.appointment.profile.datasource.entity.Medical
import app.ibiocd.appointment.util.AppointmentConstant
import com.google.accompanist.pager.ExperimentalPagerApi
import com.google.accompanist.pager.HorizontalPager
import com.google.accompanist.pager.rememberPagerState
import kotlinx.coroutines.launch
import java.util.Calendar.*

@OptIn(ExperimentalPagerApi::class)
@Composable
fun AppointmentOne(datelist:List<Date>, medicallist: List<Medical>, patientlist: List<Patient>, specialtylist: List<Specialty>, onSelected:(Appointment) -> Unit, viewModel: UserViewModel = hiltViewModel(), viewModelappointment: AppointmentViewModel = hiltViewModel()) {
    val pagerState = rememberPagerState(pageCount = 2)
    val scope = rememberCoroutineScope()
    val appointmentlist by viewModelappointment.appointment.observeAsState(arrayListOf())
    // val state =viewModelappointment.appointmentState.collectAsState(initial = null) Para poder usar Flows
    val list = listOf("Form Appointment ", "Fecha")
    var AppointmentItem by remember { mutableStateOf(Appointment("","","","","","","","","",""))}
    var day by remember { mutableStateOf(0)}
    val listHour= generatorListHour(AppointmentItem,datelist,medicallist,appointmentlist,day)//lista e hora


    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        Spacers()
        Text(list[pagerState.currentPage],fontSize = 20.sp, fontFamily = FontFamily.SansSerif, fontWeight = FontWeight.Normal,modifier = Modifier.padding(start = 20.dp))
        Spacers()
        HorizontalPager(state = pagerState, verticalAlignment = Alignment.Top) { page ->
            when(page){
                0 -> CardSimpleForm(medicallist,patientlist,specialtylist, onSelected={
                    AppointmentItem=it
                    if (it.hora!=""&& it.medical!=""&& it.patient!=""&& it.profession!=""&& it.specialty!=""&& it.fecha!=""){
                        onSelected(Appointment("",AppointmentItem.fecha,AppointmentItem.hora,"",AppointmentItem.specialty,AppointmentItem.patient,AppointmentItem.medical,AppointmentItem.profession,"",""))
                    }
                })
                1 ->
                    Card(
                    shape = RoundedCornerShape(30.dp),
                    elevation = 10.dp,
                    modifier = Modifier
                        .padding(horizontal = 20.dp)
                        .fillMaxWidth()
                    ) {
                    Dates(onSearchClicked = {i,j->
                        AppointmentItem.fecha=i
                        day=j
                        if (AppointmentItem.profession!=""&& AppointmentItem.medical!=""&& AppointmentItem.patient!=""&& AppointmentItem.specialty!=""){
                            onSelected(Appointment("",AppointmentItem.fecha,AppointmentItem.hora,"",AppointmentItem.specialty,AppointmentItem.patient,AppointmentItem.medical,AppointmentItem.profession,"",""))

                        }else{
                            pagerState.pageCount=0

                        }
                    })

                }
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
                    Text("calendar",fontSize = 14.sp, fontFamily = FontFamily.SansSerif, fontWeight = FontWeight.Normal,modifier = Modifier.padding(start = 20.dp), color = Color.Black)
                    Spacers()
                    Icon(painterResource(id = R.drawable.ic_forward), contentDescription =AppointmentConstant.DESCRIPTION_BTN_CALENDAR, modifier = Modifier.size(20.dp), tint = Color.Black )

                }
            }
            else{
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


        if (AppointmentItem.fecha!=""&&AppointmentItem.profession!=""&& AppointmentItem.medical!=""&& AppointmentItem.patient!=""&& AppointmentItem.specialty!=""){
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

                        val itemCount2 = listHour.size

                        items(count = itemCount2) { index ->
                            val auxIndex = index;


                            CardHour(listHour[auxIndex],onSelect = {
                                AppointmentItem.hora=it
                                onSelected(Appointment("",AppointmentItem.fecha,AppointmentItem.hora,"",AppointmentItem.specialty,AppointmentItem.patient,AppointmentItem.medical,AppointmentItem.profession,"",""))


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

@Composable
fun generatorListHour(
    AppointmentItem: Appointment,
    datelist: List<Date>,
    medicallist: List<Medical>,
    appointmentlist: List<Appointment>,
    day: Int,
):ArrayList<String> {
    val array= ArrayList<String>()
    var date by remember { mutableStateOf(Date("","","","","","","","","")) }
    val listturnhora= arrayOf("09:00","09:15","09:30","09:45","10:00","10:15","10:30","10:45","11:00","11:25","11:45","12:00","12:15","12:30","12:45","13:00","13:15","13:30","13:45","14:00","14:15","14:30","14:45","15:00","15:15","15:30","15:45","16:00","16:15","16:30","16:45","17:00","17:15","17:30","17:45","18:00","18:15","18:30","18:45")

    array.addAll(listturnhora)
    if (AppointmentItem.medical!=""){
        val hour_on= medicallist.find { AppointmentItem.medical.contentEquals(it._id) }?.hour_on
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

    return array
}

@Composable
fun Dates(onSearchClicked: (String,Int) -> Unit){

    var dayOfWeek  by remember{
        mutableStateOf(0)
    }
    val c= getInstance()
    val today=("${c.get(java.util.Calendar.DAY_OF_MONTH)}/${c.get(java.util.Calendar.MONTH)+1}/${c.get(
        java.util.Calendar.YEAR)}")
    var fecha  by remember{
        mutableStateOf(today)
    }
    Column(modifier = Modifier
        .fillMaxWidth(1f), horizontalAlignment = Alignment.CenterHorizontally) {


        AndroidView(factory = { CalendarView(it) }, update = {
            it.minDate=c.timeInMillis



            it.setOnDateChangeListener { calendarView,year,moth,day ->
                fecha=("${day}/${moth +1}/$year")
                c.set(year,moth,day)
                dayOfWeek=c.get(DAY_OF_WEEK)
            }
        })
        Button(onClick = { onSearchClicked(fecha,dayOfWeek) }) {
            Text(text = "Search")

        }
        BlockSpace()

    }

}

