package app.ibiocd.appointment

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
class UserViewModel @Inject constructor(
    private val userRepo: UserRepository,
    private val medicalRepository: MedicalRepository
    ) : ViewModel() {

    //UPLOAD IMAGE
    private val _isImage: MutableLiveData<String> by lazy {
        MutableLiveData<String>("")
    }
    private val _isImageUri: MutableLiveData<String> by lazy {
        MutableLiveData<String>("")
    }
    val isImageUri: LiveData<String> get() = _isImageUri
    val isImage: LiveData<String> get() = _isImage


    private val _isLoadingUp: MutableLiveData<Boolean> by lazy {
        MutableLiveData<Boolean>(false)
    }
    private val _isLoading: MutableLiveData<Boolean> by lazy {
        MutableLiveData<Boolean>(false)
    }
    val isLoading: LiveData<Boolean> get() = _isLoading
    val isLoadingUp: LiveData<Boolean> get() = _isLoadingUp


    private val _isLogin: MutableLiveData<Boolean> by lazy {
        MutableLiveData<Boolean>(false)
    }




     fun uploadImage(context: Activity?, imagename:String, imageData:ByteArray) {

         if (_isLoading.value == false)
            viewModelScope.launch(Dispatchers.IO) {
                _isLoading.postValue(true)
                _isImage.postValue(userRepo.uploadImage(context!!,imagename,imageData))
                _isLoading.postValue(false)
                Log.d("uploadImage","Successful")
            }
    }

    fun insertImag(image:String) {
            _isImageUri.postValue(image)

    }
    fun logeopatient(user:String,password:String) {

        viewModelScope.launch(Dispatchers.IO) {
            if (user!=""){
                _isLogin.postValue(true)

            }
        }
    }

    private val _LatLng: MutableLiveData<List<Double>> by lazy {
        MutableLiveData<List<Double>>()
    }
    val LatLngDir: LiveData<List<Double>> get() = _LatLng

    fun getDirectionLatLng(address:String,neighbourhood:String,country:String) {
        viewModelScope.launch(Dispatchers.IO) {
            _isLoading.postValue(true)
            _LatLng.postValue(medicalRepository.getDirectionLatLng(address, neighbourhood, country))
            _isLoading.postValue(false)


        }
    }
}