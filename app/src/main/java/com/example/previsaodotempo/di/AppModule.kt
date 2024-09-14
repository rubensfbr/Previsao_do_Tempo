package com.example.previsaodotempo.di

import android.content.Context
import com.example.previsaodotempo.data.remote.OpenWeatherApi
import com.example.previsaodotempo.data.repository.LocationRepositoryImpl
import com.example.previsaodotempo.data.repository.WeatherRepositoryImpl
import com.example.previsaodotempo.domain.repository.LocationRepository
import com.example.previsaodotempo.domain.repository.WeatherRepositoty
import com.example.previsaodotempo.domain.usecase.LocationUseCase
import com.example.previsaodotempo.domain.usecase.WeatherUseCase
import com.example.previsaodotempo.utils.Constants
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent
import dagger.hilt.android.qualifiers.ApplicationContext
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

@Module
@InstallIn(ViewModelComponent::class)
object AppModule {

    private val okHttpClient: OkHttpClient = OkHttpClient.Builder()
        .writeTimeout(10, TimeUnit.SECONDS)
        .readTimeout(20, TimeUnit.SECONDS)
        .addInterceptor(object : Interceptor {
            override fun intercept(chain: Interceptor.Chain): Response {
                val requestConstructor = chain.request().newBuilder()
                val urlNova = chain.request().url().newBuilder().addQueryParameter(
                    "appid", Constants.API_KEY
                ).build()
                val request = requestConstructor.url(urlNova).build()
                return chain.proceed(request)
            }
        })
        .build()

    @Provides
    fun provideRetrofit(): Retrofit {
        return Retrofit.Builder()
            .baseUrl(Constants.BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .client(okHttpClient)
            .build()
    }

    @Provides
    fun provideOpenWeatherApi(retrofit: Retrofit): OpenWeatherApi {
        return retrofit.create(OpenWeatherApi::class.java)
    }

    @Provides
    fun provideWeatherRepository(
        openWeatherApi: OpenWeatherApi,
        @ApplicationContext context: Context
    ): WeatherRepositoty {
        return WeatherRepositoryImpl(openWeatherApi, context)
    }

    @Provides
    fun provideWeatherUseCase(repositoty: WeatherRepositoty): WeatherUseCase {
        return WeatherUseCase(repositoty)
    }

    @Provides
    fun provideLocationRepository(@ApplicationContext context: Context): LocationRepository {
        return LocationRepositoryImpl(context)
    }

    @Provides
    fun provideLocationUseCase(
        repository: LocationRepository,
        @ApplicationContext context: Context
    ): LocationUseCase {
        return LocationUseCase(repository, context)
    }

}