package com.nopalsoft.simple.rest.presentation.screens.profile.reset

import androidx.compose.foundation.layout.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import app.ibiocd.appointment.presentation.components.profile.CardResetProfession
import app.ibiocd.appointment.presentation.normalized.BlockSpaces

@Composable
fun ResetProfession(ID:String,BackMedical:()->Unit

) {
    Column(modifier = Modifier.fillMaxWidth(), horizontalAlignment = Alignment.CenterHorizontally) {
        BlockSpaces()
        CardResetProfession(ID,BackMedical)
    }
}
