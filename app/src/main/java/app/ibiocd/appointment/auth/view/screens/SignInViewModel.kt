package app.ibiocd.appointment.auth.view.screens

import android.app.Application
import android.content.Context
import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import app.ibiocd.appointment.auth.model.AuthRepository
import app.ibiocd.appointment.model.SignInState
import app.ibiocd.appointment.util.Resource
import app.ibiocd.appointment.util.UtilJwtParser
import app.ibiocd.appointment.util.UtilSharedToken
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch
import javax.inject.Inject


@HiltViewModel
class SignInViewModel @Inject constructor(
    private val repository: AuthRepository,
    application: Application,

    ) : ViewModel() {
    val _signInState = Channel<SignInState>()
    val signInState = _signInState.receiveAsFlow()

    private val utilToken = UtilSharedToken(application.applicationContext)

    suspend fun loginUser(email: String, password: String, isMedical: Boolean) = viewModelScope.launch {
        repository.loginUser(email, password,isMedical).collect { result ->
            when (result) {
                is Resource.Success -> {
                    utilToken.saveJwt(result.data.toString())
                    _signInState.send(SignInState(isSuccess = "Sign In Success"))


                }
                is Resource.Loading -> {
                    _signInState.send(SignInState(isLoading = true))

                }
                is Resource.Error -> {
                    _signInState.send(SignInState(isError = "sad"))
                    Log.e("ERROR: Login user", result.message.toString())

                }
            }


        }
    }

}