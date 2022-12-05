package com.isabri.dragonballandroidavanzado.data.remote

import com.isabri.dragonballandroidavanzado.data.remote.request.HeroesRequest
import com.isabri.dragonballandroidavanzado.domain.models.Hero
import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory


class RemoteDataSource {
    private val token = "eyJraWQiOiJwcml2YXRlIiwiYWxnIjoiSFMyNTYiLCJ0eXAiOiJKV1QifQ.eyJleHBpcmF0aW9uIjo2NDA5MjIxMTIwMCwiZW1haWwiOiJoaXNtZTE0QGdtYWlsLmNvbSIsImlkZW50aWZ5IjoiQTczOTVGRTQtNkYwMy00MEJBLUE3RDctMDRFMUE5NUNEMEM5In0.Q3hnm0KSxhyKJBYXeG5k6p-8CF8gYy9MIJHvrD56XSg"
    private var moshi = Moshi.Builder()
        .addLast(KotlinJsonAdapterFactory())
        .build()
    private val httpLoggingInterceptor = HttpLoggingInterceptor(HttpLoggingInterceptor.Logger.DEFAULT).apply {
        level = HttpLoggingInterceptor.Level.BODY
    }
    private val okHttpClient = OkHttpClient.Builder()
        .authenticator { _, response ->
            response.request.newBuilder().header("Authorization", "Bearer $token").build()
        }
        .addInterceptor(httpLoggingInterceptor)
        .build()
    private var retrofit = Retrofit.Builder()
        .baseUrl("https://dragonball.keepcoding.education/")
        .client(okHttpClient)
        .addConverterFactory(MoshiConverterFactory.create(moshi))
        .build()
    private var api: DragonBallAPI = retrofit.create(DragonBallAPI::class.java)


    suspend fun getHeroes(): List<Hero> {
        return api.getHeroes(HeroesRequest())
    }
}