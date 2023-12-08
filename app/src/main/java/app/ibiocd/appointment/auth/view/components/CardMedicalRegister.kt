package app.ibiocd.appointment.auth.view.components

import android.util.Log
import android.widget.Toast
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import app.ibiocd.appointment.R
import app.ibiocd.appointment.presentation.normalized.BlockSpace
import app.ibiocd.appointment.presentation.normalized.Spacers
import app.ibiocd.appointment.profile.model.MedicalRequest
import app.ibiocd.appointment.profile.viewmodel.MedicalViewModel
import kotlinx.coroutines.launch
import java.util.regex.Matcher
import java.util.regex.Pattern

@Composable
fun CardMedicalRegister(
    NavLogIn: () -> Unit,
    medicalViewModel: MedicalViewModel = hiltViewModel()
) {
    var form by remember { mutableStateOf(MedicalRequest()) }
    var intent by remember { mutableStateOf(false) }

    var passwordconfirm by remember { mutableStateOf("") }
    var isErrorPassword by rememberSaveable { mutableStateOf(false) }
    var isErrorEmail by rememberSaveable { mutableStateOf(false) }
    var hidden by remember { mutableStateOf(true) } //1
    var hiddenConfirm by remember { mutableStateOf(true) } //1
    val scope = rememberCoroutineScope()
    val context = LocalContext.current
    val state = medicalViewModel.registerState.collectAsState(initial = null)

    Card(
        shape = RoundedCornerShape(10.dp),
        elevation = 5.dp,
        modifier = Modifier
            .padding(horizontal = 30.dp)
            .fillMaxWidth()

    ) {


        Column(
            modifier = Modifier

                .padding(vertical = 16.dp), horizontalAlignment = Alignment.CenterHorizontally
        )
        {

            OutlinedTextField(
                value = form.name,
                modifier = Modifier.testTag("Name_medical"),
                onValueChange = { form = form.copy(name = it)   },
                label = { Text("Nombre") },
                trailingIcon = {
                    if (form.name=="" && intent) {
                        Icon(
                            painter = painterResource(R.drawable.ic_error),
                            contentDescription = "Error",
                            tint = Color.Red
                        )

                    }
                }
            )

            Spacers()//10

            OutlinedTextField(
                value = form.dni,
                modifier = Modifier.testTag("Dni_medical"),
                onValueChange = { form = form.copy(dni = it)  },
                label = { Text("DNI") },
                trailingIcon = {
                    if (form.dni=="" && intent) {
                        Icon(
                            painter = painterResource(R.drawable.ic_error),
                            contentDescription = "Error",
                            tint = Color.Red
                        )

                    }
                }
            )
            Spacers()//10
            OutlinedTextField(
                value = form.username,
                modifier = Modifier.testTag("Username_medical"),
                onValueChange = { form = form.copy(username = it) },
                label = { Text("Username") },
                trailingIcon = {
                    if (form.username=="" && intent) {
                        Icon(
                            painter = painterResource(R.drawable.ic_error),
                            contentDescription = "Error",
                            tint = Color.Red
                        )

                    }
                }
            )
            Spacers()//10
            OutlinedTextField(
                value = form.tuition,
                modifier = Modifier.testTag("Tuition_medical"),
                onValueChange = { form = form.copy(tuition = it) },
                label = { Text("Matricula") },
                trailingIcon = {
                    if (form.tuition=="" && intent) {
                        Icon(
                            painter = painterResource(R.drawable.ic_error),
                            contentDescription = "Error",
                            tint = Color.Red
                        )

                    }
                }
            )
            Spacers()//10
            OutlinedTextField(
                value = form.email.replace("\\n\\r", ""),
                modifier = Modifier.testTag("Correo_medical"),
                onValueChange = {
                    form = form.copy(email = it.replace("\\n\\r", ""))
                    isErrorEmail = !esCorreo(form.email)
                },
                label = { Text("Correo") },
                trailingIcon = {
                    if ((form.email=="" || isErrorEmail) && intent) {
                        Icon(
                            painter = painterResource(R.drawable.ic_error),
                            contentDescription = "Error",
                            tint = Color.Red
                        )

                    }
                }
            )
            Spacers()//10
            OutlinedTextField(
                value = form.password,
                modifier = Modifier.testTag("Pass_medical"),
                onValueChange = { form = form.copy(password = it) },
                label = { Text("Contraseña") },
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password),//2
                singleLine = true,
                visualTransformation =
                if (hidden) PasswordVisualTransformation() else VisualTransformation.None,//3
                trailingIcon = {// 4
                    IconButton(onClick = { hidden = !hidden }) {
                        val vector = painterResource(//5
                            if (hidden) R.drawable.ic_visibility
                            else R.drawable.ic_visibility_off
                        )
                        val description =
                            if (hidden) "Ocultar contraseña medicals" else "Revelar contraseña medicals" //6
                        Icon(painter = vector, contentDescription = description)
                    }
                    if (form.password=="" && intent) {
                        Icon(
                            painter = painterResource(R.drawable.ic_error),
                            contentDescription = "Error",
                            tint = Color.Red
                        )

                    }
                }
            )
            Spacers()//10
            OutlinedTextField(
                value = passwordconfirm,
                modifier = Modifier.testTag("ConfPass_medical"),
                onValueChange = {
                    passwordconfirm = it
                    isErrorPassword = passwordconfirm != form.password},
                label = { Text("Confirmar contraseña") },
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password),//2
                singleLine = true,
                visualTransformation =
                if (hiddenConfirm) PasswordVisualTransformation() else VisualTransformation.None,//3
                trailingIcon = {
                    IconButton(onClick = { hiddenConfirm = !hiddenConfirm }) {
                        val vector = painterResource(//5
                            if (hiddenConfirm) R.drawable.ic_visibility
                            else R.drawable.ic_visibility_off
                        )
                        val description =
                            if (hiddenConfirm) "Ocultar conf contraseña" else "Revelar conf contraseña" //6
                        Icon(painter = vector, contentDescription = description)
                    }
                    if ( passwordconfirm=="" && intent) {
                        Icon(
                            painter = painterResource(R.drawable.ic_error),
                            contentDescription = "Error",
                            tint = Color.Red
                        )

                    }
                }
            )
            if (isErrorPassword) {
                Text(
                    text = "Las contraseñas no coinciden",
                    color = MaterialTheme.colors.error,
                    style = MaterialTheme.typography.caption,
                    modifier = Modifier
                        .padding(start = 16.dp)
                        .testTag("medical_confirm_pass_error")
                )
            }

            Spacers()//10
            BlockSpace()//20

            Button(modifier = Modifier
                .fillMaxWidth()
                .height(60.dp)
                .padding(horizontal = 30.dp),
                colors = ButtonDefaults.buttonColors(backgroundColor = Color.White),
                shape = RoundedCornerShape(10),
                onClick = {
                    scope.launch {
                        intent = true
                        if (!isErrorPassword && !isErrorEmail) {

                            scope.launch {
                                if (form.email.isNotBlank() && form.password.isNotBlank() && passwordconfirm.isNotBlank() && form.name.isNotBlank() && form.dni.isNotBlank() && form.tuition.isNotBlank()) {
                                    //viewModel.registerUser(email, password)
                                    Log.d("Inicio la peticion de registro","")

                                    form = form.copy(tuition = form.tuition +":f")
                                    medicalViewModel.addMedical(form)
                                    //patientViewModel.addPatient(Name, dni, email,password, username)


                                } else {
                                    Toast.makeText(
                                        context,
                                        "Por favor complete el formulario",
                                        Toast.LENGTH_SHORT
                                    ).show()
                                }


                            }
                        }else {
                            Toast.makeText(
                                context,
                                "Por favor verifique los datos ingresados",
                                Toast.LENGTH_LONG
                            ).show()
                        }
                    }
                }
            ) {

                if (state.value?.isLoading == true) CircularProgressIndicator(
                    modifier = Modifier.size(
                        20.dp
                    ), strokeWidth = 2.dp
                )
                else Text("Confirmar")
            }
            LaunchedEffect(key1 = state.value?.isSuccess) {
                scope.launch {
                    if (state.value?.isSuccess?.isNotEmpty() == true) {
                        val success = state.value?.isSuccess
                        Toast.makeText(
                            context,
                            "Cuenta creada!: Verifique su Email",
                            Toast.LENGTH_LONG
                        ).show()

                        NavLogIn()
                    }
                }
            }
            LaunchedEffect(key1 = state.value?.isError) {
                scope.launch {
                    if (state.value?.isError?.isNotEmpty() == true) {
                        val success = state.value?.isError
                        Toast.makeText(context, success, Toast.LENGTH_SHORT).show()
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
