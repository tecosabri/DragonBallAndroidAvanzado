package com.isabri.dragonballandroidavanzado.data.remote

import com.isabri.dragonballandroidavanzado.domain.models.Hero
import retrofit2.Retrofit

class RemoteDataSource {

    private var retrofit = Retrofit.Builder()
        .baseUrl("https://vapor2022.herokuapp.com")
        .build()
    private var api: DragonBallAPI = retrofit.create(DragonBallAPI::class.java)

    suspend fun getHeroes(): List<Hero> {
        return api.getHeroes()
    }
}