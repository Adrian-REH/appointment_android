package app.ibiocd.appointment.auth.view.screens

import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import app.ibiocd.appointment.auth.view.components.CardLogIn


@Composable
fun LogIn(NavBeginning: () -> Unit, NavReset: () -> Unit) {

    Column(
        modifier = Modifier
            .padding(16.dp)) {
        Box() {
            IconButton(
                onClick = NavBeginning, modifier = Modifier
                    .wrapContentWidth(Alignment.Start)
                    .wrapContentHeight(Alignment.Top)
            ) {
                Icon(Icons.Filled.ArrowBack, contentDescription = "")

            }

        }
        Column(
            horizontalAlignment = Alignment.CenterHorizontally, modifier = Modifier
                .fillMaxSize()
                .wrapContentHeight(Alignment.CenterVertically)
        ) {
            CardLogIn(onReset = {
                NavReset.invoke()
            })

        }
    }


}
