package app.ibiocd.appointment.profile.viewmodel

import android.app.Application
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import app.ibiocd.appointment.profile.datasource.entity.Medical
import app.ibiocd.appointment.model.RegisterState
import app.ibiocd.appointment.profile.model.MedicalRequest
import com.google.android.gms.tasks.OnCompleteListener
import com.google.firebase.messaging.FirebaseMessaging
import app.ibiocd.appointment.profile.model.repository.MedicalRepository
import app.ibiocd.appointment.util.ApiResource
import app.ibiocd.appointment.util.UtilJwtParser
import app.ibiocd.appointment.util.UtilSharedToken
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch
import javax.inject.Inject


@HiltViewModel
class MedicalViewModel @Inject constructor(
    private val medicalRepository: MedicalRepository,
    application: Application,

    ) : ViewModel() {
    private val utilToken = UtilSharedToken(application.applicationContext)

    private val _isLoading: MutableLiveData<Boolean> by lazy {
        MutableLiveData<Boolean>(false)
    }
    val isLoading: LiveData<Boolean> get() = _isLoading


    private val _Medical: MutableLiveData<Medical> by lazy {
        MutableLiveData<Medical>(Medical("", "", "", "", "", "", "", "", "", "", ""))
    }

    val medical: LiveData<Medical> get() = _Medical

    private val _id: MutableLiveData<String> by lazy {
        MutableLiveData<String>("")
    }
    val id: LiveData<String> get() = _id

    //MEDICAL
    val medicals: LiveData<List<Medical>> by lazy {
        medicalRepository.getAllMedical()
    }

    val _registerState=  Channel<RegisterState>()
    val registerState=  _registerState.receiveAsFlow()

    fun addMedical(
        medicalRequest: MedicalRequest
    ) {
        FirebaseMessaging.getInstance().token.addOnCompleteListener(
            OnCompleteListener { task ->
                if (!task.isSuccessful) {
                    return@OnCompleteListener
                }
                if (_isLoading.value == false)
                    viewModelScope.launch(Dispatchers.IO) {
                        medicalRequest.tokenNot = task.result
                        medicalRepository.postMedical(medicalRequest
                        ).collect {
                            when (it) {
                                is ApiResource.Error -> {
                                    Log.e("Error: Medical add", it.message + "")
                                    _registerState.send(RegisterState(isLoading = false,isError = it.message))

                                }
                                is ApiResource.Loading -> {
                                    _registerState.send(RegisterState(isLoading = true))
                                }
                                is ApiResource.Success -> {
                                    _registerState.send(RegisterState(isLoading = false,isSuccess = it.result))
                                    _id.postValue(it.result)
                                    Log.d("Success", "Medical add")
                                }
                            }
                        }

                    }


            })


    }

    suspend fun loadMedical() = viewModelScope.launch(Dispatchers.IO) {
        if (_isLoading.value == false){

            val username= utilToken.getJwt()?.let { UtilJwtParser(it).getUsername() }.orEmpty()
            medicalRepository.getByUsername(username).collect {
                when (it) {
                    is ApiResource.Error -> {
                        Log.e("Error: Load Medical", it.message + "")
                    }
                    is ApiResource.Loading -> {
                        _isLoading.postValue(true)

                    }
                    is ApiResource.Success -> {
                        _isLoading.postValue(false)
                        _Medical.postValue(it.result!!)
                        _id.postValue(it.result._id)
                        Log.d("Success", "Medical Login")
                    }
                }
            }
        }
    }

    suspend fun loadAllMedical() = viewModelScope.launch(Dispatchers.IO) {

        if (_isLoading.value == false)

            medicalRepository.getMedical().collect {
                when (it) {
                    is ApiResource.Error -> {
                        Log.e("Error: Medical Login", it.message + "")
                    }
                    is ApiResource.Loading -> {
                        _isLoading.postValue(true)

                    }
                    is ApiResource.Success -> {
                        _isLoading.postValue(false)
                        Log.d("Success", "Medical Login")
                    }
                }
            }


    }

    fun uploadMedical(medical: Medical) {
        FirebaseMessaging.getInstance().token.addOnCompleteListener(
            OnCompleteListener { task ->
                if (!task.isSuccessful) {
                    return@OnCompleteListener
                }
                medical.token_not = task.result
                if (_isLoading.value == false)
                    viewModelScope.launch(Dispatchers.IO) {
                        medicalRepository.putMedical(medical).collect {
                            when (it) {
                                is ApiResource.Error -> {
                                    Log.e("Error: Upload medical", it.message + "")
                                }
                                is ApiResource.Loading -> {
                                    _isLoading.postValue(true)

                                }
                                is ApiResource.Success -> {
                                    _isLoading.postValue(false)
                                    Log.d("Success", "Upload medical")
                                }
                            }
                        }

                    }

            })

    }

    suspend fun deleteAllMedical() {
        viewModelScope.launch(Dispatchers.IO) {
            medicalRepository.deleteAllMedical()
        }
    }


}