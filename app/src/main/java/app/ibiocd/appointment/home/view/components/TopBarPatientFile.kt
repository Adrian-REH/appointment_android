package app.ibiocd.appointment.home.view.components

import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.google.accompanist.pager.ExperimentalPagerApi
import com.google.accompanist.pager.PagerState
import com.google.accompanist.pager.pagerTabIndicatorOffset
import app.ibiocd.appointment.UserViewModel
import kotlinx.coroutines.launch
import app.ibiocd.appointment.R


@ExperimentalPagerApi
@Composable
fun TabsPatient(pagerState: PagerState, viewModel: UserViewModel = hiltViewModel()) {

    val list = listOf("Historial", "Estudio","Lab","Receta")
    val lists = listOf(
        painterResource(id = R.drawable.ic_list_box),
        painterResource(id = R.drawable.ic_bookmarks),
        painterResource(id = R.drawable.ic_lab),
        painterResource(id = R.drawable.ic_paper)

    )
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

                    scope.launch {
                        pagerState.animateScrollToPage(index)
                    }
                },
                icon = {
                    Icon(lists[index] , contentDescription = null)
                }
            )
        }

    }



}