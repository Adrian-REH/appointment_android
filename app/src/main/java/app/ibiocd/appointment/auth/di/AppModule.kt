package app.ibiocd.appointment.auth.di

import app.ibiocd.appointment.appointment.datasource.AppointmentDataSource
import app.ibiocd.appointment.auth.datasource.AuthDataSource
import app.ibiocd.appointment.auth.model.AuthRepository
import app.ibiocd.appointment.auth.model.AuthRepositoryImpl
import com.google.firebase.auth.FirebaseAuth
import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import java.util.concurrent.TimeUnit
import javax.inject.Singleton


@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Singleton
    @Provides
    fun cliente(): OkHttpClient =
        OkHttpClient.Builder()
            .connectTimeout(10, TimeUnit.SECONDS)
            .readTimeout(10, TimeUnit.SECONDS)
            .build()
    @Singleton
    @Provides
    fun restDataSource(retrofit: Retrofit): AuthDataSource =
        retrofit.create(AuthDataSource::class.java)

    @Provides
    @Singleton
    fun providesRepositoryImpl(retrofit: Retrofit): AuthRepository {
        return AuthRepositoryImpl(restDataSource(retrofit))
    }

}