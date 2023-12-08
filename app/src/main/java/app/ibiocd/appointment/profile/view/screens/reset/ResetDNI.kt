package com.nopalsoft.simple.rest.presentation.screens.profile.reset

import androidx.compose.foundation.layout.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import app.ibiocd.appointment.presentation.components.profile.CardResetIdentity
import app.ibiocd.appointment.presentation.normalized.BlockSpaces


@Composable
fun ResetDNI(ID:String,medicalb:Boolean,BackMedical:()->Unit,BackPatient: () -> Unit){
    Column(modifier = Modifier.fillMaxWidth(), horizontalAlignment = Alignment.CenterHorizontally) {
        BlockSpaces()
        CardResetIdentity(ID,medicalb,BackMedical,BackPatient)
    }
}