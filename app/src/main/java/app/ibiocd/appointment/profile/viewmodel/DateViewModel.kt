package app.ibiocd.appointment.profile.viewmodel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import app.ibiocd.appointment.model.Date
import app.ibiocd.appointment.profile.model.repository.DateRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject


@HiltViewModel
class DateViewModel @Inject constructor(
    private val dateRepo: DateRepository,
) : ViewModel() {
    private val _isLoadingUp: MutableLiveData<Boolean> by lazy {
        MutableLiveData<Boolean>(false)
    }
    private val _isLoading: MutableLiveData<Boolean> by lazy {
        MutableLiveData<Boolean>(false)
    }
    val isLoading: LiveData<Boolean> get() = _isLoading
    val isLoadingUp: LiveData<Boolean> get() = _isLoadingUp

    //DATE
    val date: LiveData<List<Date>> by lazy {
        dateRepo.getAllDate()
    }
    fun deleteDate(toDelete: Date) {
        if (_isLoading.value == false)
            viewModelScope.launch(Dispatchers.IO) {
                _isLoading.postValue(true)
                dateRepo.deleteDate(toDelete)
                _isLoading.postValue(false)
            }
    }
    suspend fun loadDate() {

        if (_isLoading.value == false)
            viewModelScope.launch(Dispatchers.IO) {
                _isLoading.postValue(true)
                dateRepo.getDate()
                _isLoading.postValue(false)
                Log.d("loadDate","Successful")
            }
    }
    fun uploadDate(date: Date) {
        if (_isLoading.value == false)
            viewModelScope.launch(Dispatchers.IO) {
                _isLoadingUp.postValue(true)
                dateRepo.putDate(date)
                _isLoadingUp.postValue(false)
                Log.d("uploadDate","Successful")

            }
    }
    fun addDate(date: Date) {

        if (_isLoading.value == false)
            viewModelScope.launch(Dispatchers.IO) {
                _isLoading.postValue(true)
                dateRepo.postDate(date)
                _isLoading.postValue(false)
                Log.d("addDate","Successful")
            }
    }


}