package app.ibiocd.appointment.appointment.di

import app.ibiocd.appointment.appointment.datasource.LabDataSource
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
class LabSourceModule {


    @Singleton
    @Provides
    fun cliente(): OkHttpClient =
        OkHttpClient.Builder()
            .connectTimeout(100,TimeUnit.SECONDS)
            .readTimeout(100,TimeUnit.SECONDS)
            .build()

    @Singleton
    @Provides
    fun restDataSource(retrofit: Retrofit): LabDataSource =
        retrofit.create(LabDataSource::class.java)



}








