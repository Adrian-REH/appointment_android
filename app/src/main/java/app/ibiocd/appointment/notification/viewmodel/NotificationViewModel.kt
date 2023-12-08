package app.ibiocd.appointment.notification.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import app.ibiocd.appointment.model.*
import app.ibiocd.appointment.notification.model.NotificationState
import app.ibiocd.appointment.util.Resource
import com.nopalsoft.simple.rest.repository.*
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class NotificationViewModel @Inject constructor(
    private val notificationRepository: NotificationRepositoryImp
) : ViewModel() {


    val _notificationState=  Channel<NotificationState>()
    val notificationState=  _notificationState.receiveAsFlow()


    //APPOINTMENT

    fun newNotification(notification: PushNotification)  = viewModelScope.launch(Dispatchers.IO) {
        notificationRepository.sendNotification(notification).collect{
            when(it){
                is Resource.Error -> {
                    _notificationState.send(NotificationState(isError = it.message))

                }
                is Resource.Loading -> {
                    _notificationState.send(NotificationState(isLoading = true))

                }
                is Resource.Success -> {
                    _notificationState.send(NotificationState(isSuccess = "Notificacion enviada"))

                }
            }

        }

    }


}