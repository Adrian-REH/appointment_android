package app.ibiocd.appointment.profile.di

import android.content.Context
import app.ibiocd.appointment.profile.datasource.retrofit.MedicalDataSource
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
class MedicalSourceModule {

    @Singleton
    @Provides
    fun cliente(context: Context): OkHttpClient =
        OkHttpClient.Builder()
            .connectTimeout(100,TimeUnit.SECONDS)
            .readTimeout(100,TimeUnit.SECONDS)

            .build()

    @Singleton
    @Provides
    fun restDataSource(retrofit: Retrofit): MedicalDataSource =
        retrofit.create(MedicalDataSource::class.java)


}








