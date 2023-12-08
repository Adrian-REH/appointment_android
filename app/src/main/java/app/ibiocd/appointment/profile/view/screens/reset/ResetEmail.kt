package com.nopalsoft.simple.rest.presentation.screens.profile.reset

import androidx.compose.foundation.layout.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import app.ibiocd.appointment.presentation.components.profile.CardResetEmail
import app.ibiocd.appointment.presentation.normalized.BlockSpaces

@Composable
fun ResetEmail(ID:String,medicalb:Boolean,BackMedical:()->Unit,BackPatient: () -> Unit){
    Column(modifier = Modifier.fillMaxWidth(), horizontalAlignment = Alignment.CenterHorizontally) {
        BlockSpaces()
        CardResetEmail(ID,medicalb,BackMedical,BackPatient)
    }
}