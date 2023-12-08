package app.ibiocd.appointment.appointment.view.components

import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.google.accompanist.pager.ExperimentalPagerApi
import com.google.accompanist.pager.PagerState
import com.google.accompanist.pager.pagerTabIndicatorOffset

@ExperimentalPagerApi
@Composable
fun TabsAppointment(pagerState: PagerState) {

    val list = listOf("Datos, Fecha y hora", "Confirmaciòn")


    val scope = rememberCoroutineScope()
    TabRow(selectedTabIndex = pagerState.currentPage,
        backgroundColor = Color.White,
        contentColor = Color.Black,
        indicator = { tabPositions ->
            TabRowDefaults.Indicator(
                Modifier.pagerTabIndicatorOffset(pagerState,tabPositions),
                height = 3.dp,
                color = Color.Black
            )

        }
    ) {
        list.forEachIndexed { index, _ ->
            Tab(
                text = {
                    Text(text = list[index],
                        color = if (pagerState.currentPage == index) Color.Black else Color.LightGray,
                        fontFamily = FontFamily.SansSerif,fontWeight = FontWeight.Normal, fontSize = 12.sp
                    )
                },
                selected = pagerState.currentPage == index ,
                onClick = {

                }
            )
        }

    }




}