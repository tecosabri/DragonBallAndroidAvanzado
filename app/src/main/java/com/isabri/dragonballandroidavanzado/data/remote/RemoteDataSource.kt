package com.isabri.dragonballandroidavanzado.data.remote

import com.isabri.dragonballandroidavanzado.domain.models.Hero
import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory


class RemoteDataSource {
    private var moshi = Moshi.Builder()
        .addLast(KotlinJsonAdapterFactory())
        .build()
    private var retrofit = Retrofit.Builder()
        .baseUrl("https://vapor2022.herokuapp.com")
        .addConverterFactory(MoshiConverterFactory.create(moshi))
        .build()
    private var api: DragonBallAPI = retrofit.create(DragonBallAPI::class.java)

    suspend fun getHeroes(): List<Hero> {
        return api.getHeroes()
    }
}