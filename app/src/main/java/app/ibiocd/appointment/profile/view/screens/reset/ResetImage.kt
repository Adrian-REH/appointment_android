package com.nopalsoft.simple.rest.presentation.screens.profile.reset

import androidx.compose.foundation.layout.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import app.ibiocd.appointment.presentation.components.profile.CardResetImage
import app.ibiocd.appointment.presentation.normalized.BlockSpaces

@Composable
fun ResetImage(ID:String,
               medicalb:Boolean,
               isLoading:Boolean,
               imageUri:String,
               isImage:String,
               onOpen:()->Unit,
               onAddImage:()->Unit,
               BackMedical:()->Unit,
               BackPatient: () -> Unit){
    Column(modifier = Modifier.fillMaxWidth(), horizontalAlignment = Alignment.CenterHorizontally) {
        BlockSpaces()

        CardResetImage(ID,medicalb,isLoading,isImage,imageUri,onOpen,onAddImage,BackMedical,BackPatient)
    }
}