package app.ibiocd.appointment.di

import android.content.Context
import android.util.Log
import androidx.room.Room
import app.ibiocd.appointment.datasource.DbDataSource
import app.ibiocd.appointment.model.*
import app.ibiocd.appointment.util.UtilSharedToken
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.Multipart
import java.util.concurrent.TimeUnit
import javax.inject.Named
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class DataSourceModule {

    @Singleton
    @Provides
    @Named("BaseUrl")
    //fun provideBaseUrl() = "https://appointmentibiocd.azurewebsites.net/api/"
    //fun provideBaseUrl() = "http://10.0.2.2:9000/api/"
    fun provideBaseUrl() = "http://192.168.0.16:8080/"
    @Singleton
    @Provides
    fun provideRetrofit(@Named("BaseUrl") baseUrl: String,@ApplicationContext context: Context): Retrofit {
        return Retrofit.Builder()
            .addConverterFactory(GsonConverterFactory.create())
            .client(cliente(context))
            .baseUrl(baseUrl)
            .build()
    }

    @Singleton
    @Provides
    fun cliente(context: Context): OkHttpClient =
        OkHttpClient.Builder()
            .connectTimeout(30,TimeUnit.SECONDS)
            .readTimeout(30,TimeUnit.SECONDS)
            .addInterceptor { chain ->
                // Accede al contexto aquí según sea necesario
                val jwt = UtilSharedToken(context).getJwt()

                if(!jwt.isNullOrBlank()){
                    Log.d("CHAIN","Token $jwt")
                    // Continúa con la cadena de interceptores
                    val originalRequest = chain.request()
                    var requestBuilder = originalRequest.newBuilder()
                        .header("Authorization", "Bearer $jwt")
                    chain.proceed(requestBuilder.build())

                }else{
                    chain.proceed(chain.request())

                }

            }
            .build()





}








