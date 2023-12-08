package app.ibiocd.appointment.notification.model

data class NotificationState(
    val isLoading: Boolean = false,
    val isSuccess: String? = "",
    val isError: String? = ""
)