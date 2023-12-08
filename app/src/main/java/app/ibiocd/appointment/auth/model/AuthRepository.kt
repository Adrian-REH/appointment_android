package app.ibiocd.appointment.auth.model

import app.ibiocd.appointment.auth.datasource.ResetPasswordRequest
import app.ibiocd.appointment.auth.datasource.VerifyCodeRequest
import app.ibiocd.appointment.util.Resource
import com.google.firebase.auth.AuthCredential
import com.google.firebase.auth.AuthResult
import com.google.firebase.auth.SignInMethodQueryResult
import kotlinx.coroutines.flow.Flow

interface AuthRepository {
    suspend  fun loginUser(email: String, password: String, isMedical: Boolean): Flow<Resource<String>>
    suspend fun sendEmailCode(email: String): Flow<Resource<Unit>>
    suspend fun verifyCode(request: VerifyCodeRequest): Flow<Resource<Unit>>
    suspend fun resetPassword(request: ResetPasswordRequest): Flow<Resource<Unit>>
}