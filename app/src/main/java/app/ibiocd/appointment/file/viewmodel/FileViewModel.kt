package app.ibiocd.appointment.file.viewmodel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import app.ibiocd.appointment.model.Files
import app.ibiocd.appointment.file.model.FileRepository
import app.ibiocd.appointment.util.ApiResource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.lang.Exception
import javax.inject.Inject


@HiltViewModel
class FileViewModel @Inject constructor(
    private val fileRepo: FileRepository,
) : ViewModel() {

    private val _isLoading: MutableLiveData<Boolean> by lazy {
        MutableLiveData<Boolean>(false)
    }
    val isLoading: LiveData<Boolean> get() = _isLoading

    private val _idFile: MutableLiveData<String> by lazy {
        MutableLiveData<String>("")
    }
    val idFile: LiveData<String> get() = _idFile

    //FILES
    val files: LiveData<List<Files>> by lazy {
        fileRepo.getAllFile()
    }
   suspend fun deleteAllFiles() {
        viewModelScope.launch(Dispatchers.IO) {
            fileRepo.deleteAllFiles()
        }
    }
    fun deleteFile(toDelete: Files) {
        viewModelScope.launch(Dispatchers.IO) {
            fileRepo.deleteFile(toDelete);
        }
    }
    fun addFile(files: Files) {

        if (_isLoading.value == false)
            viewModelScope.launch(Dispatchers.IO) {
                _isLoading.postValue(true)
                _idFile.postValue(fileRepo.postFile(files))
                _isLoading.postValue(false)
                Log.d("addFile","Successful")
            }
    }
    fun uploadFile(files: Files) {

        if (_isLoading.value == false)
            viewModelScope.launch(Dispatchers.IO) {
                _isLoading.postValue(true)
                fileRepo.putFile(files)
                _isLoading.postValue(false)
                Log.d("uploadFile","Successful")
            }
    }
    suspend fun findAllFiles() = viewModelScope.launch(Dispatchers.IO) {
        fileRepo.findAllFiles().collect{
            when (it) {
                is ApiResource.Error -> {
                    Log.e("Error: Files not founds", it.message + "")
                }
                is ApiResource.Loading -> {
                    _isLoading.postValue(true)

                }
                is ApiResource.Success -> {
                    _isLoading.postValue(false)
                    Log.d("Success", "Files Found")

                }
            }
        }
    }
    suspend fun loadPatientFiles(patient:String) =viewModelScope.launch(Dispatchers.IO){
        fileRepo.getPatientFiles(patient).collect{
            when (it) {
                is ApiResource.Error -> {
                    Log.e("Error: Load Files patient", it.message + "")
                }
                is ApiResource.Loading -> {
                    _isLoading.postValue(true)

                }
                is ApiResource.Success -> {
                    _isLoading.postValue(false)
                    Log.d("Success", "Archivos de pacientes cargados")

                }
            }
        }




    }



}