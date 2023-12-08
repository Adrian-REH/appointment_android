package app.ibiocd.appointment.auth.model

import android.util.Log
import app.ibiocd.appointment.auth.datasource.AuthDataSource
import app.ibiocd.appointment.auth.datasource.LoginRequest
import app.ibiocd.appointment.auth.datasource.ResetPasswordRequest
import app.ibiocd.appointment.auth.datasource.VerifyCodeRequest
import app.ibiocd.appointment.model.FavsRespons
import app.ibiocd.appointment.util.ApiResource
import app.ibiocd.appointment.util.Resource
import com.google.firebase.auth.AuthResult
import com.google.firebase.auth.FirebaseAuth
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.tasks.await
import retrofit2.Call
import retrofit2.Callback
import javax.inject.Inject

class AuthRepositoryImpl @Inject constructor(
    private val authDataSource: AuthDataSource,
) : AuthRepository {

     override suspend fun loginUser(email: String, password: String, isMedical: Boolean): Flow<Resource<String>> {
        return flow {
            emit(Resource.Loading())
            //val result = firebaseAuth.signInWithEmailAndPassword(email, password).await()
            val response = authDataSource.loginIn(LoginRequest(email,password,isMedical))
            emit(Resource.Success(response.token))

        }.catch {
            emit(Resource.Error(it.message.toString()))
        }


    }



    override suspend fun sendEmailCode(email: String): Flow<Resource<Unit>> {
        return flow {
            emit(Resource.Loading())
            val result = authDataSource.verifyEmailCode(email)
            if(result.code()==200){
                emit(Resource.Success(Unit))
            }else{
                emit(Resource.Error(result.message()))
            }
        }.catch {
            emit(Resource.Error(it.message.toString()))
        }

    }

    override suspend fun verifyCode(request: VerifyCodeRequest): Flow<Resource<Unit>> {
        return flow {
            emit(Resource.Loading())
            val result = authDataSource.verifyCode(request)
            if(result.code() == 200){
                emit(Resource.Success(Unit))
            }else{
                emit(Resource.Error(result.message()))
            }
        }.catch {
            emit(Resource.Error(it.message.toString()))
        }
    }
    override suspend fun resetPassword(request: ResetPasswordRequest): Flow<Resource<Unit>> {
        return flow {
            emit(Resource.Loading())
            val result = authDataSource.resetPassword(request)
            if(result.code() == 200){
                emit(Resource.Success(Unit))
            }else{
                emit(Resource.Error(result.message()))
            }
            }.catch {
            emit(Resource.Error(it.message.toString()))
        }

    }

}