package app.ibiocd.appointment.appointment.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import app.ibiocd.appointment.appointment.model.OdontogramState
import app.ibiocd.appointment.model.*
import app.ibiocd.appointment.util.ApiResource
import com.nopalsoft.simple.rest.repository.*
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class OdontogramViewModel @Inject constructor(
    private val odontogramRepository: OdontogramRepository
) : ViewModel() {


    val _odontogramState=  Channel<OdontogramState>()
    val odontogramState=  _odontogramState.receiveAsFlow()


    //APPOINTMENT
    val odontogram: LiveData<List<Odontogram>> by lazy {
        odontogramRepository.getAllOdontogram()
    }

    val tooth: LiveData<List<Tooth>> by lazy {
        odontogramRepository.getAllTooth()
    }
    fun addOdontogram(data:ApiTooth, patient:String, medical:String)  = viewModelScope.launch(Dispatchers.IO) {
        odontogramRepository.postOdontogram(data,patient,medical).collect{
            when(it){
                is ApiResource.Error -> {
                    _odontogramState.send(OdontogramState(isError = it.message))
                }
                is ApiResource.Loading -> {
                    _odontogramState.send(OdontogramState(isLoading = true))
                }
                is ApiResource.Success -> {
                    _odontogramState.send(OdontogramState(isSuccess = it.result!!))
                }
            }

        }

    }

    fun loadOdontogram(odontogramID:String) {

            viewModelScope.launch(Dispatchers.IO) {
                odontogramRepository.getOdontogram(odontogramID)
            }
    }
    suspend fun deleteOdontogram() {

            viewModelScope.launch(Dispatchers.IO) {
                odontogramRepository.deleteAllOdontogram()
            }
    }

    fun updateontogram(id:String,data:ApiTooth,patient:String,medical:String) {

            viewModelScope.launch(Dispatchers.IO) {
                odontogramRepository.putOdontogram(id,data,patient,medical)
            }
    }

}