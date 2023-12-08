package app.ibiocd.appointment.auth.datasource

import app.ibiocd.appointment.auth.model.TokenResponse
import app.ibiocd.appointment.model.ApiFavs
import app.ibiocd.appointment.model.DeleteResponse
import app.ibiocd.appointment.model.FavsRespons
import app.ibiocd.appointment.model.Patient
import com.google.android.gms.common.internal.safeparcel.SafeParcelable
import com.google.gson.annotations.SerializedName
import retrofit2.Call
import retrofit2.Response
import retrofit2.http.*

interface AuthDataSource {

    @POST("auth/login")
    suspend fun loginIn(
        @Body loginRequest: LoginRequest,
    ): TokenResponse

    @PATCH("auth/send-email-code")
    suspend fun verifyEmailCode(
        @Body email: String,
    ): Response<Void>
    @PATCH("auth/verify-code")
    suspend fun verifyCode(
        @Body request: VerifyCodeRequest,
    ): Response<Void>
    @PATCH("auth/reset-password")
    suspend fun resetPassword(
        @Body request: ResetPasswordRequest,
    ): Response<Void>
}

data class LoginRequest(@SerializedName("username")val username: String, @SerializedName("password")val password: String, @SerializedName("isMedical")val isMedical: Boolean)
data class ResetPasswordRequest(@SerializedName("passwordHash")val passwordHash: String, @SerializedName("email")val email: String)
data class VerifyCodeRequest(@SerializedName("twoFactorCode")val twoFactorCode: String, @SerializedName("email")val email: String)