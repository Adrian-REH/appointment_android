package app.ibiocd.appointment.profile.di

import app.ibiocd.appointment.profile.datasource.retrofit.DateDataSource
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class DateSourceModule {

    @Singleton
    @Provides
    fun DateDataSource(retrofit: Retrofit): DateDataSource =
        retrofit.create(DateDataSource::class.java)



}








