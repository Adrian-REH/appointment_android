package app.ibiocd.appointment.appointment.di

import app.ibiocd.appointment.appointment.datasource.OdontogramDataSource
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
class OdontogramDataSourceModule {


    @Singleton
    @Provides
    fun cliente(): OkHttpClient =
        OkHttpClient.Builder()
            .connectTimeout(100, TimeUnit.SECONDS)
            .readTimeout(100, TimeUnit.SECONDS)
            .build()



    @Singleton
    @Provides
    fun restDataSource(retrofit: Retrofit): OdontogramDataSource =
        retrofit.create(OdontogramDataSource::class.java)


}