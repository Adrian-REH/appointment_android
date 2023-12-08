package app.ibiocd.appointment.auth.view.components

import android.app.Activity
import android.content.Intent
import android.widget.Toast
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Alignment.Companion.CenterHorizontally
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.google.accompanist.pager.*
import app.ibiocd.appointment.R
import app.ibiocd.appointment.home.view.HomeActivity
import app.ibiocd.appointment.presentation.normalized.BlockSpace
import app.ibiocd.appointment.presentation.normalized.SimpleSpacers
import app.ibiocd.appointment.presentation.normalized.Spacers
import app.ibiocd.appointment.auth.view.screens.SignInViewModel
import app.ibiocd.appointment.util.SessionScreen
import kotlinx.coroutines.launch
import java.util.regex.Matcher
import java.util.regex.Pattern


@OptIn(ExperimentalPagerApi::class)
@Composable
fun CardLogIn(
    viewModel: SignInViewModel = hiltViewModel(),
    onReset: () -> Unit
) {
    val focusManager = LocalFocusManager.current
    val activity = (LocalContext.current as? Activity)
    val scope = rememberCoroutineScope()
    val context = LocalContext.current
    val state = viewModel.signInState.collectAsState(initial = null)

    var isErrorEmail by rememberSaveable { mutableStateOf(false) }
    var isErrorPassw by rememberSaveable { mutableStateOf(false) }
    var user by rememberSaveable { mutableStateOf("") }
    var medical by rememberSaveable { mutableStateOf(true) }
    var password by rememberSaveable { mutableStateOf("") }
    var hidden by remember { mutableStateOf(true) } //1
    val pagerState = rememberPagerState(pageCount = 2)





    Card(
        shape = RoundedCornerShape(50.dp),
        elevation = 5.dp,
        modifier = Modifier
            .fillMaxWidth()

    ) {


        Column(
            modifier = Modifier
                .padding(bottom = 30.dp, top = 10.dp), horizontalAlignment = CenterHorizontally
        )
        {

            TabsSession(pagerState = pagerState)
            medical = pagerState.currentPage != 0



            BlockSpace()//10
            OutlinedTextField(
                value = user,
                onValueChange = { user = it },
                modifier = Modifier.testTag("user_session"),
                label = { Text("Correo") },
                keyboardActions = KeyboardActions(onDone = { focusManager.clearFocus() }),
                keyboardOptions = KeyboardOptions(imeAction = ImeAction.Next),
                trailingIcon = {
                    if (isErrorEmail) {
                        Icon(
                            painter = painterResource(R.drawable.ic_error),
                            contentDescription = "Error",
                            tint = Color.Red
                        )

                    }
                }
            )
            if (isErrorEmail) {
                Text(
                    text = "Error: Ingrese un correo valido",
                    color = MaterialTheme.colors.error,
                    style = MaterialTheme.typography.caption,
                    modifier = Modifier
                        .padding(start = 16.dp)
                        .testTag("user_error_session")
                )
            }

            Spacers()//10
            OutlinedTextField(
                value = password,
                modifier = Modifier.testTag("pass_session"),
                onValueChange = { password = it },
                label = { Text("Contraseña") },
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password),//2
                singleLine = true,
                visualTransformation =
                if (hidden) PasswordVisualTransformation() else VisualTransformation.None,//3
                trailingIcon = {// 4
                    IconButton(onClick = { hidden = !hidden },
                        modifier = Modifier.testTag(SessionScreen.TAG_IC_PASSWORD),
                    ) {
                        val vector = painterResource(//5
                            if (hidden) R.drawable.ic_visibility
                            else R.drawable.ic_visibility_off
                        )
                        val description =
                            if (hidden) "Ocultar contraseña" else "Revelar contraseña" //6
                        Icon(painter = vector, contentDescription = description)
                    }
                },

                keyboardActions = KeyboardActions(onDone = { focusManager.clearFocus() }),


                )
            if (isErrorPassw) {
                Text(
                    text = "Error: Ingrese una contraseña",
                    color = MaterialTheme.colors.error,
                    style = MaterialTheme.typography.caption,
                    modifier = Modifier
                        .padding(start = 16.dp)
                        .testTag("password_error_session")
                )
            }
            SimpleSpacers()//10
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(end = 60.dp)
                    .wrapContentWidth(Alignment.End)
            ) {
                Text(
                    "Has olvidado tu contraseña?",
                    modifier = Modifier
                        .clickable {

                            onReset.invoke()

                        },
                    fontFamily = FontFamily.SansSerif,
                    fontSize = 12.sp,
                    fontWeight = FontWeight.Medium,
                    color = Color.Blue
                )
            }

            Spacers()//10
            BlockSpace()//20

            Button(
                onClick = {
                    isErrorEmail = !esCorreo(user)
                    isErrorPassw = password == ""
                    scope.launch {
                        viewModel.loginUser(user, password,medical)

                    }


                }, modifier = Modifier
                    .fillMaxWidth()
                    .height(60.dp)
                    .padding(horizontal = 30.dp),
                colors = ButtonDefaults.buttonColors(backgroundColor = Color.White),
                shape = RoundedCornerShape(30),
                elevation = ButtonDefaults.elevation(
                    defaultElevation = 10.dp,
                    pressedElevation = 8.dp,
                    disabledElevation = 0.dp
                )
            ) {

                if (state.value?.isLoading == true ) CircularProgressIndicator(
                    modifier = Modifier.size(
                        5.dp
                    ), strokeWidth = 2.dp
                )
                else Text("Continuar")
            }
            Spacers()//10
            LaunchedEffect(key1 = state.value?.isSuccess) {

                scope.launch {

                    if (state.value?.isSuccess?.isNotEmpty() == true && user.isNotBlank()) {

                        val success = state.value?.isSuccess

                        //TODO para que es esto?
/*
                        if (medical) medicalViewModel.uploadMedical(isMedical)
                        else patientViewModel.uploadPatient(isPatient)
*/


                        val intent = Intent(context, HomeActivity::class.java)
                        intent.putExtra("medicals", "$medical")
                        intent.putExtra("id", user)

                        context.startActivity(intent)
                        activity?.finish()

                    }
                }
            }
            LaunchedEffect(key1 = state.value?.isError) {
                scope.launch {
                    if (state.value?.isError?.isNotEmpty() == true) {
                        val error = state.value?.isError
                        Toast.makeText(context, error, Toast.LENGTH_SHORT).show()

                    }
                }
            }

        }


    }


}

private fun esCorreo(texto: String): Boolean {
    var patroncito: Pattern =
        Pattern.compile("^[_A-Za-z0-9-\\+]+(\\.[_A-Za-z0-9-]+)*@[A-Za-z0-9-]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$")
    var comparador: Matcher = patroncito.matcher(texto)
    return comparador.find()

}


