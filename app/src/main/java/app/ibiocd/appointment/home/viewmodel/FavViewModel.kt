package app.ibiocd.appointment.home.viewmodel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import app.ibiocd.appointment.model.Favs
import app.ibiocd.appointment.util.ApiResource
import com.nopalsoft.simple.rest.repository.FavRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class FavViewModel @Inject constructor(
    private val favRepository: FavRepository,
) : ViewModel() {
    private val _isLoading: MutableLiveData<Boolean> by lazy {
        MutableLiveData<Boolean>(false)
    }
    val isLoading: LiveData<Boolean> get() = _isLoading

    //FAVS
    val favs: LiveData<List<Favs>> by lazy {
        favRepository.getAllFavDao()
    }

    suspend fun getAllFavs() = viewModelScope.launch(Dispatchers.IO) {

        if (_isLoading.value == false) {
            favRepository.getAllFav().collect {
                when (it) {
                    is ApiResource.Error -> {
                        Log.e("Error: Favorites not founds", it.message + "")
                    }
                    is ApiResource.Loading -> {
                        _isLoading.postValue(true)

                    }
                    is ApiResource.Success -> {
                        _isLoading.postValue(false)
                        Log.d("Success", "Favorites Founds")
                    }
                }
            }
        }
    }

    fun AddFavs(favs: Favs) {
        if (_isLoading.value == false)
            viewModelScope.launch(Dispatchers.IO) {
                _isLoading.postValue(true)
                favRepository.postFavs(favs)
                _isLoading.postValue(false)
                Log.d("AddFavs", "Successful")

            }
    }

    fun DeleteFavs(favs: Favs) {
        if (_isLoading.value == false)
            viewModelScope.launch(Dispatchers.IO) {
                _isLoading.postValue(true)
                favRepository.deleteFavs(favs)
                _isLoading.postValue(false)
                Log.d("DeleteFavs", "Successful")

            }
    }

    suspend fun deleteAllFavs() {
        viewModelScope.launch(Dispatchers.IO) {
            favRepository.deleteAllFavs()
        }
    }


}