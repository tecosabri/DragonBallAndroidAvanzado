package com.isabri.dragonballandroidavanzado.di

import android.app.Activity
import android.content.Context
import android.content.SharedPreferences
import com.isabri.dragonballandroidavanzado.data.remote.DragonBallAPI
import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ActivityContext
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory

@Module
@InstallIn(SingletonComponent::class)
object RemoteModule {

    private val token = "eyJraWQiOiJwcml2YXRlIiwiYWxnIjoiSFMyNTYiLCJ0eXAiOiJKV1QifQ.eyJleHBpcmF0aW9uIjo2NDA5MjIxMTIwMCwiZW1haWwiOiJoaXNtZTE0QGdtYWlsLmNvbSIsImlkZW50aWZ5IjoiQTczOTVGRTQtNkYwMy00MEJBLUE3RDctMDRFMUE5NUNEMEM5In0.Q3hnm0KSxhyKJBYXeG5k6p-8CF8gYy9MIJHvrD56XSg"

    @Provides
    fun provideSharedPreferences(@ApplicationContext context: Context): SharedPreferences {
        return context.getSharedPreferences("NAME", Context.MODE_PRIVATE)
    }

    @Provides
    fun provideMoshi(): Moshi {
        return Moshi.Builder()
            .addLast(KotlinJsonAdapterFactory())
            .build()
    }

    @Provides
    fun provideHttpLoginInterceptor(): HttpLoggingInterceptor {
        return HttpLoggingInterceptor(HttpLoggingInterceptor.Logger.DEFAULT).apply {
            level = HttpLoggingInterceptor.Level.BODY
        }
    }

    @Provides
    fun provideOkHttpClient(httpLoggingInterceptor: HttpLoggingInterceptor, sharedPreferences: SharedPreferences): OkHttpClient {
        return OkHttpClient.Builder()
            .addInterceptor { chain ->
                val originalRequest = chain.request()
                val newRequest = originalRequest.newBuilder()
//                .header("Authorization", "Bearer $TOKEN")
                    .header("Content-Type", "Application/Json")
                    .build()
                chain.proceed(newRequest)
            }
            .authenticator { _, response ->
                response.request.newBuilder().header("Authorization", "Bearer ${sharedPreferences.getString("TOKEN", null)}").build()
            }
            .addInterceptor(httpLoggingInterceptor)
            .build()
    }

    @Provides
    fun provideRetrofit(okHttpClient: OkHttpClient, moshi: Moshi): Retrofit {
        return Retrofit.Builder()
            .baseUrl("https://dragonball.keepcoding.education/")
            .client(okHttpClient)
            .addConverterFactory(MoshiConverterFactory.create(moshi))
            .build()
    }

    @Provides
    fun provideAPI(retrofit: Retrofit): DragonBallAPI {
        return retrofit.create(DragonBallAPI::class.java)
    }
}

