package com.nopalsoft.simple.rest.presentation

import android.content.Intent
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.google.accompanist.pager.ExperimentalPagerApi
import com.google.accompanist.pager.rememberPagerState
import app.ibiocd.appointment.home.view.HomeActivity
import app.ibiocd.appointment.presentation.components.profile.CardOffer
import app.ibiocd.appointment.home.view.components.LoadingCardStudiAppointment
import app.ibiocd.appointment.presentation.normalized.BlockSpace
import app.ibiocd.appointment.presentation.normalized.Spacers
import app.ibiocd.appointment.R

@OptIn(ExperimentalPagerApi::class)
@Composable
fun ListOffers(
    editb:Boolean,
    ID:String,
) {
    val pagerState = rememberPagerState(pageCount = 4)

    val context= LocalContext.current

    Scaffold(
        topBar = {
            Column(
                modifier = Modifier
                    .background(Color.White)

            ) {

                TopAppBar(
                    title = {  },
                    navigationIcon ={
                        IconButton(onClick = {
                            val intent = Intent(context, HomeActivity::class.java)
                            intent.putExtra("medicals","${true}")
                            intent.putExtra("edit","${true}")

                            // intent.putExtra("medicals","adfadsf")
                            context.startActivity(intent)

                        }) {
                            Icon( painter = painterResource(R.drawable.ic_arrow_back), contentDescription = "", modifier = Modifier.size(20.dp))
                        }
                    },
                    backgroundColor = Color.White,
                    elevation = 0.dp,
                    actions = {

                        IconButton(onClick = {
                            // context.startActivity(Intent(context, SessionActivity::class.java))
                        },) {
                            Icon(  painter = painterResource(R.drawable.ic_add_circle), contentDescription = "", modifier = Modifier.size(20.dp))

                        }
                    }
                )



            }

        }
    ) {

        LazyColumn(modifier = Modifier
            .fillMaxSize()
            .padding(bottom = 60.dp)
            .background(Color.White))
        {

            var isLoading=true



            item {
                BlockSpace()
                Row( verticalAlignment = Alignment.CenterVertically) {
                    Text(
                        text = "Study",
                        style = TextStyle(color = Color.Black, fontSize = 20.sp, fontWeight = FontWeight.Normal),
                        modifier = Modifier.padding(start = 20.dp)
                    )
                    Spacers()
                    IconButton(onClick = { /*TODO*/ }) {
                        Icon( painterResource(id = R.drawable.ic_more), contentDescription ="" , modifier = Modifier.size(25.dp), tint = Color.Gray)
                    }
                }

                Spacers()
            }

            var itemCount = 1
            if (isLoading) itemCount++

            items(count = itemCount) { index ->
                var auxIndex = index;
                if (isLoading) {
                    if (auxIndex == 0)
                        return@items LoadingCardStudiAppointment()
                    auxIndex--
                }
                CardOffer()
            }
        }

    }
}
