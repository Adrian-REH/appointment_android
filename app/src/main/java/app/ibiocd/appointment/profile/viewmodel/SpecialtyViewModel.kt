package app.ibiocd.appointment.profile.viewmodel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import app.ibiocd.appointment.model.Specialty
import app.ibiocd.appointment.profile.model.repository.SpecialtyRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject


@HiltViewModel
class SpecialtyViewModel @Inject constructor(
    private val specialtyRepo: SpecialtyRepository,
) : ViewModel() {
    private val _isLoadingUp: MutableLiveData<Boolean> by lazy {
        MutableLiveData<Boolean>(false)
    }
    private val _isLoading: MutableLiveData<Boolean> by lazy {
        MutableLiveData<Boolean>(false)
    }
    val isLoading: LiveData<Boolean> get() = _isLoading
    val isLoadingUp: LiveData<Boolean> get() = _isLoadingUp


    //SPECIALTY
    val specialty: LiveData<List<Specialty>> by lazy {
        specialtyRepo.getAllSpecialty()
    }
    suspend fun deleteSpecialty(toDelete: Specialty) {
        viewModelScope.launch(Dispatchers.IO) {
            _isLoading.postValue(true)
            specialtyRepo.deleteSpecialty(toDelete)
            _isLoading.postValue(false)
        }
    }
    suspend fun loadSpecialty() {

        if (_isLoading.value == false)
            viewModelScope.launch(Dispatchers.IO) {
                _isLoading.postValue(true)
                specialtyRepo.getSpecialty()
                _isLoading.postValue(false)
                Log.d("loadSpecialty","Successful")

            }


    }
    suspend fun addSpecialty(specialty: Specialty) {
        if (_isLoading.value == false)
            viewModelScope.launch(Dispatchers.IO) {
                _isLoading.postValue(true)
                specialtyRepo.postSpecialty(specialty)
                _isLoading.postValue(false)
                Log.d("addSpecialty","Successful")

            }
    }
    suspend fun uploadSpecialty(specialty: Specialty) {
        if (_isLoading.value == false)
            viewModelScope.launch(Dispatchers.IO) {
                _isLoading.postValue(true)
                specialtyRepo.putSpecialty(specialty)
                _isLoading.postValue(false)
                Log.d("uploadSpecialty","Successful")
            }
    }


}