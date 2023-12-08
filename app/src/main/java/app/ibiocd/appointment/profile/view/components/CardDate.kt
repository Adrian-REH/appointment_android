package app.ibiocd.appointment.presentation.components.profile

import android.app.TimePickerDialog
import android.util.Log
import androidx.compose.foundation.background
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
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import app.ibiocd.appointment.profile.viewmodel.DateViewModel
import app.ibiocd.appointment.R
import app.ibiocd.appointment.UserViewModel
import app.ibiocd.appointment.model.Date
import app.ibiocd.appointment.presentation.normalized.BlockSpace
import app.ibiocd.appointment.presentation.normalized.Spacers
import com.valentinilk.shimmer.shimmer
import java.util.*

@Composable
fun CardDate(editb:Boolean,date: Date,select:Boolean,onSelected: (Date,Boolean) -> Unit,viewModel: UserViewModel = hiltViewModel(),dateViewModel: DateViewModel = hiltViewModel()) {
    val isLoadingUp by viewModel.isLoadingUp.observeAsState(false)

    var dates by remember { mutableStateOf(Date("","","","","","","","","")) }
    var indexation by rememberSaveable { mutableStateOf(10) }
    var edit by rememberSaveable { mutableStateOf(false) }
    var upload by rememberSaveable { mutableStateOf(false) }
    val mContext = LocalContext.current
    dates=date

    // Declaring and initializing a calendar
    val mCalendar = Calendar.getInstance()
    val mHour = mCalendar[Calendar.HOUR_OF_DAY]
    val mMinute = mCalendar[Calendar.MINUTE]

    // Value for storing time as a string
    val mTime = rememberSaveable { mutableStateOf("") }
    val mTime2 = rememberSaveable { mutableStateOf("") }

    // Creating a TimePicker dialod
    val mTimePickerDialog = TimePickerDialog(
        mContext,
        {_, mHour : Int, mMinute: Int ->
            mTime.value = "$mHour:$mMinute"
            edit=true
        }, mHour, mMinute, false
    )
    val mTimePickerDialog2 = TimePickerDialog(
        mContext,
        {_, mHour : Int, mMinute: Int ->
            mTime2.value = "$mHour:$mMinute"
            edit=true


        }, mHour, mMinute, false
    )


    if (mTime.value !="" || mTime2.value!=""){
        when(indexation){
            0-> {
                dates.lunes=mTime2.value+ " de " +mTime.value
                // onSelected.invoke(dates,false)
            }

            1->dates.martes=mTime2.value+ " de " +mTime.value
            2->dates.miercoles=mTime2.value+ " de " +mTime.value
            3->dates.jueves=mTime2.value+ " de " +mTime.value
            4->dates.viernes=mTime2.value+ " de " +mTime.value
            5->dates.sabado=mTime2.value+ " de " +mTime.value
            6->dates.domingo=mTime2.value+ " de " +mTime.value
        }

    }

    val listday= listOf("lunes","martes","miercoles","jueves","viernes","sabado","domingo")
    val listHoras= listOf(dates.lunes,dates.martes,dates.miercoles,dates.jueves,dates.viernes,dates.sabado,dates.domingo)

    Card(
        shape = RoundedCornerShape(30.dp),
        elevation = 10.dp,
        modifier = Modifier
            .padding(horizontal = 20.dp, vertical = 5.dp)
            .fillMaxWidth()
    ) {

        Column(horizontalAlignment = Alignment.CenterHorizontally) {
            BlockSpace()
            LazyRow(modifier = Modifier
                .fillMaxHeight()
                .background(Color.White))
            {
                var itemCount2 = listday.size
                if (false) itemCount2++

                items(count = itemCount2) { index ->
                    val auxIndex = index;
                    dayDate(listday[auxIndex],listHoras[auxIndex],onSelected={
                        indexation=auxIndex
                        mTimePickerDialog.show()
                        mTimePickerDialog2.show()

                    })

                }

            }

            Spacers()

            if (editb){


            Row() {
                IconButton(onClick = {
                    onSelected.invoke(dates,true)
                    //SELECCIONAR
                },) {
                    if (select){

                        Icon(  painter = painterResource(R.drawable.ic_library_add_check), contentDescription = "", modifier = Modifier.size(20.dp))

                    }else{
                        Icon(  painter = painterResource(R.drawable.ic_library_add), contentDescription = "", modifier = Modifier.size(20.dp))

                    }

                }
                Spacers()
                IconButton(onClick = {
                    Log.d("DATE CARDDATE",date.toString())

                    dateViewModel.deleteDate(date)
                    //BORRAR

                },) {
                    Icon(  painter = painterResource(R.drawable.ic_delete), contentDescription = "", modifier = Modifier.size(20.dp))

                }
                if (edit){
                    Spacers()
                    IconButton(onClick = {
                        Log.d("DATE CARDDATE",date.toString())
                        edit=false
                        upload=true
                        dateViewModel.uploadDate(dates)                    //BORRAR

                    },) {
                        Icon(  painter = painterResource(R.drawable.ic_upload), contentDescription = "", modifier = Modifier.size(20.dp))

                    }
                }else{

                    if(isLoadingUp && upload){
                        CircularProgressIndicator(modifier = Modifier
                            .size(20.dp)
                            .wrapContentHeight(Alignment.CenterVertically), color = Color.Black)
                        if (!isLoadingUp){
                            upload=false
                        }
                    }
                }






            }
            }
        }


    }
}

@Composable
fun dayDate(day:String,dayhour:String,onSelected: (Boolean) -> Unit){
    val delim =" de "

    Row{

        Spacers()


        Box(modifier = Modifier
            .height(IntrinsicSize.Min)
            .clickable {
                onSelected.invoke(false)
            })
        {
            Column(horizontalAlignment = Alignment.CenterHorizontally, modifier = Modifier.padding(horizontal = 20.dp)) {
                Spacers()
                Text(day,fontSize = 18.sp, fontFamily = FontFamily.SansSerif, fontWeight = FontWeight.Normal)

                Spacers()
                Row(verticalAlignment = Alignment.CenterVertically, modifier = Modifier
                    .padding(horizontal = 10.dp)
                    .weight(1f))
                {

                    Text(dayhour.split(delim)[0],fontSize = 14.sp, fontFamily = FontFamily.SansSerif, fontWeight = FontWeight.Bold,textDecoration = TextDecoration.Underline, modifier = Modifier
                        )
                    Spacers()

                    Text("hasta",fontSize = 12.sp, fontFamily = FontFamily.SansSerif, fontWeight = FontWeight.Normal, modifier = Modifier
                        )
                    Spacers()
                    Text(dayhour.split(delim)[1],fontSize = 14.sp, fontFamily = FontFamily.SansSerif, fontWeight = FontWeight.Bold,textDecoration = TextDecoration.Underline, modifier = Modifier
                        )

                }


                Spacers()
            }
        }

        Spacers()
    }


}


@Preview
@Composable
fun CardDateLoading(){
    Card(
        shape = RoundedCornerShape(30.dp),
        elevation = 10.dp,
        modifier = Modifier
            .padding(horizontal = 20.dp, vertical = 5.dp)
            .fillMaxWidth()
    ) {

        Column(horizontalAlignment = Alignment.CenterHorizontally) {
            BlockSpace()
            Spacers()

            LazyRow(modifier = Modifier
                .background(Color.White))
            {
                var itemCount2 = 3
                if (false) itemCount2++

                items(count = itemCount2) { index ->
                    val auxIndex = index;
                    DayDateLoading()

                }

            }

            BlockSpace()
            Row() {
                Box(
                    modifier = Modifier
                        .height(13.dp)
                        .width(10.dp)
                        .shimmer()
                        .background(Color.Gray)
                )
                Spacers()
                BlockSpace()
                Spacers()

                Box(
                    modifier = Modifier
                        .height(13.dp)
                        .width(10.dp)
                        .shimmer()
                        .background(Color.Gray)
                )


            }
            Spacers()
        }


    }
}
@Preview
@Composable
fun DayDateLoading(){
    Row{

        Spacers()


        Box(modifier = Modifier
            .height(IntrinsicSize.Min)
            .clickable {
            })
        {
            Column(horizontalAlignment = Alignment.CenterHorizontally, modifier = Modifier.padding(horizontal = 20.dp)) {
                Spacers()
                Box(
                    modifier = Modifier
                        .height(16.dp)
                        .width(80.dp)
                        .shimmer()
                        .background(Color.Gray)
                )
                Spacers()
                Row(verticalAlignment = Alignment.CenterVertically, modifier = Modifier
                    .padding(horizontal = 10.dp)
                    .weight(1f))
                {

                    Box(
                        modifier = Modifier
                            .height(16.dp)
                            .width(40.dp)
                            .shimmer()
                            .background(Color.Gray)
                    )
                    Spacers()

                    Box(
                        modifier = Modifier
                            .height(16.dp)
                            .width(20.dp)
                            .shimmer()
                            .background(Color.Gray)
                    )
                    Spacers()
                    Box(
                        modifier = Modifier
                            .height(16.dp)
                            .width(40.dp)
                            .shimmer()
                            .background(Color.Gray)
                    )

                }


                Spacers()
            }
        }

        Spacers()
    }

}