package app.ibiocd.appointment.auth.view.screens

import android.util.Log
import android.widget.Toast
import androidx.compose.animation.animateContentSize
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import app.ibiocd.appointment.R
import app.ibiocd.appointment.model.ResetState
import app.ibiocd.appointment.presentation.normalized.BlockSpace
import app.ibiocd.appointment.presentation.normalized.Spacers
import com.google.accompanist.pager.ExperimentalPagerApi
import kotlinx.coroutines.launch


@Composable
fun ResetPassword(
    viewModel: ResetViewModel = hiltViewModel(),
    NavLogIn: () -> Unit,
    NavBeginning: () -> Unit
) {
    var emailState by rememberSaveable { mutableStateOf(false) }
    var codeState by rememberSaveable { mutableStateOf(false) }
    var passwordState by rememberSaveable { mutableStateOf(false) }

    var email by rememberSaveable { mutableStateOf("") }
    var codeInput by rememberSaveable { mutableStateOf("") }
    var isError by rememberSaveable { mutableStateOf(false) }

    val state = viewModel.resetState.collectAsState(initial = ResetState())
    val scope = rememberCoroutineScope()
    val context = LocalContext.current
    isError =
        state.value.codeVerifiedError || state.value.emailVerifySendError || state.value.passwordChangedError

    passwordState =
        !state.value.passwordChanged && state.value.codeVerified && state.value.emailVerifySend
    codeState =
        !state.value.passwordChanged && !state.value.codeVerified && state.value.emailVerifySend
    emailState =
        !state.value.passwordChanged && !state.value.codeVerified && !state.value.emailVerifySend
    Log.d(
        "VALOR",
        passwordState.toString() + codeState.toString() + emailState.toString() + " " + state.value.emailVerifySend
    )

    if (passwordState) codeInput = ""
    Column(
        modifier = Modifier
            .padding(16.dp)) {
            IconButton(
                onClick = NavBeginning, modifier = Modifier
                    .wrapContentWidth(Alignment.Start)
                    .wrapContentHeight(Alignment.Top)
            ) {
                Icon(Icons.Filled.ArrowBack, contentDescription = "")

            }


        Column(horizontalAlignment = Alignment.CenterHorizontally, modifier = Modifier
            .fillMaxSize()
            .wrapContentHeight(Alignment.CenterVertically)
        ) {
            Card(

                shape = RoundedCornerShape(30.dp),
                elevation = 5.dp,
                modifier = Modifier
                    .fillMaxWidth()
                    .wrapContentHeight(Alignment.CenterVertically)
            ) {

                Column(                verticalArrangement = Arrangement.Center,
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Spacers()
                    BlockSpace()

                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(horizontal = 20.dp), verticalAlignment = Alignment.CenterVertically
                    ) {
                        Spacers()
                        Text(
                            when {
                                emailState -> "Ingrese su Email"
                                passwordState -> "Ingrese su Clave"
                                codeState -> "Ingrese su Codigo"
                                isError -> {
                                    "Error"
                                }
                                else -> "Por favor intentelo mas tarde"
                            },
                            fontSize = 14.sp,
                            fontFamily = FontFamily.SansSerif,
                            fontWeight = FontWeight.Bold,
                            modifier = Modifier.animateContentSize()
                        )

                    }
                    BlockSpace()


                    OutlinedTextField(
                        value = when {
                            emailState -> email
                            passwordState -> codeInput
                            codeState -> codeInput
                            else -> "" // Valor por defecto, puedes ajustarlo según tus necesidades
                        },
                        onValueChange = {
                            when {
                                emailState -> email = it
                                passwordState -> codeInput = it
                                codeState -> codeInput = it
                            }
                        },
                        label = {
                            Text(
                                when {
                                    emailState -> "Email"
                                    passwordState -> "Clave"
                                    codeState -> "Codigo"
                                    else -> {
                                        ""
                                    }
                                }
                            )
                        }
                    )
                    BlockSpace()
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .wrapContentWidth(Alignment.CenterHorizontally)
                    ) {
                        Button(onClick = {
                            scope.launch {
                                when {
                                    emailState -> viewModel.sendEmailCode(email)
                                    codeState -> viewModel.verifyCode(email, codeInput)
                                    passwordState -> viewModel.changePassword(email, codeInput)
                                    state.value.isLoading -> null
                                }

                            }
                        }) {
                            //TODO agregar circlular progress
                            if (!state.value.isLoading) Text(
                                when {
                                    emailState -> "Solicitar un codigo"
                                    passwordState -> "Verificar codigo"
                                    codeState -> "Confirmar el cambio"
                                    else -> {
                                        ""
                                    }
                                },
                                fontSize = 10.sp,
                                fontFamily = FontFamily.SansSerif,
                                fontWeight = FontWeight.Light
                            )
                            else CircularProgressIndicator(
                                modifier = Modifier.size(
                                    20.dp
                                ), strokeWidth = 2.dp, color = Color.White
                            )
                        }
                        Spacers()
                        if (emailState) {
                            OutlinedButton(
                                modifier = Modifier.animateContentSize(),
                                onClick = {
                                    scope.launch {
                                        NavBeginning.invoke()

                                    }
                                }) {
                                Text(
                                    "Salir",
                                    fontSize = 10.sp,
                                    fontFamily = FontFamily.SansSerif,
                                    fontWeight = FontWeight.Light
                                )
                            }
                        }

                    }
                    Spacers()


                }


            }
        }


    }

    if (codeState) {

        BlockSpace()
        OutlinedButton(
            modifier = Modifier
                .animateContentSize()
                .padding(horizontal = 20.dp, vertical = 5.dp)
                .fillMaxWidth(),
            onClick = {
                scope.launch {
                    viewModel.sendEmailCode(email)
                }
            }) {
            Text(
                "Reenviar codigo",
                fontSize = 10.sp,
                fontFamily = FontFamily.SansSerif,
                fontWeight = FontWeight.Light
            )
        }
    }
    LaunchedEffect(key1 = state.value.passwordChanged) {
        scope.launch {
            if (state.value.passwordChanged && state.value.codeVerified && state.value.emailVerifySend) {
                Toast.makeText(context, "Se cambio su clave", Toast.LENGTH_LONG).show()

                NavLogIn.invoke()
            }
        }

    }
    LaunchedEffect(key1 = state.value.emailVerifySendError) {
        scope.launch {
            if (state.value.emailVerifySendError) {
                val error = state.value.emailVerifySendError
                Toast.makeText(context, error.toString(), Toast.LENGTH_LONG).show()
            }
        }
    }
}

