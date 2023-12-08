package app.ibiocd.appointment.auth.view.screens

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import app.ibiocd.appointment.auth.datasource.ResetPasswordRequest
import app.ibiocd.appointment.auth.datasource.VerifyCodeRequest
import app.ibiocd.appointment.auth.model.AuthRepository
import app.ibiocd.appointment.model.ResetState
import app.ibiocd.appointment.util.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch
import org.mindrot.jbcrypt.BCrypt
import javax.inject.Inject


@HiltViewModel
class ResetViewModel @Inject constructor(
    private val repository: AuthRepository

):ViewModel() {
    private val _resetState = MutableStateFlow(ResetState())

    val resetState: StateFlow<ResetState> get() = _resetState



    suspend fun sendEmailCode(email:String) = viewModelScope.launch {

        repository.sendEmailCode(email).collect{ result ->
            when(result){
                is Resource.Success ->{
                    _resetState.value = _resetState.value.copy(isLoading = false, emailVerifySend = true)
                }
                is Resource.Loading ->{
                    _resetState.value = _resetState.value.copy(isLoading = true)

                }
                is Resource.Error ->{

                    _resetState.value = _resetState.value.copy(isLoading = true,emailVerifySendError = true)
                    Log.e("Error: Send Email Code",result.message.toString())

                }
            }
        }
    }

    suspend fun verifyCode(email:String,code:String) = viewModelScope.launch {

        repository.verifyCode(VerifyCodeRequest(code,email)).collect{ result ->
            when(result){
                is Resource.Success ->{
                    _resetState.value = _resetState.value.copy(isLoading = false, codeVerified = true)
                    Log.d("verifyCode",result.message.toString())
                }
                is Resource.Loading ->{
                    _resetState.value = _resetState.value.copy(isLoading = true)

                }
                is Resource.Error ->{
                    _resetState.value = _resetState.value.copy(isLoading = false, codeVerifiedError = true)

                    Log.e("Error: Verify Code",result.message.toString())

                }
            }
        }
    }
    suspend fun changePassword(email:String, password:String) = viewModelScope.launch {
        val hash= BCrypt.hashpw(password, BCrypt.gensalt(10))

        repository.resetPassword(ResetPasswordRequest(hash,email)).collect{ result ->
            when(result){
                is Resource.Success ->{
                    _resetState.value = _resetState.value.copy(isLoading = false, passwordChanged = true)

                    Log.d("changePassword",result.message.toString())
                }
                is Resource.Loading ->{
                    _resetState.value = _resetState.value.copy(isLoading = true)

                }
                is Resource.Error ->{
                    _resetState.value = _resetState.value.copy(isLoading = false, passwordChangedError = true)

                    Log.e("Error: Reset Password",result.message.toString())

                }
            }
        }
    }


}