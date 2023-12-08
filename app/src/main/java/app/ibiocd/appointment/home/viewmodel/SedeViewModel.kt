package app.ibiocd.appointment.home.viewmodel

import android.app.Activity
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import app.ibiocd.appointment.model.*
import app.ibiocd.appointment.profile.model.repository.MedicalRepository
import app.ibiocd.appointment.profile.model.repository.PatientRepository
import com.nopalsoft.simple.rest.repository.*
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SedeViewModel @Inject constructor(
    private val sedeRepo: SedeRepository,
    ) : ViewModel() {

    private val _isLoading: MutableLiveData<Boolean> by lazy {
        MutableLiveData<Boolean>(false)
    }
    val isLoading: LiveData<Boolean> get() = _isLoading

    //SEDE
    val sedeslab: LiveData<List<Sedes>> by lazy {
        sedeRepo.getAllSedes()
    }
    suspend fun loadSede() {

        if (_isLoading.value == false)
            viewModelScope.launch(Dispatchers.IO) {
                _isLoading.postValue(true)
                sedeRepo.getSede()
                _isLoading.postValue(false)
                Log.d("loadSede","Successful")
            }
    }










}