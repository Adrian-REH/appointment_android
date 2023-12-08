package com.nopalsoft.simple.rest.presentation

import android.app.Activity
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import app.ibiocd.appointment.R
import app.ibiocd.appointment.presentation.normalized.BlockSpace

@Composable
fun HomeSelect(NavSimpleIn: () -> Unit,NavLabIn: () -> Unit) {

    val activity = (LocalContext.current as? Activity)



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
                            activity?.finish()

                        }) {
                            Icon( painter = painterResource(R.drawable.ic_arrow_back), contentDescription = "Fin", modifier = Modifier.size(20.dp))
                        }
                    },
                    backgroundColor = Color.White,
                    elevation = 0.dp
                )



            }

        }
    ) {
        Column(horizontalAlignment = Alignment.CenterHorizontally, modifier = Modifier
            .padding(horizontal = 30.dp)
            .fillMaxSize()
            .wrapContentHeight(Alignment.CenterVertically)
            ) {


            OutlinedButton(onClick = NavSimpleIn, modifier = Modifier
                .fillMaxWidth()
                .height(60.dp),shape = RoundedCornerShape(50),
                elevation = ButtonDefaults.elevation(
                    defaultElevation = 6.dp,
                    pressedElevation = 10.dp,
                    disabledElevation = 0.dp
                )
                ,



                ) {
                Text(text = "MEDICAL", color = Color.Black)

            }
            BlockSpace()

            BlockSpace()
            OutlinedButton(onClick = NavLabIn, modifier = Modifier
                .fillMaxWidth()
                .height(60.dp),shape = RoundedCornerShape(50),
                elevation = ButtonDefaults.elevation(
                    defaultElevation = 6.dp,
                    pressedElevation = 10.dp,
                    disabledElevation = 0.dp
                )) {
                Text(text = "LABORATORY", color = Color.Black)

            }



        }


    }



}
