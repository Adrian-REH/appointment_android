package com.nopalsoft.simple.rest.presentation.screens.profile.reset

import androidx.compose.foundation.layout.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import app.ibiocd.appointment.presentation.components.profile.CardResetLocation
import app.ibiocd.appointment.presentation.normalized.BlockSpace
import app.ibiocd.appointment.presentation.normalized.BlockSpaces
import app.ibiocd.appointment.presentation.normalized.Spacers

@Composable
fun ResetLocation(ID:String,medicalb:Boolean,BackMedical:()->Unit,BackPatient: () -> Unit){
    Column(modifier = Modifier.fillMaxWidth(), horizontalAlignment = Alignment.CenterHorizontally) {
        Spacers()
        CardResetLocation(ID,medicalb,BackMedical,BackPatient)
    }
}