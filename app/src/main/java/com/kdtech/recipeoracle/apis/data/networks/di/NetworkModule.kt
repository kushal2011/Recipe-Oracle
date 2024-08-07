package com.kdtech.recipeoracle.apis.data.networks.di

import com.google.gson.Gson
import com.google.gson.GsonBuilder
import com.kdtech.recipeoracle.BuildConfig
import com.kdtech.recipeoracle.apis.data.networks.RecipesApi
import dagger.Module
import dagger.Provides
import dagger.Reusable
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import dagger.multibindings.IntoSet
import okhttp3.Authenticator
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit
import javax.inject.Named
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object NetworkModule {
    private const val DEFAULT_TIMEOUT = 20L
    private const val TIME_UNIT_NAMED = "timeUnitNamed"

    @Provides
    @Named(TIME_UNIT_NAMED)
    @Singleton
    fun timeOutUnit(): TimeUnit = TimeUnit.SECONDS

    @Provides
    @Singleton
    fun provideRetrofit(
        okHttpClient: OkHttpClient,
        gson: Gson
    ): Retrofit =
        createInstance(okHttpClient, gson)

    @Provides
    @Singleton
    fun provideInterceptors(): Set<Interceptor> {
        val loggingInterceptor = HttpLoggingInterceptor().apply {
            level = if (BuildConfig.DEBUG) {
                HttpLoggingInterceptor.Level.BODY
            } else {
                HttpLoggingInterceptor.Level.NONE
            }
        }
        return setOf(loggingInterceptor)
    }

    @Provides
    @Singleton
    fun provideAuthenticator(): Authenticator {
        return Authenticator.NONE
    }

    @Provides
    @Reusable
    fun provideOkHttpClient(
        interceptorsSet: Set<@JvmSuppressWildcards Interceptor>,
        authenticator: Authenticator,
        @Named(TIME_UNIT_NAMED) timeOutUnit: TimeUnit,
    ): OkHttpClient = OkHttpClient.Builder()
        .connectTimeout(DEFAULT_TIMEOUT, timeOutUnit)
        .writeTimeout(DEFAULT_TIMEOUT, timeOutUnit)
        .readTimeout(DEFAULT_TIMEOUT, timeOutUnit)
        .authenticator(authenticator)
        .apply { interceptorsSet.forEach(::addInterceptor) }
        .build()

    @Provides
    @Singleton
    fun provideGson(): Gson = GsonBuilder().create()

    private fun createInstance(
        okHttpClient: OkHttpClient,
        gson: Gson
    ) = Retrofit.Builder()
        .baseUrl("https://recipeoracle.kodedynamic.com")
        .client(okHttpClient)
        .addConverterFactory(GsonConverterFactory.create(gson))
        .build()
}