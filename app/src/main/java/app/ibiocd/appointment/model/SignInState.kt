package app.ibiocd.appointment.model

import okhttp3.ResponseBody

data class SignInState(
    val isLoading: Boolean = false,
    val isSuccess: String? = "",
    val isError: String? = ""
)

data class ResetState(
    var isLoading: Boolean = false,
    var emailVerifySend: Boolean = false,
    var emailVerifySendError: Boolean = false,
    var codeVerified: Boolean = false,
    var codeVerifiedError: Boolean = false,
    var passwordChanged: Boolean = false,
    var passwordChangedError: Boolean = false,
)
data class RegisterState(
    val isLoading: Boolean = false,
    val isSuccess: String? = "",
    val isError: String? = ""
)

