package app.ibiocd.appointment.auth.view.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.google.accompanist.pager.ExperimentalPagerApi
import com.google.accompanist.pager.HorizontalPager
import com.google.accompanist.pager.rememberPagerState
import app.ibiocd.appointment.auth.view.components.CardMedicalRegister
import app.ibiocd.appointment.auth.view.components.CardPatientRegister
import app.ibiocd.appointment.auth.view.components.TabsSession
import app.ibiocd.appointment.presentation.normalized.BlockSpaces
import app.ibiocd.appointment.presentation.normalized.Spacers

@OptIn(ExperimentalPagerApi::class)
@Composable
fun CheckIn(NavBeginning: () -> Unit,NavLogIn: () -> Unit) {
    val pagerState = rememberPagerState(pageCount = 2)




    Column (
        modifier = Modifier.fillMaxSize().background(Color.White)
    )
    {

        IconButton(onClick = NavBeginning, modifier = Modifier.padding(16.dp).wrapContentWidth(Alignment.Start)) {
            Icon( Icons.Filled.ArrowBack, contentDescription = "")
        }
        TabsSession(pagerState= pagerState)

        Spacers()

        HorizontalPager(state = pagerState, verticalAlignment = Alignment.Top) { page ->
            when(page){
                0 -> CardPatientRegister(NavLogIn)
                1 -> CardMedicalRegister(NavLogIn)
            }
        }
    }


}


