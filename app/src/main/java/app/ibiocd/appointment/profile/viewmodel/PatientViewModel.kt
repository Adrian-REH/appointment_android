package app.ibiocd.appointment.profile.viewmodel

import android.app.Application
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import app.ibiocd.appointment.model.Patient
import app.ibiocd.appointment.model.RegisterState
import app.ibiocd.appointment.profile.model.PatientRequest
import com.google.android.gms.tasks.OnCompleteListener
import com.google.firebase.messaging.FirebaseMessaging
import app.ibiocd.appointment.profile.model.repository.PatientRepository
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
class PatientViewModel @Inject constructor(
    private val patientRepository: PatientRepository,
    application: Application,

    ) : ViewModel() {
    private val utilToken = UtilSharedToken(application.applicationContext)

    private val _isLoading: MutableLiveData<Boolean> by lazy {
        MutableLiveData<Boolean>(false)
    }
    val isLoading: LiveData<Boolean> get() = _isLoading


    val patient: LiveData<Patient> get() = _patient
    private val _patient: MutableLiveData<Patient> by lazy {
        MutableLiveData<Patient>(Patient("","","","","","","",""))
    }
    private val _id: MutableLiveData<String> by lazy {
        MutableLiveData<String>("")
    }
    val id: LiveData<String> get() = _id

    //PATIENT
    val patients: LiveData<List<Patient>> by lazy {
        patientRepository.getAllPatientDao()
    }
    suspend fun deleteAllPatient() {
        viewModelScope.launch(Dispatchers.IO) {
            patientRepository.deleteAllPatient()
        }
    }

    val _registerState=  Channel<RegisterState>()
    val registerState=  _registerState.receiveAsFlow()

    suspend fun loadAllPatient() {
        if (_isLoading.value == false)
            viewModelScope.launch(Dispatchers.IO) {
                patientRepository.getAllPatient().collect{
                    when (it) {
                        is ApiResource.Error -> {
                            Log.e("Error: Cargar paciente", it.message + "")
                        }
                        is ApiResource.Loading -> {
                            _isLoading.postValue(true)

                        }
                        is ApiResource.Success -> {
                            _isLoading.postValue(false)
                            Log.d("Success", "Paciente cargado")
                        }
                    }
                }
            }
    }
    suspend fun addPatient( patientRequest : PatientRequest){
        FirebaseMessaging.getInstance().token.addOnCompleteListener(
            OnCompleteListener { task ->
                if (!task.isSuccessful) {
                    return@OnCompleteListener
                }
                patientRequest.tokenNot = task.result
                viewModelScope.launch(Dispatchers.IO) {
                    patientRepository.postPatient(patientRequest).collect {
                        when (it) {
                            is ApiResource.Error -> {
                                Log.e("Error: Patient add", it.message + "")
                                _registerState.send(RegisterState(isLoading = false))
                                _registerState.send(RegisterState(isError = it.message))

                            }
                            is ApiResource.Loading -> {
                                _registerState.send(RegisterState(isLoading = true))
                            }
                            is ApiResource.Success -> {
                                _registerState.send(RegisterState(isLoading = false))
                                _registerState.send(RegisterState(isSuccess = it.result))
                                _id.postValue(it.result)
                                Log.d("Success", "Patient add")
                            }
                        }
                    }
                }

            })




    }
    suspend fun loadPatient() = viewModelScope.launch(Dispatchers.IO ) {
        if (_isLoading.value == false)
            {
                val username= utilToken.getJwt()?.let { UtilJwtParser(it).getUsername() }.orEmpty()

                patientRepository.getByUsername(username).collect{
                    when (it) {
                        is ApiResource.Error -> {
                            Log.e("Error: Cargar paciente al logearse", it.message + "")
                        }
                        is ApiResource.Loading -> {
                            _isLoading.postValue(true)

                        }
                        is ApiResource.Success -> {
                            _isLoading.postValue(false)
                            _patient.postValue(it.result!!)
                            _id.postValue(it.result._id)
                            Log.d("Success", "Paciente cargado logeado")
                        }
                    }
                }
                Log.d("Patientlogin","Successful")
            }


    }
    fun uploadPatient(patient: Patient) {
        FirebaseMessaging.getInstance().token.addOnCompleteListener(
            OnCompleteListener { task ->
                if (!task.isSuccessful) {
                    return@OnCompleteListener
                }
                patient.token_not = task.result
                if (_isLoading.value == false)
                    viewModelScope.launch(Dispatchers.IO) {
                        _isLoading.postValue(true)
                        patientRepository.putPatient(patient).collect{
                            when (it) {
                                is ApiResource.Error -> {
                                    Log.e("Error: Actualizar paciente", it.message + "")
                                }
                                is ApiResource.Loading -> {
                                    _isLoading.postValue(true)

                                }
                                is ApiResource.Success -> {
                                    _isLoading.postValue(false)
                                    Log.d("Success", "upload Patient")
                                }
                            }
                        }

                    }
            })

    }





}