package app.ibiocd.appointment.appointment.viewmodel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import app.ibiocd.appointment.appointment.model.AppointmentState
import app.ibiocd.appointment.model.*
import app.ibiocd.appointment.util.ApiResource
import com.nopalsoft.simple.rest.repository.AppointmetRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

/**
 *@author Adrian Ramon Elias Herrera
 *
 *
 *
 *
 */
@HiltViewModel
class AppointmentViewModel @Inject constructor(
    private val appointmentRepository: AppointmetRepository
) : ViewModel() {

    val _appointmentState = Channel<AppointmentState>()
    val appointmentState = _appointmentState.receiveAsFlow()

    private val _isLoading: MutableLiveData<Boolean> by lazy {
        MutableLiveData<Boolean>(false)
    }
    val isLoading: LiveData<Boolean> get() = _isLoading

    //APPOINTMENT
    val appointment: LiveData<List<Appointment>> by lazy {
        appointmentRepository.getAllAppointmentDao()
    }

    suspend fun getAllAppointment() = viewModelScope.launch(Dispatchers.IO) {

        appointmentRepository.getAllAppointment().collect {
            when (it) {
                is ApiResource.Error -> {
                    Log.e("Error: Appointments not founds", it.message + "")
                    _isLoading.postValue(false)

                }
                is ApiResource.Loading -> {
                    _isLoading.postValue(true)

                }
                is ApiResource.Success -> {
                    _isLoading.postValue(false)
                    Log.d("Success", "Appointments Founds")

                }
            }
        }


    }

    suspend fun addAppointment(appointment: Appointment) = viewModelScope.launch(Dispatchers.IO) {
        appointmentRepository.postAppointment(appointment).collect {
            when (it) {
                is ApiResource.Error -> {
                    _appointmentState.send(AppointmentState(isError = it.message))
                    Log.e("Error: Appointment add", it.message + "")
                }
                is ApiResource.Loading -> {
                    _appointmentState.send(AppointmentState(isLoading = true))

                }
                is ApiResource.Success -> {
                    _appointmentState.send(AppointmentState(isSuccess = it.result!!))

                    Log.d("Success", "Appointment creado")

                }
            }

        }
    }

    fun deleteAppointment(toDelete: Appointment) {
        viewModelScope.launch(Dispatchers.IO) {
            appointmentRepository.deleteAppointment(toDelete)
        }
    }

    suspend fun deleteAllAppointment() = viewModelScope.launch(Dispatchers.IO) {

        appointmentRepository.deleteAllAppointment()

    }
}